package sunshine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentWeatherDto {
    private double temperature2m;
    @JsonProperty("apparent_temperature")
    private double apparentTemperature;
    private double relativeHumidity2m;
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
