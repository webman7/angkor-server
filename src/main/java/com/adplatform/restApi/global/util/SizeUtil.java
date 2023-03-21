package com.adplatform.restApi.global.util;

public class SizeUtil {
    private static final int MIN = 1;
    private static final int MAX = 10000;

    public static String getWidthHeightRatio(int width, int height) {
        double ratio = (double) width / (double) height;

        double difference = Double.MAX_VALUE;
        int assumeWidth = 0;
        int assumeHeight = 0;

        for(int i = MIN; i <= MAX; i++) {
            for(int j = MIN; j <= MAX; j++) {
                double testRatio = (double) i / (double) j;
                double testDifference = ratio - testRatio;
                if(testDifference<0) testDifference = -testDifference;
                if(testDifference < difference) {
                    difference = testDifference;
                    assumeWidth = i;
                    assumeHeight = j;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(assumeWidth);
        stringBuilder.append(":");
        stringBuilder.append(assumeHeight);

        return stringBuilder.toString();
    }
}
