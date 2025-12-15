package sunshine.service;

import org.junit.jupiter.api.Test;
import sunshine.domain.City;
import sunshine.domain.CityWeather;
import sunshine.domain.CurrentWeatherDto;
import sunshine.infra.OpenMeteoClient;
import sunshine.infra.OpenMeteoResponseDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class WeatherServiceTest {
    @Test
    void seoul_날씨_조회시_기대하는_도메인_생성() throws Exception {
        OpenMeteoClient stubClient = coordinate -> {
            CurrentWeatherDto current = CurrentWeatherDto.builder()
                .temperature2m(3.4)
                .apparentTemperature(0.8)
                .relativeHumidity2m(75.0)
                .weatherCode(0)
                .build();

            OpenMeteoResponseDto response = new OpenMeteoResponseDto();
            response.setCurrent(current);
            return response;
        };

        WeatherService weatherService = new WeatherService(stubClient);

        CityWeather result = weatherService.getCityWeather("Seoul");

        assertThat(result.getCity()).isEqualTo(City.SEOUL);
        assertThat(result.getTemperature()).isEqualTo(3.4);
        assertThat(result.getApparentTemperature()).isEqualTo(0.8);
        assertThat(result.getHumidity()).isEqualTo(75.0);
        assertThat(result.getSkyCondition()).isEqualTo("맑음");
        assertThat(result.getSummary())
            .contains("Seoul")
            .contains("3.4°C")
            .contains("0.8°C")
            .contains("75.0%");
    }

    @Test
    void 지원하지_않는_도시가_입력되면_예외가_발생한다() {
        OpenMeteoClient stubClient = coordinate -> dummyResponse();
        WeatherService weatherService = new WeatherService(stubClient);

        assertThatThrownBy(() -> weatherService.getCityWeather("김포"))
            .isInstanceOf(Exception.class);
    }

    private OpenMeteoResponseDto dummyResponse() {
        return new OpenMeteoResponseDto();
    }

    @Test
    void seoul_입력시_외부_api에서_null_응답이면_예외가_발생한다() {
        OpenMeteoClient stubClient = coordinate -> null;
        WeatherService weatherService = new WeatherService(stubClient);

        assertThatThrownBy(() -> weatherService.getCityWeather("Seoul"))
            .isInstanceOf(Exception.class);
    }

}
