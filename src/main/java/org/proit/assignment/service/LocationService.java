package org.proit.assignment.service;

import org.proit.assignment.constant.EndPointUrls;
import org.proit.assignment.model.WeatherApiResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class LocationService {

    @Autowired
    private RestTemplate restTemplate;

    public WeatherApiResponseModel getLocationsByCityName(String cityName) {
        String url;// = "https://geocoding-api.open-meteo.com/v1/search?name=" + name;
        url = EndPointUrls.GET_LOCATION_BY_CITY_NAME + cityName;
        return restTemplate.getForObject(url, WeatherApiResponseModel.class);
    }
}
