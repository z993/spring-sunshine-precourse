package sunshine.service;

//import sunshine.domain.City;

public class WeatherSummaryFormatter {

    public String format(
            String city,
            double temperature,
            double apparentTemperature,
            double humidity,
            String skyCondition
    ) {
//        String cityName = city.getName();
        return String.format(
                "현재 %s의 기온은 %.1f°C, 체감 온도는 %.1f°C, 습도는 %.1f%%이며, 하늘 상태는 %s입니다.",
                city,
                temperature,
                apparentTemperature,
                humidity,
                skyCondition
        );
    }
}
