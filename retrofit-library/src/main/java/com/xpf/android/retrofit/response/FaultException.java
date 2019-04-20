package com.xpf.android.retrofit.response;

/**
 * Created by x-sir on 2019/4/19 :)
 * Function:
 */
public class FaultException extends Throwable {

    public int status;
    public String message;

    public FaultException() {
    }

    public FaultException(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
