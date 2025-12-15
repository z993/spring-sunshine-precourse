package sunshine.infra;

import sunshine.domain.Coordinate;

public interface OpenMeteoClient {
    OpenMeteoResponseDto getCurrentWeather(Coordinate coordinate) throws Exception;
}
