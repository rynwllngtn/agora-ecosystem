package dev.rynwllngtn.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Main {

    @RestController
    class Controller {
        @GetMapping(value = "hello")
        public String hello() {
            return "Hello World!";
        }
    }

    void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}