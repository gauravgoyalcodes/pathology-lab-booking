package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.Centers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CenterRepository extends JpaRepository<Centers, String> {

    @Query(value = "SELECT center_id FROM lab_centers ORDER BY center_id DESC LIMIT 1", nativeQuery = true)
    String findLastCenterId();

    @Query(
            value = "SELECT * FROM lab_centers WHERE active = true ORDER BY center_id DESC LIMIT 1",
            nativeQuery = true
    )
    List<Centers> findByActiveTrue();
}
