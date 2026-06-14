package com.custommock.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custommock.backend.dto.AnalyticsDashboardDto;
import com.custommock.backend.service.AnalyticsService;

@RestController
@RequestMapping("/api/admin/analytics")
@CrossOrigin("*")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public ResponseEntity<AnalyticsDashboardDto> getDashboard() {
        AnalyticsDashboardDto dashboard = analyticsService.getDashboardAnalytics();
        return ResponseEntity.ok(dashboard);
    }

}
