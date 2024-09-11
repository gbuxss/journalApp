package com.gbuxss.journalApp.cache;

import com.gbuxss.journalApp.entity.ConfigEntity;
import com.gbuxss.journalApp.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys {
        WEATHER_API;
    }

    @Autowired
    private ConfigRepository configRepository;

    public Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigEntity> allKeyValuePairs = configRepository.findAll();
        for (ConfigEntity configEntity : allKeyValuePairs) {

            appCache.put(configEntity.getKey(), configEntity.getValue());
        }
    }
}


