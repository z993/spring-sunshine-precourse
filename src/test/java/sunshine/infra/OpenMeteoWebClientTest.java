package sunshine.infra;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import sunshine.domain.Coordinate;
import sunshine.domain.CurrentWeatherDto;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OpenMeteoWebClientTest {
    private MockWebServer mockWebServer;
    private OpenMeteoWebClient client;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();
        client = new OpenMeteoWebClient(webClient);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void seoul_좌표로_현재_날씨를_조회하면_dto로_매핑된다() throws Exception {
        enqueueSeoulWeatherResponse();

        Coordinate seoul = new Coordinate(37.5665, 126.9780);
        OpenMeteoResponseDto response = client.getCurrentWeather(seoul);

        assertResponseMapped(response);
        assertRequestSentCorrectly();
    }

    private void enqueueSeoulWeatherResponse() {
        String body = """
            {
              "latitude": 37.5665,
              "longitude": 126.9780,
              "current": {
                "temperature_2m": 3.4,
                "apparent_temperature": 0.8,
                "relative_humidity_2m": 75,
                "weather_code": 0
              }
            }
            """;
        MockResponse response = new MockResponse()
                .setBody(body)
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(response);
    }

    private void assertResponseMapped(OpenMeteoResponseDto response) {
        assertThat(response).isNotNull();
        assertThat(response.getCurrent()).isNotNull();

        CurrentWeatherDto current = response.getCurrent();
        assertThat(current.getTemperature2m()).isEqualTo(3.4);
        assertThat(current.getApparentTemperature()).isEqualTo(0.8);
        assertThat(current.getRelativeHumidity2m()).isEqualTo(75.0);
        assertThat(current.getWeatherCode()).isEqualTo(0);
    }

    private void assertRequestSentCorrectly() throws InterruptedException {
        RecordedRequest request = mockWebServer.takeRequest();
        String path = request.getPath();
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(path).startsWith("/v1/forecast");
        assertThat(path).contains("latitude=37.5665");
        assertThat(path).contains("longitude=126.978");
        assertThat(path).contains("current=temperature_2m,apparent_temperature,weather_code,relative_humidity_2m");
    }
}
