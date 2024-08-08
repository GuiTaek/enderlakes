package com.gmail.guitaekm.enderlakes;

/**
 * Follows math.md to implement the logic in this mod
 */
@SuppressWarnings("SuspiciousNameCombination")
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

    // it is difficult to include minecraft's ChunkPos into the test module, I wasn't able to
    public record ChunkPos(int x, int y) {
        net.minecraft.util.math.ChunkPos toMinecraft() {
            return new net.minecraft.util.math.ChunkPos(x, y);
        }
        ChunkPos(net.minecraft.util.math.ChunkPos pos) {
            this(pos.x, pos.z);
        }
    }

    public static ChunkPos c(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("give non-negative input to c");
        }
        int i2 = i - 1;
        // the ordering isn't relevant, because of g therefore no beautiful bijection

        // x can't be 0 but y can, then rotate to get every grid point except (0, 0)
        int rotate = i2 % 4;
        int i3 = i2 / 4;

        // calculate s := x + y by solving for s(s + 1) / 2 = n = i3 + 1 and rounding up (mathematical proof omitted)
        double sFloat = -0.5d + Math.sqrt(1.25d + 2d * (double)i3);
        // if the ceil method would return 0.99999999999999999999999999999998 then (int) would return 0, which is not
        // want. I'm not sure, if this is really the case, and it isn't worth it looking it up.
        int s = (int) (Math.ceil(sFloat) + 0.1d);
        int y = s * (s + 1) / 2 - i3 - 1;

        int x = s - y;

        return switch (rotate) {
            case 0 -> new ChunkPos(+x, +y);
            case 1 -> new ChunkPos(-y, +x);
            case 2 -> new ChunkPos(-x, -y);
            case 3 -> new ChunkPos(+y, -x);
            default -> {
                throw new IllegalStateException("Rotating should be one of 0, 1, 2, 3. Inform the developer of this mod.");
            }
        };
    }

    public static int getRotation(int x, int y) {
        // remember y can be 0
        assert x != 0 || y != 0;
        if (x == 0) {
            return y > 0 ? 1 : 3;
        }
        if (y == 0) {
            return x > 0 ? 0 : 2;
        }
        if (x > 0) {
            if (y > 0) {
                return 0;
            }
            return 3;
        }
        if (y > 0) {
            return 1;
        }
        return 2;
    }

    public static int cInv(int x, int y) {
        if (x == 0 && y == 0) {
            throw new IllegalArgumentException("May not contain the origin");
        }

        int rotation = getRotation(x, y);
        x = Math.abs(x);
        y = Math.abs(y);
        switch(rotation) {
            case 1, 3:
                int temp = x;
                x = y;
                y = temp;
        }
        int s = x + y;
        int res = (s + 1) * s / 2 - y - 1;
        return res * 4 + rotation + 1;
    }
    public static int cInv(ChunkPos outp) {
        return cInv(outp.x, outp.y);
    }
}
