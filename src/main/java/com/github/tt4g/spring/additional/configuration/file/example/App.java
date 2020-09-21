package com.github.tt4g.spring.additional.configuration.file.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(App.class);

        springApplication.addListeners(new AddAdditionalPropertySourceListener());

        springApplication.run(args);
    }

}
