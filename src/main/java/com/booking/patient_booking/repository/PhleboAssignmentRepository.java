package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.PhleboAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhleboAssignmentRepository extends JpaRepository<PhleboAssignment, Long> {
    Optional<PhleboAssignment> findByBookingId(String bookingId);
}
