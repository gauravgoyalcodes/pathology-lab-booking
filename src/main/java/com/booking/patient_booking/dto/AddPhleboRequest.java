package com.booking.patient_booking.dto;

public class AddPhleboRequest {
    private String name;
    private String phone;
    private String branchName;

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


    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public String toString() {
        return "AddPhleboRequest{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", branchName=" + branchName +
                '}';
    }
}
