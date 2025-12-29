package com.booking.patient_booking.service;

import com.booking.patient_booking.dto.AddPhleboRequest;
import com.booking.patient_booking.entity.Booking;
import com.booking.patient_booking.entity.Phlebo;
import com.booking.patient_booking.entity.PhleboAssignment;
import com.booking.patient_booking.enums.PhleboStatus;
import com.booking.patient_booking.repository.PhleboAssignmentRepository;
import com.booking.patient_booking.repository.PhleboRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PhleboService {
    Logger log = LoggerFactory.getLogger(PhleboService.class);

    @Autowired
    PhleboRepository phleboRepository;

    @Autowired
    PhleboAssignmentRepository phleboAssignmentRepository;

    @Transactional
    public Phlebo addPhlebo(AddPhleboRequest request) {

        Phlebo phlebo = new Phlebo();
        phlebo.setName(request.getName());
        phlebo.setPhone(request.getPhone());
        phlebo.setBranchId(request.getBranchId());
        phlebo.setStatus(PhleboStatus.ACTIVE);

        return phleboRepository.save(phlebo);
    }

    @Transactional
    public void assignPhlebo(Booking booking) {

        List<Phlebo> availablePhlebos =
                phleboRepository.findAvailablePhlebos(
                        1L,   //branch id set to 1 as we don't have any specific benaches yet
                        booking.getAppointmentDate(),
                        booking.getTimeSlot()
                );

        if (availablePhlebos.isEmpty()) {
            log.warn("No phlebo available for booking {}", booking.getBookingId());
            return;
        }

        Collections.shuffle(availablePhlebos);
        Phlebo selected = availablePhlebos.get(0);

        PhleboAssignment assignment = new PhleboAssignment();
        assignment.setBookingId(booking.getBookingId());
        assignment.setPhlebo(selected);
        assignment.setAppointmentDate(booking.getAppointmentDate());
        assignment.setTimeSlot(booking.getTimeSlot());

        phleboAssignmentRepository.save(assignment);
    }

    public Phlebo findAssignedPhleboBasedOnBookingId(String bookingId) {

        if (phleboAssignmentRepository.findByBookingId(bookingId).isPresent()) {
            return phleboAssignmentRepository.findByBookingId(bookingId).get().getPhlebo();
        }
        return null;
    }
}
