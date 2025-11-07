package com.booking.patient_booking.service;

import com.booking.patient_booking.entity.Doctor;
import com.booking.patient_booking.repository.DoctorRepository;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private static final String PREFIX = "DR";
    Logger log = LoggerFactory.getLogger(DoctorService.class);

    @Autowired
    DoctorRepository doctorRepository;

    public void saveDoctor(Doctor doctor) {
        doctor.setDoctorId(generateDoctorId());
        log.info("Doctor to save in the db : " + doctor);
        doctorRepository.save(doctor);
    }

    public List<Doctor> findAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        log.info("List of all doctors : " + doctors);
        return doctors;
    }

    public void deleteDoctor(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));

        doctorRepository.delete(doctor);
        log.info("Doctor deleted successfully with ID: {}", doctorId);
    }

    public List<Doctor> searchDoctor(String query) {
        log.info("Searching doctors for query: {}", query);

        List<Doctor> doctors = doctorRepository.findDoctorByNameLike(query);

        // If not found by name → try specialization
        if (doctors == null || doctors.isEmpty()) {
            log.info("No match by name, trying specialization...");
            doctors = doctorRepository.findDoctorBySpecializationLike(query);
        }

        log.info("Final result count: {}", doctors.size());
        return doctors;
    }

    public String generateDoctorId() {

        String lastId = doctorRepository.findLastDoctorId(); // e.g., "DR0001"
        int nextNumber = 1;

        if (lastId != null && lastId.startsWith(PREFIX)) {
            String numberPart = lastId.substring(PREFIX.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return PREFIX + String.format("%04d", nextNumber);  // e.g., "DR0002"
    }
}
