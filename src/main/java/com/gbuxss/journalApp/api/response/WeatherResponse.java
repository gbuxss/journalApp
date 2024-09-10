package com.gbuxss.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse {
    private Current current;


    // Nested Current class
    @Getter
    @Setter
    public  class Current {
        private int temperature;
        private int feelslike;
        @JsonProperty("weather_description")
        private List<String> weatherDescription;
    }
}

