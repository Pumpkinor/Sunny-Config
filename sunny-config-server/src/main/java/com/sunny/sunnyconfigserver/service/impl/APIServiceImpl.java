package com.sunny.sunnyconfigserver.service.impl;

import com.sunny.sunnyconfigserver.dao.ConfigsMapper;
import com.sunny.sunnyconfigserver.model.Configs;
import com.sunny.sunnyconfigserver.service.APIService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class APIServiceImpl implements APIService {
    private final ConfigsMapper configsMapper;
    
    private Map<String, Long> VERSIONS = new HashMap<>();
    
    
    public APIServiceImpl(ConfigsMapper configsMapper) {
        this.configsMapper = configsMapper;
    }
    
    @Override
    public List<Configs> list(final String app, final String env, final String ns) {
        return null;
    }
    
    @Override
    public List<Configs> update(final String app, final String env, final String ns, final Map<String, String> params) {
        params.forEach((k, v) -> {
            insertOrUpdate(new Configs(app, env, ns, k, v));
        });
        VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());
        return configsMapper.list(app, env, ns);    }
    
    @Override
    public long version(final String app, final String env, final String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }
    
    private void insertOrUpdate(Configs configs) {
        Configs conf = configsMapper.select(configs.getApp(), configs.getEnv(),
                configs.getNs(), configs.getPkey());
        if(conf == null) {
            configsMapper.insert(configs);
        } else {
            configsMapper.update(configs);
        }
    }
}
