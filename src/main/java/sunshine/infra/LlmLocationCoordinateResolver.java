package sunshine.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Component;
import sunshine.domain.Coordinate;

import java.util.Map;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
public class LlmLocationCoordinateResolver implements LocationCoordinateResolver {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public LlmLocationCoordinateResolver(
            ChatClient.Builder chatClientBuilder,
            ObjectMapper objectMapper
    ) {
        this.chatClient = chatClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public Coordinate resolve(String locationName) {

        var beanOutputConverter = new BeanOutputConverter<>(Coordinate.class);
        var format = beanOutputConverter.getFormat();
        var userMessage = """
                                너는 지구상의 도시나 지역 이름을 위도, 경도로 변환해주는 도우미야.
                반드시 아래 형식의 JSON만 출력해.

                형식: {format}

                제약:
                - JSON 외에 어떤 설명도 출력하지 말 것.
                - 위도/경도는 WGS84 기준의 십진수(double) 값으로 줄 것.
                - 도시/지역 이름이 애매하면 가장 일반적으로 알려진 위치를 사용해.

                입력 도시/지역 이름: {city}
                """;
        var prompt = new PromptTemplate(userMessage).create(Map.of("city",locationName, "format", format));

        String json = chatClient.prompt(prompt)
                .call()
                .content();
        log.info(json);
        return beanOutputConverter.convert(json);
    }




//    private Coordinate parseCoordinate(String json) {
//        CoordinatePayload payload = toPayload(json);
//        return new Coordinate(payload.latitude, payload.longitude);
//    }

    private CoordinatePayload toPayload(String json) {
        try {
            return objectMapper.readValue(json, CoordinatePayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("LLM 좌표 응답 JSON 파싱 실패: " + json, e);
        }
    }

    private static class CoordinatePayload {

        public double latitude;
        public double longitude;
    }
}
