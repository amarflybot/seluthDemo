package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

/**
 * Created by amarendra on 28/10/16.
 */
@Service
public class Service1 {

    @Autowired
    private Tracer tracer;

    public void testMethod2() throws InterruptedException {
        Thread.sleep(2000);
        this.tracer.addTag("in TestMethod2", "Value of TestMethod2");
        Thread.sleep(2000);
    }
}
