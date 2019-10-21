package com.maoqis;

import com.maoqis.utils.Log4jUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class.
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:1994/bus/";
//    public static final String BASE_URI = "http://0.0.0.0:1994/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.maoqis package
        final ResourceConfig rc = new ResourceConfig().packages("com.maoqis");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        Timer timer = new Timer();
        long delay = MyTimer.getStartTime().getTime() - System.currentTimeMillis();
        Log4jUtil.info("main delay="+delay);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                MyTimer.timer15m();
            }
        }, delay, MyTimer.uD);

        Log4jUtil.info(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        timer.cancel();
        server.stop();
    }
}

