package com.example.ParlourApp.razorPay;


import com.example.ParlourApp.userbilling.UserBillingRegModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetails
{
    private String orderId;
    private String paymentId;
    private String currency;
    private Integer amount;
    private String key;
    private Long userId;

    @Autowired
    OrderDetailsService orderDetailsService;

    public <T> TransactionDetails(T id, String paymentId, T currency, T amount, String key, Long userId) {
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<UserBillingRegModel> findRecentTransactions() {
   return  null;
    }
}
