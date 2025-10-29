package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    @Query(value = "SELECT doctor_id FROM doctors ORDER BY doctor_id DESC LIMIT 1", nativeQuery = true)
    String findLastDoctorId();
}
