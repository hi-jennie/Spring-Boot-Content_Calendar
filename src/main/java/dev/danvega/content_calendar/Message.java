package dev.danvega.content_calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

// this is class level annotation
@Component
public class Message {

    @Bean // this is method level annotation
    public String getMessage() {
        return "Hello, World!";
    }


}
