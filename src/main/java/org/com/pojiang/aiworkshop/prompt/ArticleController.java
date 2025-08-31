package org.com.pojiang.aiworkshop.prompt;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleController {

    private final ChatClient chatClient;


    public ArticleController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }



    @GetMapping("/article")
    public String getArticle(@RequestParam(value = "topic", defaultValue = "JAVA") String topic) {
        return this.chatClient.prompt()
                .system("你是一个专业的文章作家，擅长根据主题撰写简洁而有深度的文章")
                .user(u -> {
                    u.text("给我写一个主题为{topic}的文章");
                    u.param("topic", topic);
                })
                .call()
                .content();
    }
}
