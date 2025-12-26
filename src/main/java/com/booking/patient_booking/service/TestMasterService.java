package com.booking.patient_booking.service;

import com.booking.patient_booking.entity.Doctor;
import com.booking.patient_booking.entity.TestMaster;
import com.booking.patient_booking.repository.TestMasterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TestMasterService {
    Logger log = LoggerFactory.getLogger(TestMasterService.class);

    @Autowired
    TestMasterRepository testMasterRepository;

    public List<TestMaster> findAllTests() {
        List<TestMaster> doctors = testMasterRepository.findAll();
        log.info("List of all tests : " + doctors);
        return doctors;
    }

    public void saveNewTest(TestMaster test) {
        log.info("test details received from front end : " + test);
        test.setTestCode(generateTestCode(test));
        log.info("test details before being saved to db : " + test);
        testMasterRepository.save(test);
    }

    public TestMaster updateExistingTest(TestMaster test) {

        Optional<TestMaster> testFromDb = testMasterRepository.findById(test.getTestCode());
        testFromDb.ifPresent(testMaster -> testMasterRepository.delete(testMaster));
        testMasterRepository.save(test);
        return test;
    }


    public String generateTestCode(TestMaster test) {

        String categoryCode = getCategoryCode(test.getCategory());
        long count = testMasterRepository.count() + 1;
        String formattedCount = String.format("%03d", count); // e.g., 001, 002...
        return ("T" + formattedCount + categoryCode);
    }

    private String getCategoryCode(String category) {
        if (category == null) return "GEN"; // default
        category = category.toUpperCase();

        if (category.contains("HEMATO")) return "HEMAT";
        if (category.contains("BIOCHEM")) return "BIOCH";
        if (category.contains("IMMUNO")) return "IMMUN";
        if (category.contains("SERO")) return "SERO";
        if (category.contains("CLINICAL")) return "CLIN";
        if (category.contains("COAG")) return "COAG";
        if (category.contains("HISTO")) return "HISTO";

        return category.substring(0, Math.min(category.length(), 5)); // fallback
    }
}
