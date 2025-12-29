package com.booking.patient_booking.dto;

public class AddPhleboRequest {
    private String name;
    private String phone;
    private Long branchId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "AddPhleboRequest{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
