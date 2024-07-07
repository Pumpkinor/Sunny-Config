package com.sunny.sunnyconfigclient.service;

import com.sunny.sunnyconfigclient.model.ConfigMeta;
import com.sunny.sunnyconfigclient.repository.SunnyRepository;
import com.sunny.sunnyconfigclient.repository.SunnyRepositoryChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

public interface SunnyConfigService extends SunnyRepositoryChangeListener {
    
    static SunnyConfigService getDefault(ApplicationContext applicationContext, ConfigMeta configMeta) {
        SunnyRepository repository = SunnyRepository.getDefault(configMeta);
        Map<String, String> configs = repository.getConfig();
        final SunnyConfigService configService = new SunnyConfigServiceImpl(applicationContext, configs);
        repository.addListener(configService);
        return configService;
    }
    String[] getPropertyNames();
    
    String getProperty(final String name);
}
