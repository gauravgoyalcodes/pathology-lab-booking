package com.booking.patient_booking.dto;

public class LabStatsDto {

    private Long numberOfPendingReports;
    private Long numberOfTotalBookings;
    private Long numberOfTotalPhlebos;

    public Long getNumberOfPendingReports() {
        return numberOfPendingReports;
    }

    public void setNumberOfPendingReports(Long numberOfPendingReports) {
        this.numberOfPendingReports = numberOfPendingReports;
    }

    public Long getNumberOfTotalBookings() {
        return numberOfTotalBookings;
    }

    public void setNumberOfTotalBookings(Long numberOfTotalBookings) {
        this.numberOfTotalBookings = numberOfTotalBookings;
    }

    public Long getNumberOfTotalPhlebos() {
        return numberOfTotalPhlebos;
    }

    public void setNumberOfTotalPhlebos(Long numberOfTotalPhlebos) {
        this.numberOfTotalPhlebos = numberOfTotalPhlebos;
    }

    @Override
    public String toString() {
        return "LabStatsDto{" +
                "numberOfPendingReports=" + numberOfPendingReports +
                ", numberOfTotalBookings=" + numberOfTotalBookings +
                ", numberOfTotalPhlebos=" + numberOfTotalPhlebos +
                '}';
    }
}
