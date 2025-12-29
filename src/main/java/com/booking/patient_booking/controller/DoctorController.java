package com.booking.patient_booking.controller;

import com.booking.patient_booking.entity.Doctor;
import com.booking.patient_booking.enums.Role;
import com.booking.patient_booking.service.DoctorService;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pathology-lab/doctors")
public class DoctorController {
    Logger log = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    DoctorService doctorService;

    @PostMapping("/register")
    @PreAuthorize("name = Role.ADMIN, Role.SUPER_ADMIN")
    public ResponseEntity<Map<String, String>> registerNewDoctor(@RequestBody List<Doctor> doctor) {
        try {
            doctorService.saveDoctor(doctor);
            return ResponseEntity.ok(Map.of("status", "SUCCESS"));
        } catch (RuntimeException ex) {
            log.error("Doctor registration failed: {}", ex.getMessage(), ex);

            Map<String, String> response = new HashMap<>();
            response.put("status", "FAILED");
            response.put("message", "Doctor registration failed");

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {

        List<Doctor> doctors = doctorService.findAllDoctors();
        if (doctors == null || doctors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK); // 200
    }

    @GetMapping("/find")
    public ResponseEntity<?> findDoctor(@RequestParam(required = false) String query) {
        if (query == null || query.trim().isEmpty()) {
            List<Doctor> doctors = doctorService.findAllDoctors();
        }

        try {
            List<Doctor> doctors = doctorService.searchDoctor(query);

            if (doctors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No doctors found for: " + query);
            }

            return ResponseEntity.ok(doctors);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while searching: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<String> deleteExistingDoctor(@PathVariable String doctorId) {
        try {
            doctorService.deleteDoctor(doctorId);
            return ResponseEntity.ok("Doctor is successfully deleted");
        } catch (EntityNotFoundException ex) {
            log.error("Doctor not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Delete operation failed: " + ex.getMessage());
        } catch (RuntimeException ex) {
            log.error("Unable to delete doctor: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delete operation failed: " + ex.getMessage());
        }
    }
}
