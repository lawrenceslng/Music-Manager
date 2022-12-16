package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SongIntervalTest {

    SongInterval interval1, interval2, interval3;

    @BeforeEach
    void setup() {
        interval1 = new SongInterval(10);
        interval2 = new SongInterval(211);
        interval3 = new SongInterval(154);
    }

    @Test
    void testToString() {
        assertEquals(interval1.toString(), "0:10");
        assertEquals(interval2.toString(), "3:31");
        assertEquals(interval3.toString(), "2:34");
    }
}