package com.sunny.sunyconfigdemo;

import com.sunny.sunnyconfigclient.annotation.EnableSunnyConfigClient;
import com.sunny.sunyconfigdemo.config.SunnyConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableConfigurationProperties(SunnyConfigProperty.class)
@EnableSunnyConfigClient
public class SunyConfigDemoApplication {
    @Value("${sunny.a}")
    String a;
    
    @Autowired
    SunnyConfigProperty sunnyConfigProperty;
    
    @Autowired
    Environment environment;
    
    public static void main(String[] args) {
        SpringApplication.run(SunyConfigDemoApplication.class, args);
    }
    
    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            System.err.println(a);
            System.err.println(sunnyConfigProperty);
        };
    }
}
