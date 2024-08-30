package com.sos.utils;

public class PercentageCalculator {

    public static float calculatePercentage(float result, long total) {
        if (total == 0) {
            return 0;
        }
        return result / total * 100;
    }
}
