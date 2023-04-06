package ru.practicum.explorewithmemain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class ExploreWithMeMain {
    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeMain.class, args);
    }
}