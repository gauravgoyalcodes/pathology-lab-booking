package com.booking.patient_booking.entity;

import com.booking.patient_booking.enums.*;
import com.booking.patient_booking.util.StringListConverter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "patient_test_bookings")
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "patient_id", nullable = false, length = 50)
    private String patientId;

    @Column(name = "patient_name", nullable = false, length = 100)
    private String patientName;

    @Column(name = "contact", nullable = false, length = 10)
    private String contact;

    @Convert(converter = StringListConverter.class)
    @Column(name = "test_list", nullable = false, columnDefinition = "TEXT")
    private List<String> testList; // Could store comma-separated values or JSON

    @Column(name = "refd_by", nullable = false, length = 100)
    private String refdBy;

    @Column(name = "refd_by_hospital", length = 200)
    private String refdByHospital;

    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false, length = 10)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "collection_type", nullable = false, length = 10)
    private CollectionType collectionType;

    @Column(name = "chosen_center", nullable = false, length = 100)
    private String chosenCenter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_flag", nullable = false, length = 10)
    private StatusFlag statusFlag = StatusFlag.Pending;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 10)
    private PaymentStatus paymentStatus = PaymentStatus.Pending;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Automatically set timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getTestList() {
        return testList;
    }

    public void setTestList(List<String> testList) {
        this.testList = testList;
    }

    public String getRefdBy() {
        return refdBy;
    }

    public void setRefdBy(String refdBy) {
        this.refdBy = refdBy;
    }

    public String getRefdByHospital() {
        return refdByHospital;
    }

    public void setRefdByHospital(String refdByHospital) {
        this.refdByHospital = refdByHospital;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public CollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    public String getChosenCenter() {
        return chosenCenter;
    }

    public void setChosenCenter(String chosenCenter) {
        this.chosenCenter = chosenCenter;
    }

    public StatusFlag getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(StatusFlag statusFlag) {
        this.statusFlag = statusFlag;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", contact='" + contact + '\'' +
                ", testList=" + testList +
                ", refdBy='" + refdBy + '\'' +
                ", refdByHospital='" + refdByHospital + '\'' +
                ", totalCost=" + totalCost +
                ", paymentMode=" + paymentMode +
                ", collectionType=" + collectionType +
                ", chosenCenter='" + chosenCenter + '\'' +
                ", statusFlag=" + statusFlag +
                ", paymentStatus=" + paymentStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
