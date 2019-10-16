package com.maoqis;

import com.google.gson.Gson;
import com.maoqis.bean.BusResp;
import com.maoqis.bean.BusSResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
//http://www.bjbus.com/home/ajax_rtbus_data.php?act=busTime&selBLine=446&selBDir=5536189631008648804&selBStop=8
        BusResp resp = null;
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://www.bjbus.com").path("/home/ajax_rtbus_data.php");
            String t = target.queryParam("act", "busTime")
                    .queryParam("selBLine", 446)
                    .queryParam("selBDir", "5536189631008648804")
                    .queryParam("selBStop", "8")
                    .request(MediaType.TEXT_PLAIN_TYPE)
                    .get(String.class);
            resp = new Gson().fromJson(t, BusResp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String html = resp.getHtml();

        String start = "所属客四分公司</p><p>";
        String end = "</p></article></div></div>";

        Matcher matcher = Pattern.compile(start).matcher(html);
        boolean m = matcher.find();
        if (!m) {
            return "start not match";
        }
        int xStart = matcher.end();

        matcher = Pattern.compile(end).matcher(html);
        m = matcher.find();
        if (!m) {
            return "end not match";
        }
        int xEnd = matcher.start();


        BusSResp busSResp = new BusSResp();
        try {
            String s = html.substring(xStart, xEnd);
//        最近一辆车距离此还有 2 站， 1.32</span> 公里，预计到站时间 3</span> 分钟
//        车辆均已过站
            if (!s.contains("分钟")) {
                return "not cat";
            }

            String[] split = s.split("，");
            for (int i = 0; i < split.length; i++) {
                String param = split[i];
                String group = getDi(param);
                if (i == 0) {
                    busSResp.setStation(Integer.valueOf(group));
                } else if (i == 1) {
                    busSResp.setDistance(Float.valueOf(group));
                } else if (i == 2) {
                    busSResp.setMis(Integer.valueOf(group));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String ret  = new Gson().toJson(busSResp);
        return ret ;
    }

    public  String getDi(String content) {
        Pattern p = Pattern.compile("[^\\d]+([\\d]+)[^\\d]+.*");
        Matcher m = p.matcher(content);
        boolean result = m.find();
        String find_result = null;
        if (result) {
            find_result = m.group(1);
        }
        return find_result;
    }
}


