package com.booking.patient_booking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patient_booking_test_codes")
public class BookingTestCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id", nullable = false, length = 50)
    private String bookingId;

    @Column(name = "test_code", nullable = false, length = 30)
    private String testCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    @Override
    public String toString() {
        return "BookingTestCodes{" +
                "id=" + id +
                ", bookingId='" + bookingId + '\'' +
                ", testCode='" + testCode + '\'' +
                '}';
    }
}

