//package sunshine.Controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import sunshine.controller.WeatherController;
//import sunshine.domain.City;
//import sunshine.domain.CityWeather;
//import sunshine.service.WeatherService;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(controllers = WeatherController.class)
//public class WeatherControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private WeatherService weatherService;
//
//    @Test
//    void seoul_조회시_정상적으로_200과_JSON을_반환한다() throws Exception {
//        CityWeather cityWeather = new CityWeather(
//                City.SEOUL,
//                3.4,
//                0.8,
//                75.0,
//                "맑음",
//                "현재 Seoul의 기온은 3.4°C, 체감 온도는 0.8°C, 습도는 75.0%이며, 하늘 상태는 맑음입니다."
//        );
//
//        given(weatherService.getCityWeather("Seoul")).willReturn(cityWeather);
//
//        mockMvc.perform(get("/api/weather").param("city", "Seoul"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.city").value("Seoul"))
//                .andExpect(jsonPath("$.temperature").value(3.4))
//                .andExpect(jsonPath("$.skyCondition").value("맑음"));
//    }
//
//    @Test
//    void 지원하지_않는_도시_입력시_400을_반환한다() throws Exception {
//        // WeatherService에서 일반 Exception 던지는 경우
//        given(weatherService.getCityWeather("김포"))
//                .willThrow(new Exception("지원하지 않는 도시"));
//
//        mockMvc.perform(get("/api/weather").param("city", "김포"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.code").value("ERROR"))
//                .andExpect(jsonPath("$.message").value("지원하지 않는 도시"));
//    }
//}
