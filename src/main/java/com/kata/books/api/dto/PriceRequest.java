package com.kata.books.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PriceRequest(@NotEmpty List<@Valid BookDto> items) {}
