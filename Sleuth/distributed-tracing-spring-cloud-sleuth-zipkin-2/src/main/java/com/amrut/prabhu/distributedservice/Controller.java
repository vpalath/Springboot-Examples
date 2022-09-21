package com.amrut.prabhu.distributedservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/path2")
    public ResponseEntity path2(RequestEntity request) {
        logger.info("Incoming request at {} at /path2", applicationName);
        request.getHeaders().entrySet().forEach((e) -> {
            logger.info("k {} v {}", e.getKey(), e.getValue());
        });
        return ResponseEntity.ok("response from /path2 ");
    }
}
