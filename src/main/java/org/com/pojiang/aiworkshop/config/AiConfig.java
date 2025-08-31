package org.com.pojiang.aiworkshop.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liheng
 */
@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatModel chatModel){
        // 返回一个带有默认系统提示词的client
        return ChatClient.builder(chatModel)
                .build();
    }
}
