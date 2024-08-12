package com.example.ParlourApp.dto;

public class EmployeeDto {
    private Long id;
    private String employeeName;
    private byte[] image;
    private Boolean isAvailable;

    public EmployeeDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public byte[] getImage() {
        return image;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }
}
