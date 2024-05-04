package com.example.healthapp.model;

import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class Availability {
    private String name;

    private String startTime;

    private String endTime;


    // Convert the time received from the frontend to the desired format
    public void setStartTimeFromFrontend(String time) {
        this.startTime = convertTimeToFormattedString(time);
    }

    public void setEndTimeFromFrontend(String time) {
        this.endTime = convertTimeToFormattedString(time);
    }

    // Convert the formatted string back to LocalTime when retrieving from MongoDB
    public LocalTime getStartTimeAsLocalTime() {
        return convertFormattedStringToTime(startTime);
    }

    public LocalTime getEndTimeAsLocalTime() {
        return convertFormattedStringToTime(endTime);
    }

    // Helper method to convert time string to "HH:mm AM/PM" format
    public String convertTimeToFormattedString(String time) {
        // Parse the time string to a LocalTime object
        LocalTime localTime = LocalTime.parse(time);

        // Format the LocalTime object into the desired string representation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a"); // "hh" for 12-hour format, "a" for AM/PM
        return localTime.format(formatter);
    }

    // Helper method to convert formatted string back to LocalTime
    private LocalTime convertFormattedStringToTime(String formattedTime) {
        // Parse the formatted string to LocalTime
        return LocalTime.parse(formattedTime, DateTimeFormatter.ofPattern("hh:mm a"));
    }
}
