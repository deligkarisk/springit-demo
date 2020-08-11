package com.kosmas.springit;

import com.kosmas.springit.config.SpringitProperties;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
@EnableConfigurationProperties(SpringitProperties.class)
public class SpringitApplication {

    @Autowired
    private SpringitProperties springitProperties;

    public static void main(String[] args) {

        SpringApplication.run(SpringitApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            System.out.println("WelcomeMessage: " + springitProperties.getWelcomeMessage());
        };
    };

    @Bean
    PrettyTime prettyTime() {
        return new PrettyTime();
    }

    // TODO * Configuring this bean should not be needed once Spring Boot's Thymeleaf starter includes configuration
// TODO   for thymeleaf-extras-springsecurity5 (instead of thymeleaf-extras-springsecurity4)
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    }



