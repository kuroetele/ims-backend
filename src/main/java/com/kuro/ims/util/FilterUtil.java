package com.kuro.ims.util;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.domain.Specification;

public class FilterUtil
{
    public static <T> Specification<T> buildCreatedAtFilter(LocalDate startDate, LocalDate endDate)
    {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (startDate != null && endDate != null)
            {
                return criteriaBuilder.between(
                    root.get("createdAt"),
                    startDate.atTime(LocalTime.MIN), endDate.atTime(LocalTime.MAX));
            }
            return null;
        };
    }
}
