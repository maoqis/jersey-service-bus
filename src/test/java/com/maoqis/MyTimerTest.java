package com.maoqis;

import com.maoqis.bean.BusSResp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.maoqis.MyTimer.uH;
import static com.maoqis.MyTimer.uM;
import static org.junit.Assert.assertEquals;

public class MyTimerTest {


    @Before
    public void setUp() throws Exception {
        // start the server);
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
//    @Test
    public void testTimer() {
        //7:40 开始 - 8:00

        //最少 8分钟提醒

        //发现7:55 到8:10
        long l = System.currentTimeMillis();
        System.out.println(l + "");
        Date data = new Date(l);
        Date dateTom = new Date(data.getYear(), data.getMonth(), data.getDate() + 1, 0, 0, 0);
        long duration = dateTom.getTime() - l;
        System.out.println("duration=" + duration + " " + duration / (1000 * 60 * 60) + ":" + duration % (1000 * 60 * 60) / (1000 * 60) + ":" +
                duration % (1000 * 60) / 1000);

        System.out.println("dateTom=" + dateTom.getTime());

        MyTimer.getStartTime();


    }

    @Test
    public void testCheckTime() {
        BusSResp busSResp = new BusSResp();
        busSResp.setMis(7);
        int checkTime = MyTimer.checkTime(busSResp,System.currentTimeMillis()+8*uH+25*uM);
        System.out.println(checkTime);
    }

//    @Test
    public void sendE() {
        MyTimer.requestSend();
    }


}
