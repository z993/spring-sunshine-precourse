package study;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.time.LocalDate;
import java.util.function.Function;

@Configuration
public class Functions {
//    @Tool(name = "addDaysFromToday")
//    @Description("Calculate a date after days from today")
//    @Bean
//    public Function<AddDayRequest, DateResponse> addDaysFromToday(){
//        return request -> {
//            var result = LocalDate.now().plusDays(request.days());
//            return new DateResponse(result.toString());
//        };
//    }

    @Tool(description = "Calculate a date after days from today")
    public DateResponse addDaysFromToday(AddDayRequest request){
        var result = LocalDate.now().plusDays(request.days());
        return new DateResponse(result.toString());
    }
}


