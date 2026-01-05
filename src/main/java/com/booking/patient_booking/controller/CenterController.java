package com.booking.patient_booking.controller;

import com.booking.patient_booking.entity.Centers;
import com.booking.patient_booking.service.CenterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pathology-lab/centers")
public class CenterController {
    Logger log = LoggerFactory.getLogger(CenterController.class);

    @Autowired
    CenterService centerService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerNewCenter(@RequestBody Centers center) {
        try {
            centerService.addNewCenter(center);
            return ResponseEntity.ok(Map.of("status", "SUCCESS"));
        } catch (RuntimeException ex) {
            log.error("Center registration failed: {}", ex.getMessage(), ex);

            Map<String, String> response = new HashMap<>();
            response.put("status", "FAILED");
            response.put("message", "Center registration failed");

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<Centers>> getAllCenters() {

        List<Centers> centers = centerService.findAllCenters();
        if (centers == null || centers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(centers, HttpStatus.OK); // 200
    }
}
