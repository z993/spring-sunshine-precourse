package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.domain.CityWeather;
import sunshine.domain.CityWeatherResponse;
import sunshine.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<CityWeatherResponse> getWeather(
            @RequestParam("city") String cityName,
            @RequestParam(value = "voice", defaultValue = "system") String voice
    ) throws Exception {
        CityWeather cityWeather = weatherService.getCityWeather(cityName, voice);
        CityWeatherResponse response = toResponse(cityWeather);
        return ResponseEntity.ok(response);
    }



    private CityWeatherResponse toResponse(CityWeather cityWeather) {
        return new CityWeatherResponse(
//            cityWeather.getCity().getName(),
            cityWeather.getCity(),
            cityWeather.getTemperature(),
            cityWeather.getApparentTemperature(),
            cityWeather.getSkyCondition(),
            cityWeather.getHumidity(),
            cityWeather.getSummary()
        );
    }
}
