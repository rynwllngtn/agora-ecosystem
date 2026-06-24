package dev.rynwllngtn.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "dev.rynwllngtn.bank",
        "dev.rynwllngtn.common"
})
public class AgoraBankApplication {
    static void main(String[] args) {
        SpringApplication.run(AgoraBankApplication.class, args);
    }
}