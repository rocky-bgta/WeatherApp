package org.proit.assignment.model;

import lombok.Data;

import java.util.List;

@Data
public class Locations {
    private List<Result> results;
    private double generationtime_ms;
}

@Data
class Result {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double elevation;
    private String feature_code;
    private String country_code;
    private int admin1_id;
    private Integer admin2_id;
    private Integer admin3_id;
    private Integer admin4_id;
    private String timezone;
    private int population;
    private int country_id;
    private String country;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
}

