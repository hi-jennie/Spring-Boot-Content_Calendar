package dev.danvega.content_calendar.controller;

import dev.danvega.content_calendar.config.ContentCalendarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final ContentCalendarProperties properties;

    public HomeController(ContentCalendarProperties properties) {
        this.properties = properties;
    }

    // when request localhost:8080/, it will return the properties in the ContentCalendarProperties class
    @GetMapping("/")
    public ContentCalendarProperties home() {
        return properties;
    }


    /*
    // @Notion
    @Value("${cc.welcomeMessage: Hello}")
    private String welcomeMessage;
    @Value("${about: spring boot")
    private String about;

    @GetMapping("/")
    public String home() {
        return welcomeMessage;
    }

    @GetMapping("/about")
    public Map<String, String> about() {
        return Map.of("WelcomeMessage", welcomeMessage, "About", about);
    }
     */

}
