package com.booking.patient_booking.dto;

import com.booking.patient_booking.enums.StatusFlag;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AllBookingsResponseDto {


    private String bookingId;
    private String patientId;
    private String salutation;
    private String patientName;
    private Integer age;
    private String gender;
    private String phone;
    private String email;
    private LocalDate appointmentDate;
    private String timeSlot;
    private String doctorId;
    private List<String> tests;
    private BigDecimal totalPrice;
    private String maxTat;
    private String notes;
    private StatusFlag status;
    private String phleboName;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMaxTat() {
        return maxTat;
    }

    public void setMaxTat(String maxTat) {
        this.maxTat = maxTat;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public StatusFlag getStatus() {
        return status;
    }

    public void setStatus(StatusFlag status) {
        this.status = status;
    }

    public String getPhleboName() {
        return phleboName;
    }

    public void setPhleboName(String phleboName) {
        this.phleboName = phleboName;
    }

    @Override
    public String toString() {
        return "AllBookingsResponseDto{" +
                "bookingId='" + bookingId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", salutation='" + salutation + '\'' +
                ", patientName='" + patientName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", timeSlot='" + timeSlot + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", tests=" + tests +
                ", totalPrice=" + totalPrice +
                ", maxTat='" + maxTat + '\'' +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                ", phleboName='" + phleboName + '\'' +
                '}';
    }
}
