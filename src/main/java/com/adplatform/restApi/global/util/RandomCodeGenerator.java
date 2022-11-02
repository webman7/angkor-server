package com.adplatform.restApi.global.util;

import java.util.Random;

public class RandomCodeGenerator {
    private final char[] characterTable = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
    };

    public String generateAuthCode(int length) {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(characterTable[random.nextInt(characterTable.length)]);
        return sb.toString();
    }
}
