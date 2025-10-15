package com.kata.books.service;

import com.kata.books.model.BookTitle;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

class PricingServiceTest {

    private PricingService service;

    @BeforeEach
    void setup() {
        service = new PricingService();
    }

    private Map<BookTitle,Integer> basket(Object[][] items) {
        Map<BookTitle,Integer> map = new EnumMap<>(BookTitle.class);
        for (Object[] row : items) map.merge((BookTitle) row[0], (Integer) row[1], Integer::sum);
        return map;
    }

    @Test void emptyBasket_isZero() {
        assertThat(service.price(new EnumMap<>(BookTitle.class))).isEqualByComparingTo("0.00");
    }

    @Test void singleBook_is50() {
        var b = basket(new Object[][]{{BookTitle.CLEAN_CODE,1}});
        assertThat(service.price(b)).isEqualByComparingTo("50.00");
    }

    @Test void twoSame_noDiscount() {
        var b = basket(new Object[][]{{BookTitle.CLEAN_CODE,2}});
        assertThat(service.price(b)).isEqualByComparingTo("100.00");
    }

    @Test void twoDifferent_5percent() {
        var b = basket(new Object[][]{
                {BookTitle.CLEAN_CODE,1},
                {BookTitle.THE_CLEAN_CODER,1}
        });
        assertThat(service.price(b)).isEqualByComparingTo("95.00");
    }

    @Test void threeDifferent_10percent() {
        var b = basket(new Object[][]{
                {BookTitle.CLEAN_CODE,1},
                {BookTitle.THE_CLEAN_CODER,1},
                {BookTitle.CLEAN_ARCHITECTURE,1}
        });
        assertThat(service.price(b)).isEqualByComparingTo("135.00");
    }

    @Test void fourDifferent_20percent() {
        var b = basket(new Object[][]{
                {BookTitle.CLEAN_CODE,1},
                {BookTitle.THE_CLEAN_CODER,1},
                {BookTitle.CLEAN_ARCHITECTURE,1},
                {BookTitle.TDD_BY_EXAMPLE,1}
        });
        assertThat(service.price(b)).isEqualByComparingTo("160.00");
    }

    @Test void fiveDifferent_25percent() {
        var b = basket(new Object[][]{
                {BookTitle.CLEAN_CODE,1},
                {BookTitle.THE_CLEAN_CODER,1},
                {BookTitle.CLEAN_ARCHITECTURE,1},
                {BookTitle.TDD_BY_EXAMPLE,1},
                {BookTitle.WORKING_EFFECTIVELY_WITH_LEGACY_CODE,1}
        });
        assertThat(service.price(b)).isEqualByComparingTo("187.50");
    }

    @Test void rulesExample() {
        var b = basket(new Object[][]{
                {BookTitle.CLEAN_CODE,2},
                {BookTitle.THE_CLEAN_CODER,2},
                {BookTitle.CLEAN_ARCHITECTURE,2},
                {BookTitle.TDD_BY_EXAMPLE,1},
                {BookTitle.WORKING_EFFECTIVELY_WITH_LEGACY_CODE,1}
        });
        assertThat(service.price(b)).isEqualByComparingTo("320.00");
    }

}
