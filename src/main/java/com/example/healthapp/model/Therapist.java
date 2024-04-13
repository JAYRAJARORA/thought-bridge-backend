package com.example.healthapp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class Therapist extends User {
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String specialization;
    private String qualifications;
    @DBRef
    private List<Category> categories;
    private double avgRating;

    // optional fields for therapist
    private String meetingLink;
    private String description;
    private String gender;
    private Integer age;
    private List<String> languagesSpoken;
    private String availability;
    private Double experience;
    private String education;
    private String licenseCertification;
    private List<String> treatmentApproaches;
    private String fees;
    private List<String> insuranceAccepted;
    private String additionalServices;
}