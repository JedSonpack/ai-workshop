package org.com.pojiang.aiworkshop.output;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author liheng
 */
@RestController
public class structOutputController {

    @Resource
    private ChatClient chatClient;

    @GetMapping("/unStruct")
    public String getUnStructOutput() {
        return this.chatClient.prompt()
                .user("我想去夏威夷旅游，请你给我一个到了夏威夷必做的事情,列举三个即可")
                .call()
                .content();
    }

    @GetMapping("/struct-output")
    public List<Things> getStructOutput() {
        return this.chatClient.prompt()
                .user("我想去夏威夷旅游，请你给我一个到了夏威夷必做的事情,列举三个即可")
                .call()
                .entity(new ParameterizedTypeReference<>() {
                });
    }
}
