package com.gitapi.github.services;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 

@Configuration
public class AppConfig {
    @Bean
    public GithubService githubService() {
        return new GithubServiceImpl();
    }
}

