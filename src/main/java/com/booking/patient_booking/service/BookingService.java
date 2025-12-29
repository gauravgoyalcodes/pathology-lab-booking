package com.booking.patient_booking.service;

import com.booking.patient_booking.dto.AllBookingsResponseDto;
import com.booking.patient_booking.entity.Booking;
import com.booking.patient_booking.entity.BookingTestCode;
import com.booking.patient_booking.enums.StatusFlag;
import com.booking.patient_booking.repository.BookingRepository;

import com.booking.patient_booking.repository.BookingTestCodeRepository;
import com.booking.patient_booking.repository.TestMasterRepository;
import com.booking.patient_booking.util.WhatsappService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private static final String PATIENTPREFIX = "PT";
    private static final String BOOKINGPREFIX = "B";

    Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    BookingTestCodeRepository bookingTestCodeRepository;

    @Autowired
    PhleboService phleboService;

    @Autowired
    TestMasterService testMasterService;

    @Autowired
    WhatsappService whatsappService;

    @Transactional
    public Booking registerBooking(Booking request) {

        String bookingId = generateBookingId();
        String patientId = generatePatientId();


        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setPatientId(patientId);

        booking.setSalutation(request.getSalutation());
        booking.setPatientName(request.getPatientName());
        booking.setAge(request.getAge());
        booking.setGender(request.getGender());
        booking.setPhone(request.getPhone());
        booking.setEmail(request.getEmail());

        booking.setAppointmentDate(request.getAppointmentDate());
        booking.setTimeSlot(request.getTimeSlot());
        booking.setDoctorId(request.getDoctorId());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setMaxTat(request.getMaxTat());
        booking.setNotes(request.getNotes());

        booking.setStatus(StatusFlag.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        log.info("booking details before being saved to db : {}", booking);
        bookingRepository.save(booking);

        // 3️⃣ Save test codes
        for (String testCode : request.getTests()) {
            BookingTestCode btc = new BookingTestCode();
            btc.setBookingId(bookingId);
            btc.setTestCode(testCode);
            bookingTestCodeRepository.save(btc);
        }

        whatsappService.notifyBookingCreated(booking, testMasterService.findTestsOfABooking(booking.getBookingId()));
        return booking;
    }


    public List<AllBookingsResponseDto> findAllBookings() {
        List<AllBookingsResponseDto> bookingResponse = new ArrayList<AllBookingsResponseDto>();
        List<Booking> bookings = bookingRepository.findAll();

        log.info("finding all bookings : {}", bookings);

        for (Booking b : bookings) {
            AllBookingsResponseDto dto = new AllBookingsResponseDto();
            b.setTests(testMasterService.findTestsOfABooking(b.getBookingId()));
            BeanUtils.copyProperties(b, dto);
            if (phleboService.findAssignedPhleboBasedOnBookingId(b.getBookingId()) != null) {
                dto.setPhleboName(phleboService.findAssignedPhleboBasedOnBookingId(b.getBookingId()).getName());
            }
            bookingResponse.add(dto);
        }
        log.info("fetching list of all bookings : " + bookingResponse);
        return bookingResponse;
    }

    @Transactional
    public Booking updateStatusFlag(String bookingId, StatusFlag status) {

        log.info("Updating status for bookingId: {} to {}", bookingId, status);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Booking not found with id: " + bookingId)
                );

        if (booking.getStatus() != StatusFlag.PENDING) {
            throw new IllegalStateException("Booking status already updated");
        }

        if (status == StatusFlag.REJECTED) {
            booking.setStatus(StatusFlag.REJECTED);
            whatsappService.notifyPatientRejected(booking);
            return bookingRepository.save(booking);
        }

        if (status == StatusFlag.ACCEPTED) {
            booking.setStatus(StatusFlag.ACCEPTED);
            bookingRepository.save(booking);
            phleboService.assignPhlebo(booking);
            whatsappService.notifyPatientAccepted(booking, phleboService.findAssignedPhleboBasedOnBookingId(bookingId));
            whatsappService.notifyPhleboAssigned(booking, phleboService.findAssignedPhleboBasedOnBookingId(bookingId));
            return booking;
        }

        throw new IllegalArgumentException("Invalid status");
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

