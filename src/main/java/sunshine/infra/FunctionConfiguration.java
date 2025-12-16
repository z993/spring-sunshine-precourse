package sunshine.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import sunshine.service.WeatherClothMatcher;

import java.util.List;

public class FunctionConfiguration {
    private static Logger log = LoggerFactory.getLogger(FunctionConfiguration.class);

    private WeatherClothMatcher matcher = new WeatherClothMatcher();

    @Tool(description = "recommend the clothes when the temperature is {temperature}")
    public List<String> recommendClothes(@ToolParam(description = "temperature") double temperature) {
        log.info("################# recommandCloths######");
        return matcher.recommend(temperature);
    }
}
