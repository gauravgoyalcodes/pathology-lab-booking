package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsernameAndActiveTrue(String username);
}
