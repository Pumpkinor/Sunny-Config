package com.sunny.sunnyconfigclient.config;

import com.sunny.sunnyconfigclient.service.SunnyConfigService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.EnumerablePropertySource;

public class SunnyPropertySource extends EnumerablePropertySource<SunnyConfigService> {
    
    
    public SunnyPropertySource(final String name, final SunnyConfigService source) {
        super(name, source);
    }
    
    protected SunnyPropertySource(final String name) {
        super(name);
    }
    
    @NotNull
    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }
    
    @Override
    public Object getProperty(@NotNull final String name) {
        return source.getProperty(name);
    }
}
