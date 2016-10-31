package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.Random;
import java.util.concurrent.Callable;
/**
 * Created by amarendra on 28/10/16.
 */
@RestController
public class TestController implements
        ApplicationListener<EmbeddedServletContainerInitializedEvent> {


    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private RestTemplate restTemplate;
    /*@Autowired
    private Tracer tracer;
    @Autowired
    private SpanAccessor accessor;*/
    @Autowired
    private TestBackGround controller;
    @Autowired
    private Random random;
    private int port;

    @RequestMapping("/")
    public String hi() throws InterruptedException {
        Thread.sleep(this.random.nextInt(1000));
        log.info("Home page");
        String s = this.restTemplate.getForObject("http://localhost:" + this.port
                + "/hi2", String.class);
        return "hi/" + s;
    }

    @RequestMapping("/call")
    public Callable<String> call() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                int millis = TestController.this.random.nextInt(1000);
                Thread.sleep(millis);
                //TestController.this.tracer.addTag("callable-sleep-millis", String.valueOf(millis));
                log.info("callable-sleep-millis", String.valueOf(millis));
                //Span currentSpan = TestController.this.accessor.getCurrentSpan();

                return "async hi: " ;
            }
        };
    }

    @RequestMapping("/testCall")
    public String testCall() throws InterruptedException {
        /*Span span = this.tracer.createSpan("http:testCall",
                new AlwaysSampler());
        this.tracer.addTag("In testCall", "value of TestCall");*/
        log.info("In testCall", "value of TestCall");
        controller.testMethod1();
        Thread.sleep(5000);
        /*span.stop();
        this.tracer.close(span);*/
        return "in TestCall";
    }

    @RequestMapping("/async")
    public String async() throws InterruptedException {
        log.info("async");
        this.controller.background();
        return "ho";
    }

    @RequestMapping("/hi2")
    public String hi2() throws InterruptedException {
        log.info("hi2");
        int millis = this.random.nextInt(1000);
        Thread.sleep(millis);
        //this.tracer.addTag("random-sleep-millis", String.valueOf(millis));
        log.info("random-sleep-millis", String.valueOf(millis));
        return "hi2";
    }

    @RequestMapping("/traced")
    public String traced() throws InterruptedException {
        /*Span span = this.tracer.createSpan("http:customTraceEndpoint",
                new AlwaysSampler());*/
        int millis = this.random.nextInt(1000);
        log.info(String.format("Sleeping for [%d] millis", millis));
        Thread.sleep(millis);
        //this.tracer.addTag("random-sleep-millis", String.valueOf(millis));

        String s = this.restTemplate.getForObject("http://localhost:" + this.port
                + "/call", String.class);
        //this.tracer.close(span);
        return "traced/" + s;
    }

    @RequestMapping("/start")
    public String start() throws InterruptedException {
        int millis = this.random.nextInt(1000);
        log.info(String.format("Sleeping for [%d] millis", millis));
        Thread.sleep(millis);
        //this.tracer.addTag("random-sleep-millis", String.valueOf(millis));
        log.info("random-sleep-millis", String.valueOf(millis));

        String s = this.restTemplate.getForObject("http://localhost:" + this.port
                + "/call", String.class);
        return "start/" + s;
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        this.port = event.getEmbeddedServletContainer().getPort();
    }

}
