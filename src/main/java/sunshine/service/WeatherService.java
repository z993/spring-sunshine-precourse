package sunshine.service;

import org.springframework.stereotype.Service;
import sunshine.domain.City;
import sunshine.domain.CityWeather;
import sunshine.domain.Coordinate;
import sunshine.domain.CurrentWeatherDto;
import sunshine.infra.OpenMeteoClient;
import sunshine.infra.OpenMeteoResponseDto;

@Service
public class WeatherService {
    private final OpenMeteoClient openMeteoClient;
    private final WeatherSummaryFormatter summaryFormatter;
    private final WeatherCodeTranslator weatherCodeTranslator;

    public WeatherService(
            OpenMeteoClient openMeteoClient
    ) {
        this.openMeteoClient = openMeteoClient;
        this.summaryFormatter = new WeatherSummaryFormatter();
        this.weatherCodeTranslator = new WeatherCodeTranslator();
    }

    public CityWeather getCityWeather(String cityName) throws Exception {
        City city = toCity(cityName);
        Coordinate coordinate = city.toCoordinate();
        OpenMeteoResponseDto response = openMeteoClient.getCurrentWeather(coordinate);
        CurrentWeatherDto current = extractCurrentWeather(response);
        return createCityWeather(city, current);
    }

    private CityWeather createCityWeather(City city, CurrentWeatherDto current) {
        double temperature = current.getTemperature2m();
        double apparentTemperature = current.getApparentTemperature();
        double humidity = current.getRelativeHumidity2m();
        String skyCondition = weatherCodeTranslator.translate(current.getWeatherCode());
        String summary = summaryFormatter.format(
                city,
                temperature,
                apparentTemperature,
                humidity,
                skyCondition
        );
        return new CityWeather(
                city,
                temperature,
                apparentTemperature,
                humidity,       // ✅ 4번째
                skyCondition,   // ✅ 5번째
                summary
        );
    }


    private City toCity(String cityName) throws Exception {
        return City.fromName(cityName);
    }

    private CurrentWeatherDto extractCurrentWeather(OpenMeteoResponseDto response) throws Exception {
        if (response == null) {
            throw new Exception("Open-Meteo 응답이 null입니다.", null);
        }
        CurrentWeatherDto current = response.getCurrent();
        if (current == null) {
            if (response == null) {
                throw new Exception("Open-Meteo 현재 날씨 응답이 없습니다.", null);
            }
            return current;
        }
        return current;
    }
}
