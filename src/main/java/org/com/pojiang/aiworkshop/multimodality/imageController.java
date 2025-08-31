package org.com.pojiang.aiworkshop.multimodality;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author liheng
 * ZhiPu SpringAI 不支持 识图 生图 音频生成
 */
@RestController
public class imageController {
    @Resource
    private ChatClient chatClient;

    @Value("classpath:/images/img.png")
    org.springframework.core.io.Resource image;

    @GetMapping("/image")
    public String getImage() {
        // 从URL加载图像
        ChatOptions chatOptions = ChatOptions.builder()
                .model("GLM-4V-Flash")
                .build();

        return chatClient.prompt()
                .options(chatOptions)
                .user(userSpec -> {
                            userSpec.text("图片中有什么？")
                                    .media(MimeTypeUtils.IMAGE_PNG, image);
                        }
                )
                .call()
                .content();
    }
}
