package com.kata.books.api.controller;

import com.kata.books.api.dto.*;
import com.kata.books.model.*;
import com.kata.books.service.*;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.EnumMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final PricingService service;

    public BookController(PricingService service) {
        this.service = service;
    }

    @PostMapping(path = "/pricing", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PriceResponse price(@Valid @RequestBody PriceRequest request) {
        Map<BookTitle, Integer> basket = new EnumMap<>(BookTitle.class);
        for (BookDto item : request.items()) {
            basket.merge(item.title(), item.quantity(), Integer::sum);
        }
        return new PriceResponse(service.price(basket));
    }

    @GetMapping("/catalog")
    public BookTitle[] catalog() {
        return BookTitle.values();
    }
}
