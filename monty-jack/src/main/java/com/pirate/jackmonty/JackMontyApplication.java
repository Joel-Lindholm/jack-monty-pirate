package com.pirate.jackmonty;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JackMontyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JackMontyApplication.class, args);
    }

}
