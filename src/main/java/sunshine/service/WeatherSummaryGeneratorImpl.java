package sunshine.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Component;
import sunshine.infra.FunctionConfiguration;
//import sunshine.domain.City;

import java.util.Map;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
public class WeatherSummaryGeneratorImpl implements WeatherSummaryGenerator {

    private final ChatClient chatClient;

    public WeatherSummaryGeneratorImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


    @Override
    public String generate(String city, double temperature, double apparentTemperature, double humidity, String skyCondition, String voice) {
//        String prompt = buildPrompt(
//                city,
//                temperature,
//                apparentTemperature,
//                humidity,
//                skyCondition
//        );

        var userTemplate = new PromptTemplate("""
                            Tell me the weather using input data and recommend the clothes when the temperature is {temperature}
                            """);
        var user = new UserMessage(userTemplate.render(Map.of("temperature", temperature)));

        var template = new SystemPromptTemplate("""
                너는 날씨 요약을 한 문장으로 만드는 어시스턴트야.
                
                요구사항:
                - 한 문장만 생성
                - 설명이나 추가 문장은 절대 붙이지 말 것.
                - 복장 추천 내용을 포함할 것.
                - 복장 추천은 반드시 recommendClothes(temperature) 툴을 호출해서 나온 clothes 목록에서만 선택해서 포함할 것.
                - 사용자의 요청에 {voice} 스타일로 사용자에게 정보를 주는 답변을 만들어줘.
                
                입력:
                - 조회한 도시: {city}
                - 온도: {temperature}
                - 체감온도: {apparentTemperature}
                - 습도: {humidity}
                - 날씨: {skyCondition}
                """);

        var system = template.createMessage(Map.of("voice", voice, "city", city, "temperature", temperature,
                "apparentTemperature", apparentTemperature, "humidity", humidity, "skyCondition", skyCondition));
        var prompt = new Prompt(user, system);

        ChatResponse response = chatClient.prompt(prompt)
                .tools(new FunctionConfiguration())
                .call()
                .chatResponse();

        log.info(response.getMetadata().getUsage().toString());

        return response.getResult().getOutput().getText();
    }

//    private String buildPrompt(
//            City city,
//            double temperature,
//            double apparentTemperature,
//            double humidity,
//            String skyCondition
//    ) {
//        return """
//                너는 날씨 요약을 한 문장으로 만드는 어시스턴트야.
//                아래 정보를 사용해서 한국어로 한 줄 요약 문장을 만들어줘.
//
//                요구사항:
//                - 존댓말 사용
//                - 한 문장만 생성
//                - 형식 예시:
//                  "현재 %s의 기온은 %.1f°C, 체감 온도는 %.1f°C, 습도는 %.1f%%이며, 하늘 상태는 맑음입니다."
//                - 설명이나 추가 문장은 절대 붙이지 말 것.
//
//                입력:
//                - 도시: %s
//                - 현재 기온: %.1f°C
//                - 체감 온도: %.1f°C
//                - 습도: %.1f%%
//                - 하늘 상태: %s
//                """
//                .formatted(
//                        city.getName(),
//                        city.getName(),
//                        temperature,
//                        apparentTemperature,
//                        humidity,
//                        skyCondition
//                );
//    }
}
