package com.kuro.ims.controller;

import com.kuro.ims.config.security.CustomUserDetails;
import com.kuro.ims.dto.Response;
import com.kuro.ims.entity.User;
import com.kuro.ims.service.DashboardService;
import com.kuro.ims.type.Role;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public Response<?> getTotalCounts(Authentication authentication)
    {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        return Response.<Map<String, Long>>builder()
            .data(dashboardService.getTotalCounts(user.getRole() == Role.USER ? user.getId() : null))
            .build();
    }


    @GetMapping("/total-sums")
    public Response<?> getTotalSums(Authentication authentication)
    {
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        return Response.<Map<String, BigDecimal>>builder()
            .data(dashboardService.getTotalSums(user.getRole() == Role.USER ? user.getId() : null))
            .build();
    }
}
