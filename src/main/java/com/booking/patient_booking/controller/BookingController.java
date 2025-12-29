package com.booking.patient_booking.controller;

import com.booking.patient_booking.dto.AllBookingsResponseDto;
import com.booking.patient_booking.entity.Booking;
import com.booking.patient_booking.enums.StatusFlag;
import com.booking.patient_booking.service.BookingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pathology-lab")
public class BookingController {
    Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    BookingService bookingService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Booking booking) {
        bookingService.registerBooking(booking);
        return ResponseEntity.ok(Map.of("status", "SUCCESS"));
    }


    @GetMapping("/find-all/bookings")
    public ResponseEntity<List<AllBookingsResponseDto>> getAllBookings() {

        List<AllBookingsResponseDto> bookings = bookingService.findAllBookings();
        if (bookings == null || bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK); // 200
    }

    @PatchMapping("/update-status/{bookingId}/{status}")
    public ResponseEntity<?> updateStatusFlag(@PathVariable String bookingId, @PathVariable StatusFlag status) {

        try {
            Booking booking = bookingService.updateStatusFlag(bookingId, status);
            return ResponseEntity.ok(booking); // 200 OK + updated booking
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Booking not found with ID: " + bookingId);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating booking.");
        }
    }
}
