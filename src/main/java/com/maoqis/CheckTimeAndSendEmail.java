package com.maoqis;

import com.maoqis.utils.Log4jUtil;

import static com.maoqis.MyTimer.*;

public class CheckTimeAndSendEmail {
    private long l;
    private int isSend;
    private String msg;

    public CheckTimeAndSendEmail(long l) {
        this.l = l;
    }

    public int getIsSend() {
        return isSend;
    }

    public String getMsg() {
        return msg;
    }

    public CheckTimeAndSendEmail invoke() {
        long minTime = SEVER_START_TIME + 15 * uM;
        long maxTime = SEVER_START_TIME + 25 * uM;
        long bjTime = (l + 8 * uH) % uD;


        isSend = 0;
        boolean isStartTime = false;
        if (bjTime >= minTime && bjTime <= maxTime) {
            isStartTime = true;

        }
        Log4jUtil.info("CheckTimeAndSendEmail invoke isStartTime= "+isStartTime);
        if (isStartTime) {
            isSend = requestSend();
        }

        msg = "timer15m start";
        msg += " l = " + l;
        msg += " bjTime = " + bjTime;
        msg += " isSend = " + isSend;
        return this;
    }
}