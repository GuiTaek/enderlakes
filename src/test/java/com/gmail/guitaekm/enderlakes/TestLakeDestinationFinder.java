package com.gmail.guitaekm.enderlakes;

import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestLakeDestinationFinder {
    static ConfigInstance smallPrimeConfig = new ConfigInstance(
            19,
            new ConfigModel().powerDistance,
            List.of(
                    1, 4, 3, 4, 0, 6
            ),
            new ConfigModel().minimumDistance
    );
    static ConfigInstance CONFIG =  new ConfigInstance();

    @Test
    public void testPi() {
        List<Integer> piIVals = List.of(
                0,

                2, 1,
                4, 3,

                6, 7, 5,

                9, 10, 11, 8,

                13, 14, 15, 16, 17, 12
        );
        int ind = 0;
        for (Integer piI : piIVals)
            assertEquals(piI, LakeDestinationFinder.pi(ind++, smallPrimeConfig));
    }
    @Test
    public void testCInjective() {
        Set<LakeDestinationFinder.COutput> usedPositions = new HashSet<>();
        for (int i: IntStream.rangeClosed(0, 1000).toArray()) {
            LakeDestinationFinder.COutput outp = LakeDestinationFinder.c(i);
            System.out.println(outp.toString());
            assertFalse(usedPositions.contains(outp), outp.toString());
            usedPositions.add(outp);
        }
    }
}
