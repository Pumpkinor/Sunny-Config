package com.sunny.sunnyconfigclient.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import com.sunny.sunnyconfigclient.model.ConfigMeta;
import com.sunny.sunnyconfigclient.model.Configs;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * default implement of SunnyRepository
 */
@Slf4j
public class SunnyRepositoryImpl implements SunnyRepository{
    private ConfigMeta configMeta;
    
    private Map<String ,Long> configsVersion = new HashMap<>();
    
    private Map<String, Map<String, String>> configs = new HashMap<>();
    
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    
    List<SunnyRepositoryChangeListener> listeners = new ArrayList<>();
    
    public SunnyRepositoryImpl(final ConfigMeta configMeta) {
        this.configMeta = configMeta;
        executorService.scheduleWithFixedDelay(this::heartBeat, 0, 1000, TimeUnit.MICROSECONDS);
    }
    
    public void addListener(SunnyRepositoryChangeListener listener) {
        listeners.add(listener);
    }
    
    private void heartBeat() {
        String key = configMeta.genKey();
        Long oldVersion = configsVersion.getOrDefault(key,-1L);
        Long version = HttpUtils.httpGet(configMeta.versionPath(), new TypeReference<>() {});
        if (version != null && version > oldVersion){
            configsVersion.put(configMeta.genKey(), version);
            log.info("configs changed, version:{}", version);
            Map<String, String> newConfigs = findAll();
            configs.put(key,newConfigs);
            log.info("configs:{}", configs.get(key));
//            配置发生变化的时候 发送一个事件通知spring cloud来更新配置bean
            listeners.forEach(l -> l.onChange(new SunnyRepositoryChangeListener.ChangeEvent(configMeta, newConfigs)));
        }
    }
    
    @Override
    public Map<String, String> getConfig() {     
        String key = configMeta.genKey();
        if (configs.containsKey(key)){
            return configs.get(key);
        }
        return findAll();
    }
    
    @NotNull
    private Map<String, String> findAll() {
        final List<Configs> configs = HttpUtils.httpGet(configMeta.listPath(), new TypeReference<>() {});
        return configs.stream().collect(Collectors.toMap(Configs::getPkey, Configs::getPval, (oldValue, newValue) -> newValue));
    }
}
