package com.example.ParlourApp.razorPay;

import com.example.ParlourApp.userbilling.UserBillingRegModel;
import com.example.ParlourApp.userbilling.UserBillingRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDetailsService
{
    private static final String ORDER_PLACED = "Placed";

    private static final String KEY = "rzp_test_Lh738g2oARGFbD";
    private static final String Key_Secret = "iOSGwx2YAmHsl2dNuzfi1bSa";
    private static final String Currency = "INR";
    @Autowired
    UserBillingRepository userBillingRepository;
    public TransactionDetails createTransaction(Integer amount,Long userId){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount",amount * 100);
            jsonObject.put("currency",Currency);

            RazorpayClient razorpayClient = new RazorpayClient(KEY,Key_Secret);
            Order order = razorpayClient.orders.create(jsonObject);
            String paymentId="";
            TransactionDetails transactionDetails = new TransactionDetails(order.get("id"), paymentId, order.get("currency"), order.get("amount"), KEY, userId);



//            TransactionDetails transactionDetails = prepareTransactionDetails(order,userId);

            Optional<UserBillingRegModel> optionalUser = userBillingRepository.findById(userId);
            if (optionalUser.isPresent()){
                UserBillingRegModel userBillingRegModel = optionalUser.get();
                userBillingRegModel.setOrderId(transactionDetails.getOrderId());
                userBillingRegModel.setPaymentId(transactionDetails.getPaymentId());
                userBillingRepository.save(userBillingRegModel);
            }
            return transactionDetails;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
//    private TransactionDetails prepareTransactionDetails(Order order,Long userId){
//        try {
//            String orderId = order.get("id");
//            String currency = order.get("currency");
//
//            Integer amount = order.get("amount");
//            String key = order.get("key");
//
//            TransactionDetails transactionDetails = new TransactionDetails(orderId,currency,amount,KEY,userId);
//
//            return transactionDetails;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
