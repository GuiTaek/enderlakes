package com.gmail.guitaekm.enderlakes;

import com.gmail.guitaekm.enderlakes.Config;

import java.util.List;

/**
 * Follows math.md to implement the logic in this mod
 */
public class LakeDestinationFinder {
    public static int pi(int i, ConfigInstance config) {
        int weightSum = config.cycleWeights().stream().mapToInt(w -> w).sum();
        int[] nrLakesPerCycle = config.cycleWeights()
                .stream()
                .mapToDouble(w -> (double) w / weightSum)
                .mapToInt(p -> (int) Math.round(config.nrLakes() * p))
                .toArray();
        int unUsedCycles = i;
        for (int cycleLength = 1; cycleLength <= nrLakesPerCycle.length; cycleLength++) {
            unUsedCycles -= nrLakesPerCycle[cycleLength - 1];
            if (unUsedCycles < 0) {
                int temp = unUsedCycles % cycleLength;
                int cyclePos;
                if (temp < 0) {
                    cyclePos = temp + cycleLength;
                } else {
                    cyclePos = temp;
                }
                if (cyclePos == cycleLength - 1) {
                    return i - cycleLength + 1;
                }
                return i + 1;
            }
        }
        if (i < config.nrLakes()) {
            return i;
        }
        throw new IllegalArgumentException("integer not part of the permutation");
    }
}
