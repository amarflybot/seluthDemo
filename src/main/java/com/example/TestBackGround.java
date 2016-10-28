package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by amarendra on 28/10/16.
 */
@Component
public class TestBackGround {

    @Autowired
    private Tracer tracer;
    @Autowired
    private Random random;
    @Autowired
    private Service1 service1;

    @Async
    public void background() throws InterruptedException {
        int millis = this.random.nextInt(1000);
        Thread.sleep(millis);
        this.tracer.addTag("background-sleep-millis", String.valueOf(millis));
    }

    public void testMethod1() {
        try {
            Thread.sleep(2345);
            this.tracer.addTag("in TestMethod1", "value TestMethod1");
            service1.testMethod2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
