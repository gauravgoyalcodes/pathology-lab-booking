package com.booking.patient_booking.service;

import com.booking.patient_booking.entity.Doctor;
import com.booking.patient_booking.entity.PathologyCenters;
import com.booking.patient_booking.repository.PathologyCenterRepository;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathologyCenterService {
    private static final String PREFIX = "C";
    Logger log = LoggerFactory.getLogger(PathologyCenters.class);

    @Autowired
    PathologyCenterRepository pathologyCenterRepository;

    public PathologyCenters saveCenter(PathologyCenters center) {
        center.setCenterId(generateCenterId());
        log.info("Center to save in the db : " + center);
        pathologyCenterRepository.save(center);
        return center;
    }

    public List<PathologyCenters> findAllCenters(){
        List<PathologyCenters> centers = pathologyCenterRepository.findAll();
        log.info("List of all centers : " + centers);
        return centers;
    }

    public void deleteCenter(String centerId) {
         PathologyCenters center = pathologyCenterRepository.findById(centerId)
                .orElseThrow(() -> new EntityNotFoundException("Center not found with ID: " + centerId));

        pathologyCenterRepository.delete(center);
        log.info("Center deleted successfully with ID: {}", centerId);
    }

    public String generateCenterId() {

        String lastId = pathologyCenterRepository.findLastCenterId(); // e.g., "DR0001"
        int nextNumber = 1;

        if (lastId != null && lastId.startsWith(PREFIX)) {
            String numberPart = lastId.substring(PREFIX.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return PREFIX + String.format("%02d", nextNumber);  // e.g., "C01"
    }
}
