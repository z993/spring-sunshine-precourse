package sunshine.service;

//import sunshine.domain.City;

public interface WeatherSummaryGenerator {
    String generate(
            String city,
            double temperature,
            double apparentTemperature,
            double humidity,
            String skyCondition,
            String voice
    );
}
