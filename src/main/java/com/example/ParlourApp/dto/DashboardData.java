package com.example.ParlourApp.dto;

import com.example.ParlourApp.userbilling.UserBillingRegModel;
import lombok.Data;
import org.hibernate.Transaction;

import java.util.List;

@Data
public class DashboardData {

    private Long totalBookings;

    private Double totalRevenue;

    private Long activeParlours;

    private Long activeEmployees;

    private List<UserBillingRegModel> recentTransactions;

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public Long getActiveParlours() {
        return activeParlours;
    }

    public void setActiveParlours(Long activeParlours) {
        this.activeParlours = activeParlours;
    }

    public Long getActiveEmployees() {
        return activeEmployees;
    }

    public void setActiveEmployees(Long activeEmployees) {
        this.activeEmployees = activeEmployees;
    }

    public List<UserBillingRegModel> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<UserBillingRegModel> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }
}
