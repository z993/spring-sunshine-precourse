package sunshine.domain;

public class CurrentWeatherDto {
    private double temperature2m;
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
