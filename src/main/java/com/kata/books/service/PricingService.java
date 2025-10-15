package com.kata.books.service;

import com.kata.books.model.BookTitle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PricingService {

    private static final BigDecimal UNIT = new BigDecimal("50.00");
    private static final Map<Integer, BigDecimal> DISCOUNT = Map.of(
            1, BigDecimal.ZERO,
            2, new BigDecimal("0.05"),
            3, new BigDecimal("0.10"),
            4, new BigDecimal("0.20"),
            5, new BigDecimal("0.25")
    );

    public BigDecimal price(Map<BookTitle,Integer> basket) {
        int[] counts = new int[BookTitle.values().length];
        for (BookTitle t : BookTitle.values()) counts[t.ordinal()] = Math.max(0, basket.getOrDefault(t, 0));
        Map<String, BigDecimal> memo = new HashMap<>();
        return dp(counts, memo).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal dp(int[] counts, Map<String, BigDecimal> memo) {
        if (Arrays.stream(counts).allMatch(c -> c == 0)) return BigDecimal.ZERO;
        String key = Arrays.toString(counts);
        if (memo.containsKey(key)) return memo.get(key);

        BigDecimal best = null;
        int n = counts.length;

        for (int mask = 1; mask < (1 << n); mask++) {
            int size = Integer.bitCount(mask);

            boolean valid = true;
            for (int i = 0; i < n; i++)
                if (((mask >> i) & 1) == 1 && counts[i] == 0) { valid = false; break; }
            if (!valid) continue;

            int[] next = counts.clone();
            for (int i = 0; i < n; i++) if (((mask >> i) & 1) == 1) next[i]--;

            BigDecimal setPrice = UNIT.multiply(BigDecimal.valueOf(size));
            setPrice = setPrice.multiply(BigDecimal.ONE.subtract(DISCOUNT.get(size)));

            BigDecimal candidate = setPrice.add(dp(next, memo));
            if (best == null || candidate.compareTo(best) < 0) best = candidate;
        }

        memo.put(key, best);
        return best;
    }
}
