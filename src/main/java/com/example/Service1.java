package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

/**
 * Created by amarendra on 28/10/16.
 */
@Service
public class Service1 {

    //private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Async
    @Loggable
    public void testMethod2() throws InterruptedException {
        Thread.sleep(2000);
        //log.info("in TestMethod2", "Value of TestMethod2");
        Thread.sleep(2000);
    }
}
