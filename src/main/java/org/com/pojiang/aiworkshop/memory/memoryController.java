package org.com.pojiang.aiworkshop.memory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liheng
 */
@RestController
public class memoryController {

    private final ChatClient chatClient;

    public memoryController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }


    @GetMapping("/memory")
    public String memory(String message) {

        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
