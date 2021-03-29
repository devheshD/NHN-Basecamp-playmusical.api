package com.playmuscial.api.util;


import com.playmuscial.api.enums.PossibleCancel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CommonTest {

    @Test
    void getPretimeFirstresevation() {
        Long pretimeFirstresevation = Common.PRETIME_FIRSTRESEVATION;
        assertEquals((Long) (30L), pretimeFirstresevation);
    }

    @Test
    void getPretimeSecondresevation() {
        Long pretimeSecondreservation = Common.PRETIME_SECONDRESERVATION;

        assertEquals((Long) 600L, pretimeSecondreservation);
    }

    @Test
    void getPossibleCancle() {
        int NO = PossibleCancel.NO.ordinal();
        int YES = PossibleCancel.YES.ordinal();
        // assertEquals(0, NO);
        // assertEquals(1, YES);
    }
}
