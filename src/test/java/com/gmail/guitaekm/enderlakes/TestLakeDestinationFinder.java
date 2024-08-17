package com.gmail.guitaekm.enderlakes;

import com.gmail.guitaekm.enderlakes.LakeDestinationFinder.ChunkPos;
import com.gmail.guitaekm.enderlakes.LakeDestinationFinder.GridPos;
import org.junit.jupiter.api.*;

import java.util.*;
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
        Set<GridPos> usedPositions = new HashSet<>();
        for (int i: IntStream.rangeClosed(1, 1000).toArray()) {
            GridPos outp = LakeDestinationFinder.c(i);
            assertFalse(usedPositions.contains(outp), outp.toString());
            usedPositions.add(outp);
        }
    }
    @Test void testCInvInjective() {
        Set<Integer> usedIntegers = new HashSet<>();
        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                int i = LakeDestinationFinder.cInv(+x, +y);
                assertFalse(usedIntegers.contains(i), String.valueOf(i));
                usedIntegers.add(i);
            }
        }
    }
    @Test void testGetRotation() {
        testRotationWithXAndY(1, 0);
        testRotationWithXAndY(1, 1);
    }
    public static void testRotationWithXAndY(int x, int y) {
        assertEquals(0, LakeDestinationFinder.getRotation(+x, +y));
        assertEquals(1, LakeDestinationFinder.getRotation(-y, +x));
        assertEquals(2, LakeDestinationFinder.getRotation(-x, -y));
        assertEquals(3, LakeDestinationFinder.getRotation(+y, -x));
    }
    @Test
    public void CInvPositive() {
        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Assertions.assertTrue(LakeDestinationFinder.cInv(x, y) >= 1);
            }
        }
    }

    @Test
    public void cThrows() {
        for(int i = -1000; i < 0; i++) {
            final int giveI = i;
            assertThrows(
                    IllegalArgumentException.class,
                    () -> LakeDestinationFinder.c(giveI)
            );
        }
    }

    @Test
    public void cInvThrows() {
        assertThrows(
                IllegalArgumentException.class,
                () -> LakeDestinationFinder.cInv(0, 0)
        );
    }

    @Test
    public void cInvInvOfC() {
        for (int i = 1; i < 1000; i++) {
            assertEquals(i, LakeDestinationFinder.cInv(
                    LakeDestinationFinder.c(i)
            ));
        }
    }

    @Test
    public void fInvInvOffF() {
        // this is approximately the whole range f is meant to work on
        for (int c = -350; c <= 350; c++) {
            assertEquals(
                    c, LakeDestinationFinder.fInv(
                        CONFIG,
                        LakeDestinationFinder.f(CONFIG, c)
                    )
            );
        }
    }

    @Test
    public void rawGridPosInvOfRawPos() {
        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                GridPos oldPos = new GridPos(x, y);
                GridPos newPos = LakeDestinationFinder.getRawGridPos(
                        CONFIG,
                        LakeDestinationFinder.rawPos(CONFIG, oldPos)
                );
                assertEquals(oldPos, newPos);
            }
        }
    }

    //@Test
    public void nearestLakeInvOfPos() {
        Random rand = new Random(42);
        for (int i = 0; i < 10; i++) {
            nearestLakeInvOfPosWithSeed(rand.nextLong());
        }
    }

    public static void nearestLakeInvOfPosWithSeed(long seed) {
        for (int x = -100; x <= 100; x++) {
            for (int y = -100; y <= 100; y++) {
                GridPos oldPos = new GridPos(x, y);
                GridPos newPos = LakeDestinationFinder.findNearestLake(
                        CONFIG,
                        seed,
                        LakeDestinationFinder.pos(CONFIG, seed, oldPos)
                );
                assertEquals(oldPos, newPos);
            }
        }
    }

    //@Test
    public void nearestLakeIsIndeedNearest() {
        Random rand = new Random(42);
        for (int i = 0; i < 10; i++) {
            nearestLakeIsIndeedNearestWithSeed(rand.nextLong());
        }
    }

    public static void nearestLakeIsIndeedNearestWithSeed(long seed) {
        // 350 is approximately the biggest needed grid coordinate
        // define a range, leave enough tolerances for checking really every lake position
        ChunkPos from = LakeDestinationFinder.rawPos(CONFIG, new GridPos(-300, -300));
        ChunkPos to = LakeDestinationFinder.rawPos(CONFIG, new GridPos(300, 300));
        Random rand = new Random(seed);
        int xCheck = rand.nextInt(from.x(), to.x());
        int zCheck = rand.nextInt(from.z(), to.z());
        ChunkPos checkChunk = new ChunkPos(xCheck, zCheck);
        int bestDistance = Integer.MAX_VALUE;
        Set<ChunkPos> bestChunks = new HashSet<>();
        for (int x = -350; x <= 350; x++) {
            for (int y = -350; y <= 350; y++) {
                ChunkPos currChunk = LakeDestinationFinder.pos(CONFIG, seed, new GridPos(x, y));
                int dx = currChunk.x() - xCheck;
                int dz = currChunk.z() - zCheck;
                int currDistSq = dx * dx + dz * dz;
                if (currDistSq == bestDistance) {
                    bestChunks.add(currChunk);
                }
                if (currDistSq < bestDistance) {
                    bestDistance = currDistSq;
                    bestChunks = new HashSet<>();
                    bestChunks.add(currChunk);
                }
            }
        }
        ChunkPos res  = LakeDestinationFinder.pos(
                CONFIG,
                seed,
                LakeDestinationFinder.findNearestLake(
                        CONFIG,
                        seed,
                        checkChunk
                )
        );
        assertTrue(bestChunks.contains(res));
    }
}
