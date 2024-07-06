package com.sunny.sunnyconfigclient.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Optional;

public class SunnyConfigRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(@NotNull final AnnotationMetadata importingClassMetadata, final BeanDefinitionRegistry registry)
    {
        System.out.println("register PropertySourceProcessor");
        final Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(name -> name.equals(PropertySourceProcessor.class.getName()))
                .findFirst();
        if (first.isPresent()){
            System.out.println("PropertySourceProcessor already registered");
            return;
        }
        final AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(PropertySourceProcessor.class).getBeanDefinition();
        registry.registerBeanDefinition(PropertySourceProcessor.class.getName(), beanDefinition);
    }
}
