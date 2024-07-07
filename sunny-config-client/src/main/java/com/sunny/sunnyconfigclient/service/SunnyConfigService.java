package com.sunny.sunnyconfigclient.service;

import com.sunny.sunnyconfigclient.model.ConfigMeta;
import com.sunny.sunnyconfigclient.repository.SunnyRepository;

public interface SunnyConfigService {
    
    static SunnyConfigService getDefault(ConfigMeta configMeta) {
        SunnyRepository repository = SunnyRepository.getDefault(configMeta);
        return new SunnyConfigServiceImpl(repository.getConfig());
    }
    String[] getPropertyNames();
    
    String getProperty(final String name);
}
