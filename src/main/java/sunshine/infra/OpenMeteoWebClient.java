package sunshine.infra;

import org.springframework.web.reactive.function.client.WebClient;
import sunshine.domain.Coordinate;

public class OpenMeteoWebClient implements OpenMeteoClient {
    private static final String CURRENT_PARAMS =
            "temperature_2m,apparent_temperature,weather_code,relative_humidity_2m";

    private final WebClient webClient;

    public OpenMeteoWebClient(WebClient openMeteoWebClient) {
        this.webClient = openMeteoWebClient;
    }

    @Override
    public OpenMeteoResponseDto getCurrentWeather(Coordinate coordinate) throws Exception {
        try {
            return requestCurrentWeather(coordinate);
        } catch (Exception e) {
            throw new Exception("Open-Meteo API 호출 실패", e);
        }
    }

    private OpenMeteoResponseDto requestCurrentWeather(Coordinate coordinate) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/forecast")
                        .queryParam("latitude", coordinate.getLatitude())
                        .queryParam("longitude", coordinate.getLongitude())
                        .queryParam("current", CURRENT_PARAMS)
                        .build())
                .retrieve()
                .bodyToMono(OpenMeteoResponseDto.class)
                .block();
    }
}
