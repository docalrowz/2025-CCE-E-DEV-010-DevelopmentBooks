package com.kata.books.service;

import com.kata.books.model.BookTitle;
import java.math.BigDecimal;
import java.util.Map;

public class PricingService {

    //For test purpose
    public BigDecimal price(Map<BookTitle, Integer> basket) {
        return new BigDecimal("0.00");
    }
}
