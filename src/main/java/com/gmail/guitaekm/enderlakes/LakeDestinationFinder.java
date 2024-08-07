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

    public record COutput(int x, int y) { }

    public static COutput c(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("give non-negative input to c");
        }
        if (i == 0) {
            return new COutput(0, 0);
        }
        int i2 = i - 1;
        // the ordering isn't relevant, because of g therefore no beautiful bijection
        int rotate = i2 % 4;
        int i3 = i2 / 4;

        // calculate s := x + y by solving for s(s + 1) / 2 = n = i3 + 1 and rounding up (mathematical proof omitted)
        double sFloat = -0.5d + Math.sqrt(1.25d + 2d * (double)i3);
        // if the ceil method would return 0.99999999999999999999999999999998 then (int) would return 0, which is not
        // want. I'm not sure, if this is really the case, and it isn't worth it looking it up.
        int s = (int) (Math.ceil(sFloat) + 0.1d);
        int y = i3 + 1 - s * (s + 1) / 2 + 1;

        // -1 because x should sometimes become zero
        int x = s - y;

        return switch (rotate) {
            case 0 -> new COutput(+x, +y);
            case 1 -> new COutput(-y, +x);
            case 2 -> new COutput(-x, -y);
            case 3 -> new COutput(+y, -x);
            default -> {
                throw new IllegalStateException("Rotating should be one of 0, 1, 2, 3. Inform the developer of this mod.");
            }
        };
    }
}
