package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.PathologyCenters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PathologyCenterRepository extends JpaRepository<PathologyCenters, String> {

    @Query(value = "SELECT center_id FROM pathology_centers ORDER BY center_id DESC LIMIT 1", nativeQuery = true)
    String findLastCenterId();
}
