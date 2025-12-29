package com.booking.patient_booking.repository;

import com.booking.patient_booking.entity.Phlebo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PhleboRepository extends JpaRepository<Phlebo, Long> {

    @Query("""
                SELECT p
                FROM Phlebo p
                WHERE p.status = 'ACTIVE'
                AND p.branchId = :branchId
                AND (
                    SELECT COUNT(pa)
                    FROM PhleboAssignment pa
                    WHERE pa.phlebo = p
                      AND pa.appointmentDate = :date
                      AND pa.timeSlot = :slot
                ) < 2
            """)
    List<Phlebo> findAvailablePhlebos(
            @Param("branchId") Long branchId,
            @Param("date") LocalDate date,
            @Param("slot") String slot
    );

}
