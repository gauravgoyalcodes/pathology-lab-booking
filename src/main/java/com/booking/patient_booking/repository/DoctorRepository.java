package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    @Query(value = "SELECT doctor_id FROM doctors ORDER BY doctor_id DESC LIMIT 1", nativeQuery = true)
    String findLastDoctorId();

    @Query(
            value = "SELECT * FROM doctors WHERE LOWER(doctor_name) LIKE LOWER(CONCAT('%', :doctorName, '%')) ORDER BY doctor_id DESC",
            nativeQuery = true
    )
    List<Doctor> findDoctorByNameLike(@Param("doctorName") String doctorName);

    @Query(
            value = "SELECT * FROM doctors WHERE LOWER(specialization) LIKE LOWER(CONCAT('%', :specialization, '%')) ORDER BY doctor_id DESC",
            nativeQuery = true
    )
    List<Doctor> findDoctorBySpecializationLike(@Param("specialization") String specialization);

}
