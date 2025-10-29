package com.booking.patient_booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name= "test_master")
public class TestMaster {

    @Id
    @Column(name = "test_code")
    private String testCode;

    @Column(name = "test_name", nullable = false, length = 100)
    private String testName;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "sample_type", nullable = false, length = 50)
    private String sampleType;

    @Column(name = "normal_tat", nullable = false, length = 50)
    private String normalTat;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getNormalTat() {
        return normalTat;
    }

    public void setNormalTat(String normalTat) {
        this.normalTat = normalTat;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TestMaster{" +
                "testCode='" + testCode + '\'' +
                ", testName='" + testName + '\'' +
                ", category='" + category + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", normalTat='" + normalTat + '\'' +
                ", price=" + price +
                '}';
    }
}