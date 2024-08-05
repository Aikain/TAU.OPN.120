@FeignClient(
    name = "claude-api",
    url = "https://api.anthropic.com/v1/",
    configuration = ClaudeConfig.class
)
public interface ClaudeApiClient {

    @PostMapping("messages")
    MessagesResponse messages(@RequestBody MessagesRequest messagesRequest);
}
