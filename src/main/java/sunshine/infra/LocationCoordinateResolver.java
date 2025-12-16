package sunshine.infra;

import sunshine.domain.Coordinate;

public interface LocationCoordinateResolver {
    Coordinate resolve(String locationName);
}
