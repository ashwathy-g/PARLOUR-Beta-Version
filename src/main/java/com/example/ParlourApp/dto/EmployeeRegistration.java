package com.example.ParlourApp.dto;

import lombok.Data;

@Data
public class EmployeeRegistration
{
    private String employeeName;
    private Long parlourId;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Long getParlourId() {
        return parlourId;
    }

    public void setParlourId(Long parlourId) {
        this.parlourId = parlourId;
    }

}
