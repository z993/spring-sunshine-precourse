package sunshine.infra;

import sunshine.domain.CurrentWeatherDto;

public class OpenMeteoResponseDto {
    private double latitude;
    private double longitude;
    private CurrentWeatherDto current;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public CurrentWeatherDto getCurrent() {
        return current;
    }
}
