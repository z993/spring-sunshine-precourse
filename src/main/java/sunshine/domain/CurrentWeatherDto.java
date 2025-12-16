package sunshine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class CurrentWeatherDto {
    @JsonProperty("temperature_2m")
    private double temperature2m;
    @JsonProperty("apparent_temperature")
    private double apparentTemperature;
    @JsonProperty("relative_humidity_2m")
    private double relativeHumidity2m;
    @JsonProperty("weather_code")
    private int weatherCode;

    public double getTemperature2m() {
        return temperature2m;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public double getRelativeHumidity2m() {
        return relativeHumidity2m;
    }

    public int getWeatherCode() {
        return weatherCode;
    }
}
