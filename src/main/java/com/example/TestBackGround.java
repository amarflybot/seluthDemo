package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by amarendra on 28/10/16.
 */
@Component
public class TestBackGround {

/*    @Autowired
    private Tracer tracer;*/
    @Autowired
    private Random random;
    @Autowired
    private Service1 service1;

    //private static final Log log = LogFactory.getLog(TestBackGround.class);

    @Async
    public void background() throws InterruptedException {
        int millis = this.random.nextInt(1000);
        Thread.sleep(millis);
        //log.info("background-sleep-millis"+ String.valueOf(millis));
        //this.tracer.addTag("background-sleep-millis", String.valueOf(millis));
    }

    @Async
    @Loggable
    public void testMethod1() {
        try {
            Thread.sleep(2345);
            //log.info("in TestMethod1"+ "value TestMethod1");
            //this.tracer.addTag("in TestMethod1", "value TestMethod1");
            service1.testMethod2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
