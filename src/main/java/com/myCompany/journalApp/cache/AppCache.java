package com.myCompany.journalApp.cache;


import com.myCompany.journalApp.entity.ConfigJournalAppEntity;
import com.myCompany.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API
    }
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public HashMap<String ,String> appCache;

    @PostConstruct
    public void init(){
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all) {
            appCache.put(configJournalAppEntity.getKey() , configJournalAppEntity.getValue());
        }
        if(appCache == null || appCache.isEmpty()){
            throw new RuntimeException("Init Method have empty hashmap");
        }

    }

}
