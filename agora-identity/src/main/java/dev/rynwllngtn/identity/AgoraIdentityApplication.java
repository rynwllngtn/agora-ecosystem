package dev.rynwllngtn.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "dev.rynwllngtn.identity",
        "dev.rynwllngtn.common"
})
public class AgoraIdentityApplication {
    static void main(String[] args) {
        SpringApplication.run(AgoraIdentityApplication.class, args);
    }
}