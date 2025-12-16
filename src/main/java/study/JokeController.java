package study;

import com.google.api.client.util.Value;
import io.opencensus.resource.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static reactor.netty.http.HttpConnectionLiveness.log;


@RestController
public class JokeController {
    private final ChatClient client;

    @Value("classpath://")
    // prompt를 파일로 관리하는 경우
    private Resource resource;

    public JokeController(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @GetMapping("/joke")
    public ChatResponse joke(@RequestParam(defaultValue = "Tell me a joke about {topic}") String message,
                             @RequestParam(defaultValue = "programming") String topic) {
        var template = new PromptTemplate(message);
        var prompt = template.render(Map.of("topic", topic));
        return client.prompt(prompt)
            .call()
            .chatResponse();
    }

    @GetMapping("/joke2")
    public ChatResponse joke2(@RequestParam(defaultValue = "Bob") String name,
                             @RequestParam(defaultValue = "pirate") String voice) {
        var user = new UserMessage("""
                    Tell me about three famous pirates from the Golden Age of Piracy and what they did.
                    Write at least one sentence for each pirate.
                """);
        var template = new SystemPromptTemplate("""
                    You are a helpful AI assistant.
                    You are an AI assistant that helps people find information.
                    Your name is {name}.
                    You should reply to the user's request using your name and in the style of a {voice}.
                """);
        var system = template.createMessage(Map.of("name", name, "voice", voice));
        var prompt = new Prompt(user, system);
        return client.prompt(prompt).call().chatResponse();
    }

    @GetMapping("/actors")
    public ActorFilms actors(@RequestParam(defaultValue = "Tom Cruise") String actor){
        var beanOutputConverter = new BeanOutputConverter<>(ActorFilms.class);
        var format = beanOutputConverter.getFormat();
        log.info(format);
        var userMessage = """
            Generate the filmography of 5 movies for {actor}.
            {format}
            """;
        var prompt = new PromptTemplate(userMessage)
                .create(Map.of("actor", actor, "format", format));

        var text = client.prompt(prompt)
                .call()
                .content();
        log.info(text);
        return beanOutputConverter.convert(text);
    }

    @GetMapping("/addDays")
    public String addDays(
            @RequestParam(defaultValue = "0") int days
    ) {
        var template = new PromptTemplate("Tell me the date after {days} from today");
        var prompt = template.render(Map.of("days", days));
        return client.prompt(prompt)
                .tools(new Functions())
                .call()
                .content();
    }

}
