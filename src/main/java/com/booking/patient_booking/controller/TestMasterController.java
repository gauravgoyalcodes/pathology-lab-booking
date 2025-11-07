package com.booking.patient_booking.controller;

import com.booking.patient_booking.entity.Doctor;
import com.booking.patient_booking.entity.TestMaster;
import com.booking.patient_booking.service.TestMasterService;
import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pathology-lab/tests")
public class TestMasterController {
    Logger log = LoggerFactory.getLogger(TestMasterController.class);

    @Autowired
    TestMasterService testMasterService;

    @GetMapping("/find-all")
    public ResponseEntity<List<TestMaster>> getAllDoctors() {

        List<TestMaster> testsList = testMasterService.findAllTests();
        if (testsList == null || testsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(testsList, HttpStatus.OK); // 200
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerNewTest(@RequestBody TestMaster test) {
        try {
            testMasterService.saveNewTest(test);
            return ResponseEntity.ok("Test is successfully Added");
        } catch (RuntimeException ex) {
            log.error("Test registration failed: {}", ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Registration failed: " + ex.getMessage());
        }
    }


//    @PutMapping("/update")
//    public ResponseEntity<TestMaster> updateExistingTest(@RequestBody TestMaster test) {
//        try {
//            TestMaster testUpdated = testMasterService.updateExistingTest(test);
//            return ResponseEntity.ok(testUpdated);
//        } catch (RuntimeException ex) {
//            log.error("Test update failed: {}", ex.getMessage(), ex);
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(test);
//        }
//    }

}
