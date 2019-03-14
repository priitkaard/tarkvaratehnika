package com.qaengine.controllers;

import com.qaengine.models.outputs.Statistics;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {
    public Statistics getPageStatistics() {
        return new Statistics();
    }
}
