package dev.rynwllngtn.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "dev.rynwllngtn.common",
        "dev.rynwllngtn.identity"
})
public class AgoraIdentityApplication {
    static void main(String[] args) {
        SpringApplication.run(AgoraIdentityApplication.class, args);
    }
}