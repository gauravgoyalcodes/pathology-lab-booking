package com.booking.patient_booking.service;

import com.booking.patient_booking.entity.Booking;
import com.booking.patient_booking.enums.StatusFlag;
import com.booking.patient_booking.repository.BookingRepository;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private static final String PATIENTPREFIX = "PT";
    private static final String BOOKINGPREFIX = "B";

    Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    BookingRepository bookingRepository;

    public Booking registerBooking(Booking booking){
        log.info("booking details received from front end : " + booking);
        booking.setBookingId(generateBookingId());
        log.info("booking_id : " + booking.getBookingId());
        booking.setPatientId(generatePatientId());

        Booking bookingSaved = bookingRepository.save(booking);
        log.info("booking details before being saved to db : " + bookingSaved);
        return bookingSaved;
    }

    public List<Booking> findAllBookings(){
        List<Booking> bookings = bookingRepository.findAll();
        log.info("fetching list of all bookings : " + bookings);
        return bookings;
    }

    public Booking updateStatusFlag(String bookingId, StatusFlag status) {
        log.info("Find the booking using booking Id : {}", bookingId);

        return bookingRepository.findById(bookingId)
                .map(booking -> {
                    booking.setStatusFlag(status);
                    return bookingRepository.save(booking);
                })
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));
    }


    public String generateBookingId() {

        String lastId = bookingRepository.findLastBookingId(); // e.g., "B0000000001"
        int nextNumber = 1;

        if (lastId != null && lastId.startsWith(BOOKINGPREFIX)) {
            String numberPart = lastId.substring(BOOKINGPREFIX.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return BOOKINGPREFIX + String.format("%09d", nextNumber);  // e.g., "B0000000001"
    }

    public String generatePatientId() {

        String lastId = bookingRepository.findLastPatientId(); // e.g., "PT00000001"
        int nextNumber = 1;

        if (lastId != null && lastId.startsWith(PATIENTPREFIX)) {
            String numberPart = lastId.substring(PATIENTPREFIX.length());
            nextNumber = Integer.parseInt(numberPart) + 1;
        }

        return PATIENTPREFIX + String.format("%08d", nextNumber);  // e.g., "PT00000001"
    }

}
