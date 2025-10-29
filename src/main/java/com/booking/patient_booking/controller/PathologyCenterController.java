package com.booking.patient_booking.controller;

import com.booking.patient_booking.entity.PathologyCenters;
import com.booking.patient_booking.service.PathologyCenterService;

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
@RequestMapping("/pathology-lab/centers")
public class PathologyCenterController {
    Logger log = LoggerFactory.getLogger(DoctorController.class);

    @Autowired
    PathologyCenterService pathologyCenterService;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewCenter(@RequestBody PathologyCenters center) {
        try {
            pathologyCenterService.saveCenter(center);
            return ResponseEntity.ok("Center is successfully registered");
        } catch (RuntimeException ex) {
            log.error("Center registration failed: {}", ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + ex.getMessage());
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<PathologyCenters>> getAllCenters() {

        List<PathologyCenters> centers = pathologyCenterService.findAllCenters();
        if (centers == null || centers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(centers, HttpStatus.OK); // 200
    }

    @DeleteMapping("/delete/{centerId}")
    public ResponseEntity<String> deleteExistingCenter(@PathVariable String centerId) {
        try {
            pathologyCenterService.deleteCenter(centerId);
            return ResponseEntity.ok("Center is successfully deleted");
        } catch (EntityNotFoundException ex) {
            log.error("Center not found: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Delete operation failed: " + ex.getMessage());
        } catch (RuntimeException ex) {
            log.error("Unable to delete center: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Delete operation failed: " + ex.getMessage());
        }
    }
}
