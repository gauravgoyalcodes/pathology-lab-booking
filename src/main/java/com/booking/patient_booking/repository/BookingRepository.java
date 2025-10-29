package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, String> {

    @Query(value = "SELECT patient_id FROM patient_test_bookings ORDER BY patient_id DESC LIMIT 1", nativeQuery = true)
    String findLastPatientId();

    @Query(value = "SELECT booking_id FROM patient_test_bookings ORDER BY booking_id DESC LIMIT 1", nativeQuery = true)
    String findLastBookingId();
}
