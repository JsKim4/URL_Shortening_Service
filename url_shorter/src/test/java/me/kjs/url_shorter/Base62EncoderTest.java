package me.kjs.url_shorter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("base62 테스트")
class Base62EncoderTest {
    @Test
    @DisplayName("인코딩 테스트")
    void base62EncodeTest() {
        Base62Encoder base62Encoder = new Base62Encoder();
        String expected1 = "B";
        String expected2 = "C";
        String expected3 = "AB";
        String expected4 = "AAB";
        String actual1 = base62Encoder.encode(1);
        String actual2 = base62Encoder.encode(2);
        String actual3 = base62Encoder.encode(62);
        String actual4 = base62Encoder.encode(62 * 62);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected4, actual4);
    }
}