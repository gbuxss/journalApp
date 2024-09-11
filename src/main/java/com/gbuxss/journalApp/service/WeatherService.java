package com.gbuxss.journalApp.service;

import com.gbuxss.journalApp.api.response.WeatherResponse;
import com.gbuxss.journalApp.cache.AppCache;
import com.gbuxss.journalApp.constants.Placeholder;
import com.gbuxss.journalApp.entity.ConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherService {

   @Autowired
    private RestTemplate restTemplate;

   @Autowired
    private AppCache appCache;

   @Value("${WEATHER_API_KEY}")
   private String apikey ;


    public WeatherResponse getWeather(String city) {
        String finalAPI= appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholder.CITY, city).replace(Placeholder.API_KEY, apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }



}
