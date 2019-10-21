package com.maoqis;

import com.google.gson.Gson;
import com.maoqis.bean.BusSResp;
import com.maoqis.utils.Log4jUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.maoqis.Main.BASE_URI;

public class MyTimer {
    public static final long uS = 1000L;
    public static final long uM = uS * 60;
    public static final long uH = uM * 60;
    public static final long uD = 24 * uH;

    public static Date getStartTime() {
        String zero = "1571155200000";


        long l = System.currentTimeMillis();//在过8小时我们已经到0点了
        Date start;
        long seven40 = (7) * uH + 40 * uM;
        if (l % uD > seven40 + 8 * uH) {
            start = new Date(l - l % uD + uD + seven40 - 8 * uH);
        } else {
            start = new Date(l / uD * uD + seven40 - 8 * uH);
        }
        System.out.println(start);
        System.out.println(new Date(l));
        return start;
    }

    public static void timer15m() {
        Log4jUtil.info(MyTimer.class.getSimpleName(), "timer15m ++++++++");
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log4jUtil.info("start first timer15m+++");
                long l = System.currentTimeMillis();
                CheckTimeAndSendEmail checkTimeAndSendEmail = new CheckTimeAndSendEmail(l).invoke();
                int isSend = checkTimeAndSendEmail.getIsSend();
                String msg = checkTimeAndSendEmail.getMsg();

                Log4jUtil.info("timer15m" + "isSend=" + isSend + ":" + msg);

                if (isSend > 0) {
                    timer.cancel();
                    Log4jUtil.info(MyTimer.class.getSimpleName(), "timer15m cancel--------");
                }
            }
        }, 1, uM);


    }

    static int requestSend() {


        boolean inTime = false;
        long desTime = System.currentTimeMillis() + 8 * uH;//东8时间
        long dayTime = desTime % uD;
        if (dayTime < 8 * uH + 0 * uM && dayTime > 7 * uH + 45 * uM) {//7:55 - 8点5分之前的车
            inTime = true;
        }
        Log4jUtil.info("CheckTimeAndSendEmail requestSend inTime= " + inTime);
        if (!inTime) {
            return -6;
        }
        BusSResp busSResp;
        busSResp = requestBusSResp();

        Log4jUtil.info("CheckTimeAndSendEmail requestSend BusSResp= " + busSResp);

        if (busSResp == null) {
            return -1;
        }

        int checkTime = checkTime(busSResp, System.currentTimeMillis());
        Log4jUtil.info("CheckTimeAndSendEmail requestSend checkTime= " + checkTime);
        if (checkTime <= 0) {
            return checkTime;
        }

        SendEmail.sendMailToMe(busSResp.getMis() + "分钟来446", BASE_URI + "myresource");
        return 1;
    }

    static int checkTime(BusSResp busSResp, long currentTimeMillis) {

        Log4jUtil.info("start checkTime");
        long m = busSResp.getMis() * uM;

        if (m == 0) {
            return -2;
        }

        long desTime = m + currentTimeMillis + 8 * uH;//东8时间

        long dayTime = desTime % uD;

        Log4jUtil.info("dayTime=" + dayTime + " " + dayTime / uH + ":" + dayTime % uH / uM);

        if (dayTime < 8 * uH + 5 * uM && dayTime > 7 * uH + 55 * uM) {//7:55 - 8点5分之前的车
            if (m > 6 * uM) {//"时间间隔"
                return 1;
            }
        }
        return -3;
    }

    private static BusSResp requestBusSResp() {
        System.out.printf("requestBusSResp");
        MyResource myResource = new MyResource();
        String res = myResource.getIt();
        BusSResp busSResp = null;
        try {
            busSResp = new Gson().fromJson(res, BusSResp.class);
        } catch (Exception e) {
            e.printStackTrace();
            busSResp = null;
        } finally {
            return busSResp;
        }
    }


}
