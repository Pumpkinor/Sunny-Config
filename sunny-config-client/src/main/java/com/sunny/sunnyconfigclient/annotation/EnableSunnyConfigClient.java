package com.sunny.sunnyconfigclient.annotation;

import com.sunny.sunnyconfigclient.config.SunnyConfigRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * SunnyConfig client enable start annotation
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({SunnyConfigRegistrar.class})
public @interface EnableSunnyConfigClient {
}
