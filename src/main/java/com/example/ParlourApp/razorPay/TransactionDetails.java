package com.example.ParlourApp.razorPay;


import com.example.ParlourApp.userbilling.UserBillingRegModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class TransactionDetails
{

    @Autowired
    OrderDetailsService orderDetailsService;
    private String orderId;
    private String paymentId;
    private String currency;
    private Integer amount;
    private String key;
    private Long userId;

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

}
