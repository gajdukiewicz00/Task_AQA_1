package com.example.triangles;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public final class TriangleClassifier {
    private TriangleClassifier() {}

    public static boolean isTriangle(long a, long b, long c) {
        if (a <= 0 || b <= 0 || c <= 0) return false;

        long[] s = new long[]{a, b, c};
        Arrays.sort(s);
        return s[0] + s[1] > s[2];
    }

    public static Optional<TriangleType> classify(long a, long b, long c) {
        if (!isTriangle(a, b, c)) return Optional.empty();
        if (a == b && b == c) return Optional.of(TriangleType.EQUILATERAL);
        if (a == b || a == c || b == c) return Optional.of(TriangleType.ISOSCELES);
        return Optional.of(TriangleType.SCALENE);
    }

    public static long[] parseThreeInts(String a, String b, String c) {
        Objects.requireNonNull(a); Objects.requireNonNull(b); Objects.requireNonNull(c);
        return new long[] {
                Long.parseLong(a.trim()),
                Long.parseLong(b.trim()),
                Long.parseLong(c.trim())
        };
    }
}
