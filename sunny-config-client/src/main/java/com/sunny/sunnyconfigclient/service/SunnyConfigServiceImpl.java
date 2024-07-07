package com.sunny.sunnyconfigclient.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
public class SunnyConfigServiceImpl implements SunnyConfigService
{
    private ApplicationContext applicationContext;
    private Map<String, String> configs;
    
    public SunnyConfigServiceImpl(ApplicationContext applicationContext, Map<String, String> configs){
        this.applicationContext = applicationContext;
        this.configs = configs;
    }
    @Override
    public String[] getPropertyNames() {
        return this.configs.keySet().toArray(new String[0]);
    }
    
    @Override
    public String getProperty(final String name) {
        return this.configs.get(name);
    }
    
    @Override
    public void onChange(final ChangeEvent event) {
        Set<String> keys = calcChangedKeys(this.configs, event.config());
        if(keys.isEmpty()) {
            log.info("[CONFIG] calcChangedKeys return empty, ignore update.");
            return;
        }
        this.configs = event.config();
        if(!configs.isEmpty()) {
            log.info("[CONFIG] fire an EnvironmentChangeEvent with keys: {}", keys);
            applicationContext.publishEvent(new EnvironmentChangeEvent(keys));
        }
    }
    
    private Set<String> calcChangedKeys(Map<String, String> oldConfigs, Map<String, String> newConfigs) {
        if(oldConfigs.isEmpty()) return newConfigs.keySet();
        if(newConfigs.isEmpty()) return oldConfigs.keySet();
        Set<String> news = newConfigs.keySet().stream()
                .filter(key -> !newConfigs.get(key).equals(oldConfigs.get(key)))
                .collect(Collectors.toSet());
        oldConfigs.keySet().stream().filter(key -> !newConfigs.containsKey(key)).forEach(news::add);
        return news;
    }
}
