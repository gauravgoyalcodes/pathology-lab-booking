package com.booking.patient_booking.controller;

import com.booking.patient_booking.dto.AddPhleboRequest;
import com.booking.patient_booking.entity.Phlebo;
import com.booking.patient_booking.service.PhleboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pathology-lab/phlebos")
public class PhleboController {
    Logger log = LoggerFactory.getLogger(PhleboController.class);

   @Autowired
    PhleboService phleboService;

    @PostMapping("/add")
    public ResponseEntity<List<Phlebo>> addPhlebo(@RequestBody List<AddPhleboRequest> request) {
        List<Phlebo> savedPhlebo = phleboService.addPhlebo(request);
        return ResponseEntity.ok(savedPhlebo);
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAllPhlebos() {
        List<Phlebo> savedPhlebo = phleboService.findAllPhlebos();
        if(savedPhlebo.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(savedPhlebo);
    }
}