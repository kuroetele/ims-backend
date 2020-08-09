package com.kuro.ims.controller;

import com.kuro.ims.dto.Response;
import com.kuro.ims.service.DashboardService;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@AllArgsConstructor
public class DashboardController
{
    private final DashboardService dashboardService;


    @GetMapping("/total-counts")
    public Response<?> getTotalCounts()
    {
        return Response.<Map<String, Long>>builder()
            .data(dashboardService.totalCounts())
            .build();
    }


    @GetMapping("/total-sums")
    public Response<?> getTotalSums()
    {
        return Response.<Map<String, BigDecimal>>builder()
            .data(dashboardService.getTotalSums())
            .build();
    }
}
