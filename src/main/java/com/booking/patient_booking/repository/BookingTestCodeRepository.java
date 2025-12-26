package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.BookingTestCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingTestCodeRepository
        extends JpaRepository<BookingTestCode, Long> {

    @Query(value = "SELECT test_code FROM patient_booking_test_codes where booking_id = :bookingId", nativeQuery = true)
    List<String> findTestCodesByBookingId(@Param("bookingId") String bookingId);
}