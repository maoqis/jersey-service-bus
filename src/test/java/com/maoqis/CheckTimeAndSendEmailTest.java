package com.maoqis;

import com.maoqis.bean.BusSResp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static com.maoqis.MyTimer.uH;
import static com.maoqis.MyTimer.uM;

public class CheckTimeAndSendEmailTest {


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
    @Test
    public void testTimer() {
        CheckTimeAndSendEmail checkTimeAndSendEmail = new CheckTimeAndSendEmail(System.currentTimeMillis()+8*uH+44*uM);
        checkTimeAndSendEmail.invoke();
        int m1 = checkTimeAndSendEmail.getIsSend();
        String m2 = checkTimeAndSendEmail.getMsg();
        System.out.println("isSend=" + m1);
        System.out.println("msg=" + m2);
    }


}
