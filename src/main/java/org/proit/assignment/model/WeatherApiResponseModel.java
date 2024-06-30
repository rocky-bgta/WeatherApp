package org.proit.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class WeatherApiResponseModel {
    private List<LocationDetailsModel> results;
    private double generationtime_ms;
}

