package sunshine.service;

import org.springframework.stereotype.Service;
//import sunshine.domain.City;
import sunshine.domain.CityWeather;
import sunshine.domain.Coordinate;
import sunshine.domain.CurrentWeatherDto;
import sunshine.infra.LocationCoordinateResolver;
import sunshine.infra.OpenMeteoClient;
import sunshine.infra.OpenMeteoResponseDto;

@Service
public class WeatherService {
    private final OpenMeteoClient openMeteoClient;
    private final WeatherSummaryFormatter summaryFormatter;
    private final WeatherCodeTranslator weatherCodeTranslator;
    private final WeatherSummaryGenerator summaryGenerator;
    private final LocationCoordinateResolver coordinateResolver;


    public WeatherService(
            OpenMeteoClient openMeteoClient,
            WeatherSummaryGenerator summaryGenerator, LocationCoordinateResolver coordinateResolver) {
        this.openMeteoClient = openMeteoClient;
        this.summaryGenerator = summaryGenerator;
        this.coordinateResolver = coordinateResolver;
        this.summaryFormatter = new WeatherSummaryFormatter();
        this.weatherCodeTranslator = new WeatherCodeTranslator();
    }

//    public CityWeather getCityWeather(String cityName) throws Exception {
//        City city = toCity(cityName);
//        Coordinate coordinate = city.toCoordinate();
//        OpenMeteoResponseDto response = openMeteoClient.getCurrentWeather(coordinate);
//        CurrentWeatherDto current = extractCurrentWeather(response);
//        return createCityWeather(city, current);
//    }

    public CityWeather getCityWeather(String city, String voice) throws Exception {
//        City city = toCity(cityName);
//        Coordinate coordinate = city.toCoordinate();
//        Coordinate coordinate = coordinateResolver.resolve(city.getName());
        Coordinate coordinate = coordinateResolver.resolve(city);
        OpenMeteoResponseDto response = openMeteoClient.getCurrentWeather(coordinate);
        CurrentWeatherDto current = extractCurrentWeather(response);
        return createCityWeather(city, current, voice);
    }

//    private CityWeather createCityWeather(City city, CurrentWeatherDto current) {
//        double temperature = current.getTemperature2m();
//        double apparentTemperature = current.getApparentTemperature();
//        double humidity = current.getRelativeHumidity2m();
//        String skyCondition = weatherCodeTranslator.translate(current.getWeatherCode());
//        String summary = summaryFormatter.format(
//                city,
//                temperature,
//                apparentTemperature,
//                humidity,
//                skyCondition
//        );
//        return new CityWeather(
//                city,
//                temperature,
//                apparentTemperature,
//                humidity,       // ✅ 4번째
//                skyCondition,   // ✅ 5번째
//                summary
//        );
//    }

    private CityWeather createCityWeather(String city, CurrentWeatherDto current, String voice) {
        double temperature = current.getTemperature2m();
        double apparentTemperature = current.getApparentTemperature();
        double humidity = current.getRelativeHumidity2m();
        String skyCondition = weatherCodeTranslator.translate(current.getWeatherCode());
        String summary;
        if("system".equals(voice)){
            summary = summaryFormatter.format(
                    city,
                    temperature,
                    apparentTemperature,
                    humidity,
                    skyCondition
            );
        } else {
            summary = summaryGenerator.generate(
                    city,
                    temperature,
                    apparentTemperature,
                    humidity,
                    skyCondition,
                    voice
            );
        }

        return new CityWeather(
                city,
                temperature,
                apparentTemperature,
                humidity,       // ✅ 4번째
                skyCondition,   // ✅ 5번째
                summary
        );
    }

//    private City toCity(String cityName) throws Exception {
//        return City.fromName(cityName);
//    }

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
