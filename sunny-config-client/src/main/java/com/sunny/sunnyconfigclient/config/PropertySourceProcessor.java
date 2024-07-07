package com.sunny.sunnyconfigclient.config;

import com.sunny.sunnyconfigclient.model.ConfigMeta;
import com.sunny.sunnyconfigclient.service.SunnyConfigService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

public class PropertySourceProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered
{
    private final static String SUNNY_PROPERTY_SOURCE = "SunnyPropertySource";
    private final static String SUNNY_PROPERTY_SOURCES = "SunnyPropertySources";
    Environment environment;
    @Override
    public void postProcessBeanFactory(@NotNull final ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        TODO 其实实现这个BeanFactoryPostProcessor接口重写postProcessBeanFactory方法好像意义不大 这部分逻辑写到其他位置也都ok
//        毕竟所有操作都是基于environment对象来的 
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        if (propertySources.contains(SUNNY_PROPERTY_SOURCES)){
            return;
        }
//        本地没有SunnyPropertySource，则通过http去sunny-config-server获取配置 TODO
        String app = configurableEnvironment.getProperty("sunny.app", "app1");
        String env = configurableEnvironment.getProperty("sunny.env", "dev");
        String nameSpace = configurableEnvironment.getProperty("sunny.ns", "public");
        String configServer = configurableEnvironment.getProperty("sunny.config-server", "http://127.0.0.1:9129");
        final ConfigMeta configMeta = new ConfigMeta(app, env, nameSpace, configServer);
        SunnyConfigService sunnyConfigService = SunnyConfigService.getDefault(configMeta);
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
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
