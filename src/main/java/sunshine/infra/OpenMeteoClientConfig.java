package sunshine.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration

public class OpenMeteoClientConfig {
    @Bean
    public OpenMeteoClient openMeteoClient(WebClient openMeteoWebClient) {
        return new OpenMeteoWebClient(openMeteoWebClient);
    }
}
