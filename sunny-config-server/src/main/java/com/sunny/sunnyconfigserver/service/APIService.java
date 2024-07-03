package com.sunny.sunnyconfigserver.service;

import com.sunny.sunnyconfigserver.model.Configs;

import java.util.List;
import java.util.Map;

public interface APIService {
    List<Configs> list(String app, String env, String ns);
    List<Configs> update(String app, String env, String ns, Map<String, String> params);
    long version(String app, String env, String ns);
}
