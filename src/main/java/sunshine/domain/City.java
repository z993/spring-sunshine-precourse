package sunshine.domain;

import java.util.Objects;

public enum City {

    SEOUL("Seoul", 37.5665, 126.9780),
    TOKYO("Tokyo", 35.6762, 139.6503),
    NEW_YORK("NewYork", 40.7128, -74.0060),
    PARIS("Paris", 48.8566, 2.3522),
    LONDON("London", 51.5074, -0.1278);

    private final String name;
    private final double latitude;
    private final double longitude;

    City(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public Coordinate toCoordinate() {
        return new Coordinate(latitude, longitude);
    }

    public static City fromName(String name) throws Exception {
        Objects.requireNonNull(name, "city name must not be null");
        String normalizedName = name.trim();
        for (City city : values()) {
            if (city.name.equalsIgnoreCase(normalizedName)) {
                return city;
            }
        }
        throw new Exception(name);
    }
}
