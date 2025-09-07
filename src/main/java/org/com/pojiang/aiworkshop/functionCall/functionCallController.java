package org.com.pojiang.aiworkshop.functionCall;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.resolution.ToolCallbackResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liheng
 */
@RestController
public class functionCallController {

    private final ChatClient chatClient;
    public functionCallController(ChatClient.Builder builder) {
        this.chatClient = builder.defaultUser("Can you set an alarm 10 minutes from now?")
                .defaultTools(new DateTimeTools())
                .build();
    }


    @GetMapping("/functionCall")
    public String functionCall() {
        return chatClient.prompt()
                .call()
                .content();
    }
}
