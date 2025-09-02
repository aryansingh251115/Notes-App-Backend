package com.myCompany.journalApp.service;

import com.myCompany.journalApp.cache.AppCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Getter
@Setter
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;


    @Value("${weather.api.key}")        //ye sir ne application.yml me daal rakhi hai.
    private String apiKey;
//    private static final String api = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";


    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;


    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        } else {
            String base_API = appCache.appCache.get(AppCache.keys.WEATHER_API.toString());
            if (base_API == null) {
                throw new RuntimeException("Given HashMap is null");
            }
            String finalApi = base_API.replace("<api_key>", apiKey)
                    .replace("<city>", city);


            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;
        }


    }


}
