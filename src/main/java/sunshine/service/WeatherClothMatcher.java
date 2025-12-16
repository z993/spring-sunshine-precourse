package sunshine.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class WeatherClothMatcher {

    public List<String> recommend(double temperature, boolean raining) {
        List<String> clothes = new ArrayList<>(matchBaseClothes(temperature));
        if (raining) {
            addRainItems(clothes);
        }
        return clothes;
    }
    public List<String> recommend(double temperature) {
        List<String> clothes = new ArrayList<>(matchBaseClothes(temperature));

        return clothes;
    }

    private List<String> matchBaseClothes(double temperature) {
        if (temperature >= 28) {
            return List.of("민소매 또는 반팔 티셔츠", "반바지", "샌들");
        }
        if (temperature >= 23) {
            return List.of("반팔 티셔츠", "얇은 셔츠", "면바지");
        }
        if (temperature >= 20) {
            return List.of("긴팔 티셔츠", "가벼운 가디건", "청바지");
        }
        if (temperature >= 17) {
            return List.of("얇은 니트 또는 맨투맨", "청바지");
        }
        if (temperature >= 12) {
            return List.of("가벼운 자켓", "니트", "청바지");
        }
        if (temperature >= 9) {
            return List.of("코트", "니트", "슬랙스");
        }
        if (temperature >= 5) {
            return List.of("두꺼운 코트", "니트", "기모 바지");
        }
        return List.of("패딩", "목도리", "장갑");
    }

    private void addRainItems(List<String> clothes) {
        clothes.add("우산");
        clothes.add("방수 신발 또는 장화");
    }
}
