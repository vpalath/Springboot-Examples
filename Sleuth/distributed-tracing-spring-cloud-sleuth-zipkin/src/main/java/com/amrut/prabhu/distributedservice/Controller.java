package com.amrut.prabhu.distributedservice;

import brave.baggage.BaggageField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.BaggageInScope;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/service")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    Tracer tracer;

    @Autowired
    BaggageField requestIdField;

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/path1")
    public ResponseEntity path1() {

        logger.info("Incoming request at {} for request /path1 ", applicationName);
        requestIdField.updateValue("Vinee Testing New");
//        BaggageInScope requestId = this.tracer.createBaggage("request-Id").set("Vinee Test");
        Map allBaggage = tracer.getAllBaggage();
//        MDC.put("request-Id", "Vinee Test");
        String response = restTemplate.getForObject("http://localhost:8090/service/path2", String.class);
        return ResponseEntity.ok("response from /path1 + " + response);
    }

}
