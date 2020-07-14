package com.kosmas.springit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("springitagain")
public class SpringitProperties {

    /**
    * This is a welcome message
     */
    private String welcomeMessage = "Hello world";

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
}
