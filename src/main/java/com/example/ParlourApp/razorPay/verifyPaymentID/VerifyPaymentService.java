package com.example.ParlourApp.razorPay.verifyPaymentID;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Service;

@Service
public class VerifyPaymentService
{
    private static String razorpayKeyId = "rzp_test_Lh738g2oARGFbD";
    private static String razorpayKeySecret = "iOSGwx2YAmHsl2dNuzfi1bSa";
    private static final String currency = "INR";
    public boolean verifyPayment(String orderId, String paymentId, String signature ) throws RazorpayException {
        try {
            if (!verifySignature(orderId,paymentId,signature)){
                throw new RazorpayException("Signature Verification Failed");
            }
            RazorpayClient razorpayClient =new RazorpayClient(razorpayKeyId,razorpayKeySecret);
            Payment payment =razorpayClient.payments.fetch(paymentId);
            return "captured".equals(payment.get("status"));

        }catch (RazorpayException e){
            e.printStackTrace();
            throw new RazorpayException("Payment verification failed.", e);
        }
    }

    public boolean verifySignature(String orderId,String paymentId,String signature){


//        String secret = "iOSGwx2YAmHsl2dNuzfi1bSa";
        String secret = razorpayKeySecret;
        String data = orderId + " | "+paymentId;
      String  generatedSignature = HmacUtils.hmacSha256Hex(secret, data);
        return generatedSignature.equals(signature);
    }
}
