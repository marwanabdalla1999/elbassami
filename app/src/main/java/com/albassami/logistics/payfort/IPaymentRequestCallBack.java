package com.albassami.logistics.payfort;

/**
 * Created by mostafa_anter on 7/6/17.
 */

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);
}
