package com.booking.patient_booking.service;

import com.booking.patient_booking.dto.LabStatsDto;
import com.booking.patient_booking.enums.StatusFlag;
import com.booking.patient_booking.repository.BookingRepository;
import com.booking.patient_booking.repository.PhleboRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabStatsService {
    Logger log = LoggerFactory.getLogger(LabStatsService.class);

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PhleboRepository phleboRepository;

    public LabStatsDto getAllLabStats(){
    LabStatsDto stats = new LabStatsDto();
    stats.setNumberOfTotalBookings(bookingRepository.count());
    stats.setNumberOfPendingReports(bookingRepository.countByStatus(StatusFlag.PENDING));
    stats.setNumberOfTotalPhlebos(phleboRepository.count());

    return stats;
    }
}
