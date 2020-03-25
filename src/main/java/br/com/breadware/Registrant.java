package br.com.breadware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("br.com.breadware.properties")
public class Registrant {

    public static void main(String[] args) {
        SpringApplication.run(Registrant.class, args);
    }
}