@Service
@RequiredArgsConstructor
public class ClaudeService {

    private final ClaudeApiClient claudeApiClient;

    @PostConstruct
    public void test() {
        var response = claudeApiClient.messages(
            new MessagesRequest(new Message("user", "Hello world!"))
        );
        // ...
    }
}
