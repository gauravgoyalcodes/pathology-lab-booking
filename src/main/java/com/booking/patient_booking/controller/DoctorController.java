package com.booking.patient_booking.controller;

import com.booking.patient_booking.entity.Doctor;
import com.booking.patient_booking.service.DoctorService;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pathology-lab/doctors")
public class DoctorController {
    Logger log = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewDoctor(@RequestBody Doctor doctor) {
        try {
            doctorService.saveDoctor(doctor);
            return ResponseEntity.ok("Doctor is successfully registered");
        } catch (RuntimeException ex) {
            log.error("Doctor registration failed: {}", ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + ex.getMessage());
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

    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<String> deleteExistingDoctor(@PathVariable String doctorId) {
        try {
            doctorService.deleteDoctor(doctorId);
            return ResponseEntity.ok("Doctor is successfully deleted");
        } catch (EntityNotFoundException ex) {
            log.error("Doctor not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dele te operation failed: " + ex.getMessage());
        } catch (RuntimeException ex) {
            log.error("Unable to delete doctor: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delete operation failed: " + ex.getMessage());
        }
    }



}
