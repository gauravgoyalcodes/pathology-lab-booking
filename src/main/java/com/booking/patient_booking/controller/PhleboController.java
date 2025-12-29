package com.booking.patient_booking.controller;

import com.booking.patient_booking.dto.AddPhleboRequest;
import com.booking.patient_booking.entity.Phlebo;
import com.booking.patient_booking.service.PhleboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pathology-lab/phlebos")
public class PhleboController {
    Logger log = LoggerFactory.getLogger(PhleboController.class);

   @Autowired
    PhleboService phleboService;

    @PostMapping("/add")
    public ResponseEntity<?> addPhlebo(@RequestBody AddPhleboRequest request) {
        Phlebo savedPhlebo = phleboService.addPhlebo(request);
        return ResponseEntity.ok(savedPhlebo);
    }
}