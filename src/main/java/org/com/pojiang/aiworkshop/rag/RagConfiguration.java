package org.com.pojiang.aiworkshop.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author liheng
 */
@Configuration
public class RagConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RagConfiguration.class);

    @Value("vectorstore.json")
    private String vectorStoreName;


    // 原始数据
    @Value("classpath:/data/models.json")
    private Resource models;

    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) throws IOException {
        // 创建一个新的向量存储实例，使用传入的嵌入模型来生成文本向量
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        // 获取向量存储文件的路径
        File vectorStoreFile = getVectorStoreFile();
        // 检查向量存储文件是否已经存在
        if (vectorStoreFile.exists()) {
            // 如果文件存在，记录日志
            log.info("Vector Store File Exists,");
            // 从已有文件加载向量数据，避免重复处理
            simpleVectorStore.load(vectorStoreFile);
        } else {
            // 如果文件不存在，记录日志并开始构建新的向量存储
            log.info("Vector Store File Does Not Exist, loading documents");
            // 创建文本读取器，读取models资源文件中的原始文本数据
            TextReader textReader = new TextReader(models);
            // 为文档添加自定义元数据，标记文件名
            textReader.getCustomMetadata().put("filename", "models.json");
            // 读取并获取所有文档内容
            List<Document> documents = textReader.get();
            // 创建基于Token的文本分割器，用于将长文本分割成更小的片段
            TextSplitter textSplitter = new TokenTextSplitter();
            // 将文档分割成多个小片段，便于向量化处理和检索
            List<Document> splitDocuments = textSplitter.apply(documents);
            // 将分割后的文档添加到向量存储中，此过程会自动生成向量
            simpleVectorStore.add(splitDocuments);
            // 保存向量存储到文件，下次可以直接加载使用
            simpleVectorStore.save(vectorStoreFile);
        }
        // 返回配置好的向量存储实例，供其他组件使用
        return simpleVectorStore;
    }

    private File getVectorStoreFile() {
        Path path = Paths.get("src", "main", "resources", "data");
        String absolutePath = path.toFile().getAbsolutePath() + "/" + vectorStoreName;
        return new File(absolutePath);
    }

}