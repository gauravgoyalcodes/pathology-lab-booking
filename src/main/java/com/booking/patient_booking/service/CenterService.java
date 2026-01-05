package com.booking.patient_booking.service;

import com.booking.patient_booking.entity.Centers;
import com.booking.patient_booking.repository.CenterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CenterService {
    private static final String PREFIX = "CENTER";
    Logger log = LoggerFactory.getLogger(CenterService.class);

    @Autowired
    CenterRepository centerRepository;

    @Transactional
    public void addNewCenter(Centers center) {
        center.setCenterId(generateCenterId());
        centerRepository.save(center);
    }

    public List<Centers> findAllCenters() {
        List<Centers> centers = centerRepository.findAll();
        return centerRepository.findByActiveTrue();

    }

    public String generateCenterId() {

        String lastId = centerRepository.findLastCenterId(); // e.g., "CENTER001"
        int nextNumber = 1;

        if (lastId != null && lastId.startsWith(PREFIX)) {
            String numberPart = lastId.substring(PREFIX.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return PREFIX + String.format("%02d", nextNumber);  // e.g., "CENTER001"
    }
}
