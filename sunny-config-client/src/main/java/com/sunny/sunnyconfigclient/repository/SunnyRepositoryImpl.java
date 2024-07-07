package com.sunny.sunnyconfigclient.repository;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import com.sunny.sunnyconfigclient.model.ConfigMeta;
import com.sunny.sunnyconfigclient.model.Configs;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * default implement of SunnyRepository
 */

public class SunnyRepositoryImpl implements SunnyRepository{
    private ConfigMeta configMeta;
    public SunnyRepositoryImpl(final ConfigMeta configMeta) {
        this.configMeta = configMeta;
    }
    
    @Override
    public Map<String, String> getConfig() {         
        final List<Configs> configs = HttpUtils.httpGet(configMeta.listPath(), new TypeReference<>() {});
        return configs.stream().collect(Collectors.toMap(Configs::getPkey, Configs::getPval, (oldValue, newValue) -> newValue));
    }
}
