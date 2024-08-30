package com.sos.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PercentageCalculatorTest {

    @Test
    public void calculatePercentage() {
        float percentage = PercentageCalculator.calculatePercentage(10, 50);
        assertThat(20.0F, is(percentage));
    }

    @Test
    public void calculatePercentageWithZero() {
        float percentage = PercentageCalculator.calculatePercentage(10, 0);
        assertThat(0.0F, is(percentage));
    }

    @Test
    public void calculatePercentageWithZeroResults() {
        float percentage = PercentageCalculator.calculatePercentage(0, 80);
        assertThat(0.0F, is(percentage));
    }
}
