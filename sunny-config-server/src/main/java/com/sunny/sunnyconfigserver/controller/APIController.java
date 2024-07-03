package com.sunny.sunnyconfigserver.controller;

import com.sunny.sunnyconfigserver.model.Configs;
import com.sunny.sunnyconfigserver.service.APIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class APIController {
    private final APIService apiService;
    
    public APIController(final APIService apiService) {
        this.apiService = apiService;
    }
    
    @GetMapping("/list")
    public List<Configs> list(String app, String env, String ns) {
        return apiService.list(app, env, ns);
    }
    
    @RequestMapping("/update")
    public List<Configs> update(@RequestParam("app") String app,
                                @RequestParam("env") String env,
                                @RequestParam("ns") String ns,
                                @RequestBody Map<String, String> params) {
       return apiService.update(app, env, ns, params);
    }
    
    @GetMapping("/version")
    public long version(String app, String env, String ns) {
        return apiService.version(app, env, ns);
    }
}
