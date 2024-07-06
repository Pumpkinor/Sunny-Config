package com.sunny.sunnyconfigclient.service;

import java.util.Map;

public class SunnyConfigServiceImpl implements SunnyConfigService
{
    private Map<String, String> configs;
    
    public SunnyConfigServiceImpl(Map<String, String> configs){
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
}
