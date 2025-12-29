package com.booking.patient_booking.util;

import com.booking.patient_booking.entity.Booking;
import com.booking.patient_booking.entity.Phlebo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhatsappService {

    private static final Logger log = LoggerFactory.getLogger(WhatsappService.class);

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.api.token}")
    private String token;

    private final RestTemplate restTemplate = new RestTemplate();

    /* =========================
       PUBLIC NOTIFICATION APIs
       ========================= */

    // Booking created (Pending)
    public void notifyBookingCreated(Booking booking, List<String> testNames) {
        String message = String.format(
                "Dear %s,\n\n" +
                        "✅ Your lab test booking has been received.\n\n" +
                        "🆔 Booking ID: %s\n" +
                        "🧬 Tests booked: %s\n" +
                        "⏱ TAT: %s\n" +
                        "💰 Amount: ₹%s\n\n" +
                        "📅 Slot: %s | %s\n\n" +
                        "Status: Pending lab approval.\n\n" +
                        "Thank you,\n" +
                        "Dr. Pankaj Surgical Pathology Lab",
                booking.getPatientName(),
                booking.getBookingId(),
                String.join(", ", testNames),
                booking.getMaxTat(),
                booking.getTotalPrice(),
                booking.getAppointmentDate(),
                booking.getTimeSlot()
        );

        send(normalizePhone(booking.getPhone()), message);
    }

    // Booking accepted → Patient
    public void notifyPatientAccepted(Booking booking, Phlebo phlebo) {
        String message = """
                Dear %s,
                
                ✅ Your lab test booking has been confirmed.
                
                🆔 Booking ID: %s
                📅 Date: %s
                ⏱ Slot: %s
                
                👨‍⚕️ Phlebotomist: %s
                📞 %s
                
                Please be available at the given time.
                
                Thank you,
                "Dr. Pankaj Surgical Pathology Lab
                """.formatted(
                booking.getPatientName(),
                booking.getBookingId(),
                booking.getAppointmentDate(),
                booking.getTimeSlot(),
                phlebo.getName(),
                phlebo.getPhone()
        ).trim();

        send(normalizePhone(booking.getPhone()), message);
    }

    // Booking rejected → Patient
    public void notifyPatientRejected(Booking booking) {
        String message = """
                Dear %s,
                
                Sorry for the inconvenience.
                Your lab test booking has been rejected.
                
                🆔 Booking ID: %s
                📅 Date: %s
                ⏱ Slot: %s
                
                Thank you,
                Doctor Pankaj Pathology Lab
                """.formatted(
                booking.getPatientName(),
                booking.getBookingId(),
                booking.getAppointmentDate(),
                booking.getTimeSlot()
        ).trim();

        send(normalizePhone(booking.getPhone()), message);
    }

    // Booking accepted → Phlebo
    public void notifyPhleboAssigned(Booking booking, Phlebo phlebo) {
        String message = """
                Hello %s,
                
                🧪 You have been assigned a new sample collection.
                
                🆔 Booking ID: %s
                👤 Patient: %s
                📅 Date: %s
                ⏱ Slot: %s
                📞 Patient Contact: %s
                
                Please contact the patient and proceed with collection.
                
                "Dr. Pankaj Surgical Pathology Lab"
                """.formatted(
                phlebo.getName(),
                booking.getBookingId(),
                booking.getPatientName(),
                booking.getAppointmentDate(),
                booking.getTimeSlot(),
                booking.getPhone()
        ).trim();

        send(normalizePhone(phlebo.getPhone()), message);
    }

    /* =========================
       INTERNAL HELPERS
       ========================= */

    private void send(String phone, String message) {
        try {
            if(phone != null){
                URI targetUrl = UriComponentsBuilder.fromUriString(apiUrl)
                        .queryParam("token", token)
                        .queryParam("phone", phone)
                        .queryParam("message", message)
                        .build()
                        .encode()
                        .toUri();

                restTemplate.getForEntity(targetUrl, String.class);
                log.info("WhatsApp sent successfully to {}", phone);
            }
        } catch (RestClientException ex) {
            log.error("Failed to send WhatsApp to {}", phone, ex);
        }
    }

    private String normalizePhone(String phone) {
        String raw = phone.replaceAll("\\D", "");
        if (raw.length() == 10) {
            return "91" + raw;
        } else {
            log.error("invalid phone number, message can't be send");
            return null;
        }
    }
}
