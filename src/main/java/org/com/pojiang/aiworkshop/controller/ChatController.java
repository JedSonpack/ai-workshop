package org.com.pojiang.aiworkshop.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.ParameterizedTypeReference;
import org.com.pojiang.aiworkshop.output.Things;

import reactor.core.publisher.Flux;
import java.util.List;

/**
 * @author liheng
 */
@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai")
    String javaStory() {
        return this.chatClient.prompt()
                .user("告诉我有关Java的一个有趣的事实！")
                .call()
                .content();
    }

    @GetMapping("/ai/java-expert")
    String javaExpert() {
        return this.chatClient.prompt()
                .system("你是一个对Java历史研究比较牛逼的专家")
                .user("告诉我有关Java的一个有趣的事实！")
                .call()
                .content();
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam() String message) {
        return this.chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/stream/travel")
    public Flux<String> travelStream(@RequestParam() String message) {
        return this.chatClient.prompt()
                .system("你是一个旅游大师")
                .user(message)
                .stream()
                .content();
    }

    @GetMapping("/joke")
    public String tellJoke() {
        return chatClient.prompt()
                .user("讲一个关于布偶猫的笑话")
                .call()
                .content();
    }
}