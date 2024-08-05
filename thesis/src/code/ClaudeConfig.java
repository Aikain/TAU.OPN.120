public class ClaudeConfig {

    @Value("${CLAUDE_API_KEY}")
    private String CLAUDE_API_KEY;

    @Bean
    public RequestInterceptor requestInterceptor(){
        return requestTemplate -> {
            requestTemplate.header("anthropic-version", "2023-06-01");
            requestTemplate.header("x-api-key", CLAUDE_API_KEY);
        };
    }
}
