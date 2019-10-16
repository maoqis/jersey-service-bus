package com.maoqis;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.maoqis.bean.BusSResp;

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

        final Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = System.currentTimeMillis();
                long eight = (8) * uH + 0 * uM;
                if (l % uD > eight + 8 * uH){
                    //todo: checkBus

                    if (requestSend()) return;
                    timer.cancel();
                }
            }
        },1,uM);


    }

     static boolean requestSend() {
        BusSResp busSResp;
        busSResp = requestBusSResp();

        if (busSResp == null) {
            return true;
        }

        if (!checkTime(busSResp)) {
            return true;
        }

        SendEmail.sendMailToMe(busSResp.getMis() +"分钟来446" ,BASE_URI+"myresource");
        return false;
    }

    static boolean checkTime(BusSResp busSResp) {
        System.out.printf("start checkTime");
        boolean isSend = false;
        long m = busSResp.getMis()*uM;

        if (m ==0) {
            return isSend;
        }

        long desTime = m+System.currentTimeMillis() + 8*uH;//东8时间

        if (desTime % uD < 8 * uH + 5 * uM  && desTime % uD > 7 * uH + 55 * uM){//7:55 - 8点5分之前的车
            if (m> 8 *uM) {//"时间间隔"
                isSend = true;
            }
        }
        System.out.printf("end checkTime isSend"+ isSend);
        return isSend;
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
        }finally {
            return busSResp;
        }
    }
}
