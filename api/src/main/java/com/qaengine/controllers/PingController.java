package com.qaengine.controllers;

import com.qaengine.models.Ping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    @CrossOrigin(origins = "http://localhost:8081")
    public Ping getPing() {
        return new Ping("Pong");
    }
}
