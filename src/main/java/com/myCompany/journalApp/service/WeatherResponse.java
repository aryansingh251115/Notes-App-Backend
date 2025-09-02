package com.myCompany.journalApp.service;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class WeatherResponse {

    private Current current;


    @Getter
    @Setter
    public class Current {
        private int temperature;
        private int humidity;
        private int feelslike;
        private int visibility;
    }


}
