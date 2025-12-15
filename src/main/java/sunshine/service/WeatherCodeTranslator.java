package sunshine.service;

import java.util.HashMap;
import java.util.Map;

public class WeatherCodeTranslator {
    private static final Map<Integer, String> CODE_TO_TEXT = new HashMap<>();

    static {
        CODE_TO_TEXT.put(0, "맑음");
        CODE_TO_TEXT.put(1, "대체로 맑음");
        CODE_TO_TEXT.put(2, "부분적으로 흐림");
        CODE_TO_TEXT.put(3, "흐림");
        CODE_TO_TEXT.put(45, "안개");
        CODE_TO_TEXT.put(48, "안개");
        CODE_TO_TEXT.put(51, "약한 이슬비");
        CODE_TO_TEXT.put(61, "약한 비");
        CODE_TO_TEXT.put(71, "약한 눈");
    }

    public String translate(int weatherCode) {
        String text = CODE_TO_TEXT.get(weatherCode);
        if (text != null) {
            return text;
        }
        return "알 수 없는 날씨";
    }
}
