package dev.danvega.content_calendar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// this represents that this class will only be loaded when the profile is not dev
@Profile("!dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello, world2");
    }
}
