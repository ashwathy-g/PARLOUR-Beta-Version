package com.example.ParlourApp.dto;

public class RatingRequestDTO {
    private Long parlourId;
    private String customerName;
    private Integer ratingValue;
    private String review;
    private String ItemName;

   public Long getParlourId() {

       return parlourId;
    }

   public void setParlourId(Long parlourId) {
        this.parlourId = parlourId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
