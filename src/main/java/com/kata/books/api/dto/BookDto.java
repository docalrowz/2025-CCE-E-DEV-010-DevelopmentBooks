package com.kata.books.api.dto;

import com.kata.books.model.BookTitle;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookDto(@NotNull BookTitle title, @Min(1) int quantity) {}
