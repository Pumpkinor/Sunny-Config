package com.sunny.sunyconfigdemo;

import com.sunny.sunyconfigdemo.config.SunnyConfigProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class WebController {
    @Value("${sunny.a}")
    String a;
    @Autowired
    SunnyConfigProperty sunnyConfigProperty;
    @GetMapping("/demo")
    public String demo()
    {
        return "a : " + a + " \n sunny : " + sunnyConfigProperty;
    }
}
