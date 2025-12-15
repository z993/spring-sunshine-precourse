package sunshine.domain;

public class CityWeatherResponse {

    private final String city;
    private final double temperature;
    private final double apparentTemperature;
    private final String skyCondition;
    private final double humidity;
    private final String summary;

    public CityWeatherResponse(
            String city,
            double temperature,
            double apparentTemperature,
            String skyCondition,
            double humidity,
            String summary
    ) {
        this.city = city;
        this.temperature = temperature;
        this.apparentTemperature = apparentTemperature;
        this.skyCondition = skyCondition;
        this.humidity = humidity;
        this.summary = summary;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getApparentTemperature() {
        return apparentTemperature;
    }

    public String getSkyCondition() {
        return skyCondition;
    }

    public double getHumidity() {
        return humidity;
    }

    public String getSummary() {
        return summary;
    }
}
