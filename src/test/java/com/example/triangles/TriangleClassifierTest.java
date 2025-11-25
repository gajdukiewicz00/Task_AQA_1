package com.example.triangles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Parametrized tests for equivalent classes and boundary values.
 */

public class TriangleClassifierTest {

    /**
     * Single parametrized test for the whole domain:
     *  - verifies isTriangle(...)
     *  - and simultaneously verifies classify(...):
     *      * if it is not a triangle → Optional.empty()
     *      * if it is a triangle → correct TriangleTypeEnum
     */
    @ParameterizedTest(name = "[{index}] ({0},{1},{2}) -> isTriangle={3}, type={4}")
    @CsvSource({
            // NOT_TRIANGLE
            "0,0,0,false,",
            "0,1,1,false,",
            "-1,2,2,false,",
            "1,2,3,false,",
            "5,5,10,false,",
            "1,1000000,1000001,false,",

            // Valid triangles
            "1,1,1,true,EQUILATERAL",
            "5,5,8,true,ISOSCELES",
            "3,4,5,true,SCALENE",
            "2,3,4,true,SCALENE",
            "2,2,3,true,ISOSCELES",
            "10,10,19,true,ISOSCELES",
            "6,7,10,true,SCALENE",
            "2147483647,2147483647,2147483647,true,EQUILATERAL",
            "1500000000,1500000000,2000000000,true,ISOSCELES"
    })
    void testIsTriangleAndClassify(long a,
                                   long b,
                                   long c,
                                   boolean expectedIsTriangle,
                                   String expectedTypeName) {

        boolean actualIsTriangle = TriangleClassifier.isTriangle(a, b, c);
        assertEquals(expectedIsTriangle, actualIsTriangle, "isTriangle mismatch");

        Optional<TriangleTypeEnum> type = TriangleClassifier.classify(a, b, c);

        if (!expectedIsTriangle) {
            assertTrue(type.isEmpty(), "Expected empty Optional for non-triangle");
        } else {
            assertTrue(type.isPresent(), "Expected non-empty Optional for valid triangle");
            TriangleTypeEnum expectedType = TriangleTypeEnum.valueOf(expectedTypeName);
            assertEquals(expectedType, type.get());
        }
    }

    static Stream<long[]> permutationsProvider() {
        return Stream.of(
                new long[]{3, 4, 5},
                new long[]{4, 5, 3},
                new long[]{5, 3, 4},
                new long[]{5, 4, 3},
                new long[]{4, 3, 5},
                new long[]{3, 5, 4}
        );
    }

    @ParameterizedTest(name = "[{index}] permutation classify(3,4,5)")
    @MethodSource("permutationsProvider")
    void testPermutationsInvariant(long[] sides) {
        Optional<TriangleTypeEnum> type =
                TriangleClassifier.classify(sides[0], sides[1], sides[2]);
        assertTrue(type.isPresent());
        assertEquals(TriangleTypeEnum.SCALENE, type.get());
    }

    @ParameterizedTest
    @CsvSource({
            "'1','2','3',1,2,3",
            "' 10','20 ',' 30 ',10,20,30",
            "'0005','0006','0007',5,6,7"
    })
    void parseThreeInts_valid(String a, String b, String c,
                              long ea, long eb, long ec) {
        long[] r = TriangleClassifier.parseThreeInts(a, b, c);
        assertArrayEquals(new long[]{ea, eb, ec}, r);
    }

    @ParameterizedTest
    @CsvSource({
            "'x','2','3'",
            "'1','two','3'",
            "'1','2','9e18'"
    })
    void parseThreeInts_invalid(String a, String b, String c) {
        assertThrows(NumberFormatException.class,
                () -> TriangleClassifier.parseThreeInts(a, b, c));
    }

    @ParameterizedTest
    @MethodSource("nullTriples")
    void parseThreeInts_nulls(String a, String b, String c) {
        assertThrows(NullPointerException.class,
                () -> TriangleClassifier.parseThreeInts(a, b, c));
    }

    static Stream<Arguments> nullTriples() {
        return Stream.of(
                Arguments.of(null, "1", "1"),
                Arguments.of("1", null, "1"),
                Arguments.of("1", "1", null)
        );
    }
}
