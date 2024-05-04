package com.example.healthapp.model;

import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import lombok.Data;

@Data
public class Address {
    @Field("formatted_address")
    private String formattedAddress;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    @Field("location")
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;  // Spring Data MongoDB supports GeoJson types
}

