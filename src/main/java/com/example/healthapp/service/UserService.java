package com.example.healthapp.service;

import com.example.healthapp.exception.NotFoundException;
import com.example.healthapp.model.Availability;
import com.example.healthapp.model.Language;
import com.example.healthapp.model.Therapist;
import com.example.healthapp.model.User;
import com.example.healthapp.repository.LanguageRepository;
import com.example.healthapp.repository.TherapistRepository;
import com.example.healthapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private TherapistRepository therapistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getLanguages() {
        return languageRepository.findAll();
    }

    public User updateUserProfile(String id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));;
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);
    }
    public Therapist updateTherapistProfile(String id, Therapist therapist) {
        Therapist existingTherapist = therapistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Therapist not found"));

        existingTherapist.setUsername(therapist.getUsername());
        existingTherapist.setEmail(therapist.getEmail());
        existingTherapist.setPassword(therapist.getPassword());

        existingTherapist.setAddress(therapist.getAddress());
        // Update existing therapist fields with new values
        existingTherapist.setMeetingLink(therapist.getMeetingLink());
        existingTherapist.setDescription(therapist.getDescription());
        existingTherapist.setGender(therapist.getGender());
        existingTherapist.setAge(therapist.getAge());
        existingTherapist.setLanguagesSpoken(therapist.getLanguagesSpoken());

        // Convert and set the start time and end time from the frontend
        for (Availability availability : therapist.getAvailability()) {
            System.out.println(availability.getStartTime());
            System.out.println(availability.convertTimeToFormattedString(availability.getStartTime()));
            availability.setStartTimeFromFrontend(availability.getStartTime());
            availability.setEndTimeFromFrontend(availability.getEndTime());
        }

        existingTherapist.setAvailability(therapist.getAvailability());

        existingTherapist.setExperience(therapist.getExperience());
        existingTherapist.setEducation(therapist.getEducation());
        existingTherapist.setLicenseCertification(therapist.getLicenseCertification());
        existingTherapist.setTreatmentApproaches(therapist.getTreatmentApproaches());
        existingTherapist.setFees(therapist.getFees());
        existingTherapist.setInsuranceAccepted(therapist.getInsuranceAccepted());
        existingTherapist.setAdditionalServices(therapist.getAdditionalServices());

        // Save the updated therapist to the database
        return therapistRepository.save(existingTherapist);
    }

    public User getUser(String id) {
        return userRepository.findById(id).get();
    }
    public Therapist getTherapistProfile(String id) {
        return therapistRepository.findById(id).orElse(null);
    }
}
