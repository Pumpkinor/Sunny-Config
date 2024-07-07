package com.sunny.sunyconfigdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sunny")
@Data
public class SunnyConfigProperty {
    private String a;
    private String b;
    private String c;
}
