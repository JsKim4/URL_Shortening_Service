package me.kjs.url_shorter;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {
    private final char[] TABLE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
            'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9'
    };
    private final Integer BASE62 = 62;

    public String encode(long inputNumber) {
        StringBuilder stringBuilder = new StringBuilder();
        while (inputNumber > 0) {
            stringBuilder.append(TABLE[(int) (inputNumber % BASE62)]);
            inputNumber /= BASE62;
        }
        return stringBuilder.toString();
    }
}

