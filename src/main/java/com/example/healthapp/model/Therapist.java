package com.example.healthapp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class Therapist extends User {
    private String name;
    private String phoneNumber;

    @Field("address")
    private Address address;

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
    private List<Language> languagesSpoken;
    private Availability[] availability;
    private Double experience;
    private String education;
    private String licenseCertification;
    private String treatmentApproaches;
    private String fees;
    private String insuranceAccepted;
    private String additionalServices;
}