package com.booking.patient_booking.service;

import com.booking.patient_booking.entity.Booking;
import com.booking.patient_booking.entity.BookingTestCode;
import com.booking.patient_booking.enums.StatusFlag;
import com.booking.patient_booking.repository.BookingRepository;

import com.booking.patient_booking.repository.BookingTestCodeRepository;
import com.booking.patient_booking.repository.TestMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    TestMasterRepository testMasterRepository;

    private final RestTemplate restTemplate = new RestTemplate();

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

        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());

        bookingRepository.save(booking);

        // 3️⃣ Save test codes
        for (String testCode : request.getTests()) {
            BookingTestCode btc = new BookingTestCode();
            btc.setBookingId(bookingId);
            btc.setTestCode(testCode);
            bookingTestCodeRepository.save(btc);
        }

        return booking;
    }


    public List<Booking> findAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        log.info("fetching list of all bookings : " + bookings);
        return bookings;
    }

    public Booking updateStatusFlag(String bookingId, StatusFlag status) {
        log.info("Find the booking using booking Id : {}", bookingId);

        return bookingRepository.findById(bookingId)
                .map(booking -> {
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

    public void triggerWhatsappMessageForBooking(Booking booking) {
        List<String> testsChosenByPatient = new ArrayList<String>();
        String testName = "";
        for (String testCodes : bookingTestCodeRepository.findTestCodesByBookingId(booking.getBookingId())) {
            testName = testMasterRepository.findById(testCodes).get().getTestName();
            testsChosenByPatient.add(testName);
        }

        try {
            // 1. Clean and Format Phone Number
            String rawPhone = booking.getPhone().replaceAll("\\D", "");
            String phone = (rawPhone.length() == 10) ? "91" + rawPhone : rawPhone;

            // 2. Build the Message String
            String message = String.format(
                    "Dear %s,\n\n" +
                            "✅ Your lab test booking has been received.\n\n" +
                            "🆔 Booking ID: %s\n" +
                            "🧬 Tests booked: %s\n" +
                            "⏱ TAT: %s\n" +
                            "💰 Amount: ₹%s\n\n" +
                            "📅 Slot: %s | %s\n\n" +
                            "Status: Pending lab approval.\n\n" +
                            "You may pay via UPI now or pay later during sample collection.\n\n" +
                            "Thank you,\n" +
                            "Dr. Pankaj Surgical Pathology Lab",
                    booking.getPatientName(),
                    booking.getBookingId(),
                    testsChosenByPatient,
                    booking.getMaxTat(),
                    booking.getTotalPrice(),
                    booking.getAppointmentDate(),
                    booking.getTimeSlot()
            );

            // 3. Configuration (Update these values)
            String baseUrl = "http://mediaapi.stewindia.com/api/sendText";
            String token = "cmcn2uaoh2dy373ijv5a1wddz";

            // 4. Build URI with Query Parameters (Handles encoding automatically)
            URI targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                    .queryParam("token", token)
                    .queryParam("phone", phone)
                    .queryParam("message", message)
                    .build()
                    .encode()
                    .toUri();

            // 5. Execute GET request (Matching UrlFetchApp.fetch behavior)
            ResponseEntity<String> response = restTemplate.getForEntity(targetUrl, String.class);

            System.out.println("WhatsApp API Response: " + response.getBody());

        } catch (Exception e) {
            System.err.println("Failed to send WhatsApp: " + e.getMessage());
        }
    }
}

