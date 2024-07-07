package com.sunny.sunnyconfigclient.repository;

import com.sunny.sunnyconfigclient.model.ConfigMeta;

import java.util.Map;

public interface SunnyRepositoryChangeListener {
    void onChange(ChangeEvent event);
    
    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {}
}
