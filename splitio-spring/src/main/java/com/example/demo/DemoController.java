package com.example.demo;

import io.split.client.SplitClient;
import io.split.client.api.SplitResult;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import split.com.google.gson.Gson;
import java.util.*;

import java.security.Principal;

@RestController
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    private SplitClient splitClient;

    private Gson gson;

    public DemoController(SplitClient splitClient, SplitClient splitClientLocalhost) {
        this.splitClient = splitClient;
        this.gson = new Gson();
    }

    // treatment name pulled from application.properties
    @Value("#{ @environment['split.api.treatement-name'] }")
    private String treatmentName;

    @GetMapping("/demo")
    public String home(Principal principal, @RequestParam(required = false) String likes,
            @RequestParam(required = false) Integer age) {

        // start time for perofrmance tracking
        long startTime = System.nanoTime();

        // get the user name (you use this to retrieve the split treatment)
        String userName = principal.getName();
        logger.info("Authenticated user " + userName + " : " + principal.toString());

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("likes", likes);
        attributes.put("age", age);

        // get the split treatment for the given authenticated user
        SplitResult result = splitClient.getTreatmentWithConfig(userName, treatmentName, attributes);
        String treatment = result.treatment();

        // our flag
        boolean randomData;

        // set the boolean flag according to the treatment state
        if (treatment.equals("on")) {
            logger.info("Treatment " + treatmentName + " is ON");
            randomData = true;
        } else if (treatment.equals("off")) {
            randomData = false;
            logger.info("Treatment " + treatmentName + " is OFF");
        } else if (treatment.equals("maybe")) {
            randomData = Math.random() > 0.5 ? true : false;
            logger.info("Treatment " + treatmentName + " maybe ON or OFF");
        } else if (treatment.equalsIgnoreCase("control")) {
            throw new RuntimeException("control treatment from Split.");
        } else {
            throw new RuntimeException("Couldn't retrieve treatment from Split.");
        }

        if (null != result.config()) {

        } else {
            logger.warn("no config retrieved");
        }

        MyConfiguration config = new MyConfiguration();
        // get the millis delay value from the treatment configuration
        int timeDelay = 0;
        if (null != result.config()) {
            config = gson.fromJson(result.config(), MyConfiguration.class);
            logger.info("Config: {}", config);
            timeDelay = config.getTimeDelay();
        }

        // build the response based on the treatment values

        // default response
        String response = "Hey, " + userName;

        // is this feature turned on in our treatment?
        if (randomData) {
            response = "Hey, " + userName + ", Random number: " + UUID.randomUUID().toString();
        }
        response += "\n Config: " + config;

        // does the configuration specify a delay?
        if (timeDelay > 0) {
            try {
                logger.info("Threat.sleeping for " + timeDelay + " milliseconds");
                Thread.sleep(timeDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // stop time for performance measuring
        long stopTime = System.nanoTime();

        // calculate millis execution time
        double methodDurationMillis = (double) (stopTime - startTime) / 1000000;

        logger.info("methodDurationMillis = " + methodDurationMillis);

        // track the performance metric
        splitClient.track(userName, "user", "method_duration_time_millis", methodDurationMillis);

        return response;

    }

    @Data
    private class MyConfiguration {
        private String color;
        private String condition;
        private int timeDelay;
    }
}
