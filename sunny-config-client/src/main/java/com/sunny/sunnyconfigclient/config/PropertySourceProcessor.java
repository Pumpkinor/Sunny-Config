package com.sunny.sunnyconfigclient.config;

import com.sunny.sunnyconfigclient.config.SunnyPropertySource;
import com.sunny.sunnyconfigclient.service.SunnyConfigService;
import com.sunny.sunnyconfigclient.service.SunnyConfigServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

public class PropertySourceProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered
{
    private final static String SUNNY_PROPERTY_SOURCE = "SunnyPropertySource";
    private final static String SUNNY_PROPERTY_SOURCES = "SunnyPropertySources";
    Environment environment;
    @Override
    public void postProcessBeanFactory(@NotNull final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        if (propertySources.contains(SUNNY_PROPERTY_SOURCES)){
            return;
        }
//        本地没有SunnyPropertySource，则通过http去sunny-config-server获取配置 TODO
        Map<String, String> config = new HashMap<>();
        config.put("sunny.app", "app002");
        config.put("sunny.a", "a002");
        config.put("sunny.b", "b002");
        config.put("sunny.c", "c002");
        SunnyConfigService sunnyConfigService = new SunnyConfigServiceImpl(config);
        SunnyPropertySource sunnyPropertySource = new SunnyPropertySource(SUNNY_PROPERTY_SOURCE, sunnyConfigService);
        CompositePropertySource compositePropertySource = new CompositePropertySource(SUNNY_PROPERTY_SOURCES);
        compositePropertySource.addPropertySource(sunnyPropertySource);
        configurableEnvironment.getPropertySources().addFirst(compositePropertySource);
    }
    
    @Override
    public void setEnvironment(@NotNull final Environment environment) {
        this.environment = environment;
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
}
