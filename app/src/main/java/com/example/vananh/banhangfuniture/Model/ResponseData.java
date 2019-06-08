package com.example.vananh.banhangfuniture.Model;

import com.google.gson.JsonObject;

import org.json.JSONObject;

public class ResponseData {
    CustomerInfo customerInfo;
    Boolean isSuccess;
    String error;

    public ResponseData() {
    }

    public CustomerInfo getData() {
        return customerInfo;
    }

    public void setData(CustomerInfo data) {
        this.customerInfo = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
