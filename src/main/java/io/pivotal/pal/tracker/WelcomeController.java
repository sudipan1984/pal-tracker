package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private String welcomeMessage;

    public WelcomeController(
            @Value("${welcome.message}") String welcomeMessage
    ) {
        this.welcomeMessage = welcomeMessage;
        System.out.println("This is for print in the log");
    }

    @GetMapping("/")
    public String sayHello() {
        return welcomeMessage;
    }
}
