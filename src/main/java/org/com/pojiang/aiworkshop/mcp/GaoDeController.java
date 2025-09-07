package org.com.pojiang.aiworkshop.mcp;

import io.modelcontextprotocol.client.McpAsyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

/**
 * @author liheng
 */
@RestController
@Slf4j
public class GaoDeController {

    @Resource
    List<McpAsyncClient> mcpAsyncClients;

    @RequestMapping("/test")
    public Mono<McpSchema.CallToolResult> test() {
        var mcpClient = mcpAsyncClients.get(0);

        return mcpClient.listTools()
                .flatMap(tools -> {
                    log.info("tools: {}", tools);
                    return mcpClient.callTool(
                            new McpSchema.CallToolRequest(
                                    "maps_weather",
                                    Map.of("city", "北京")
                            )
                    );
                });
    }

}
