package com.booking.patient_booking.controller;

import com.booking.patient_booking.service.LabStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pathology-lab")
public class LabStatsController {

    @Autowired
    private LabStatsService statsService;

    @GetMapping("/get-stats")
    public ResponseEntity<?> getBookingStats() {
        return ResponseEntity.ok(statsService.getAllLabStats());
    }
}
