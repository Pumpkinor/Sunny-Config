package com.sunny.sunnyconfigclient.service;

public interface SunnyConfigService {
    String[] getPropertyNames();
    
    String getProperty(final String name);
}
