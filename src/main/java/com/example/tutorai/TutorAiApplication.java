package com.example.tutorai;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TutorAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TutorAiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() { return new ModelMapper(); }

    @Configuration
    @EnableAsync
    static class AsyncConfig {
        @Bean(name = "taskExecutor")
        public TaskExecutor taskExecutor() { return new SimpleAsyncTaskExecutor(); }
    }
}
