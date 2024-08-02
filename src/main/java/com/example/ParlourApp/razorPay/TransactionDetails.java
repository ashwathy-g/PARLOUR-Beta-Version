package com.example.ParlourApp.razorPay;


import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class TransactionDetails
{
    private String orderId;
    private String paymentId;
    private String currency;
    private Integer amount;
    private String key;

    private Long userId;


    public TransactionDetails(String orderId, String paymentId,String currency, Integer amount, String key,Long userId) {
        this.orderId = orderId;
        this.paymentId=paymentId;
        this.currency = currency;
        this.amount = amount;
        this.key = key;
        this.userId=userId;

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
