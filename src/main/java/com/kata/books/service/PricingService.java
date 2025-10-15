package com.kata.books.service;

import com.kata.books.model.BookTitle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.EnumMap;

public class PricingService {

    private static final BigDecimal UNIT = new BigDecimal("50.00");

    public BigDecimal price(Map<BookTitle,Integer> basket) {
        Map<BookTitle,Integer> left = new EnumMap<>(basket);
        BigDecimal total = BigDecimal.ZERO;

        while (left.values().stream().anyMatch(q -> q > 0)) {
            int size = 0;
            for (BookTitle t : BookTitle.values()) {
                if (left.getOrDefault(t,0) > 0) {
                    left.put(t, left.get(t) - 1);
                    size++;
                }
            }
            BigDecimal set = UNIT.multiply(BigDecimal.valueOf(size));
            BigDecimal discount = switch (size) {
                case 2 -> new BigDecimal("0.05");
                case 3 -> new BigDecimal("0.10");
                case 4 -> new BigDecimal("0.20");
                case 5 -> new BigDecimal("0.25");
                default -> BigDecimal.ZERO;
            };
            total = total.add(set.multiply(BigDecimal.ONE.subtract(discount)));
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
