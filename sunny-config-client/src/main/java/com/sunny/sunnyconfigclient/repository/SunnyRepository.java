package com.sunny.sunnyconfigclient.repository;

import com.sunny.sunnyconfigclient.model.ConfigMeta;

import java.util.Map;

/**
 * get config from remote config server
 */
public interface SunnyRepository {
    static SunnyRepository getDefault(ConfigMeta configMeta) {
        return new SunnyRepositoryImpl(configMeta);
    }
    
    Map<String, String> getConfig();
    
    void addListener(SunnyRepositoryChangeListener listener);
}
