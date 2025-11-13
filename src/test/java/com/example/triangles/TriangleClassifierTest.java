package com.example.triangles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

/** Параметризованные тесты: эквивалентные классы и граничные значения. */
public class TriangleClassifierTest {

    @ParameterizedTest(name = "[{index}] isTriangle({0},{1},{2}) => {3}")
    @CsvSource({
            "0,0,0,false",
            "0,1,1,false",
            "-1,2,2,false",
            "1,2,3,false",
            "2,3,4,true",
            "5,5,10,false",
            "2147483647,2147483647,2147483647,true",
            "1500000000,1500000000,2000000000,true",
            "1,1000000,1000001,false",
            "10,10,19,true",
            "6,7,10,true"
    })
    void testIsTriangle(long a, long b, long c, boolean expected) {
        assertEquals(expected, TriangleClassifier.isTriangle(a, b, c));
    }


    @ParameterizedTest(name = "[{index}] classify({0},{1},{2}) => {3}")
    @CsvSource({
            "1,1,1,EQUILATERAL",
            "5,5,8,ISOSCELES",
            "3,4,5,SCALENE",
            "10,10,19,ISOSCELES",
            "2,3,4,SCALENE",
            "2147483647,2147483647,2147483647,EQUILATERAL"
    })
    void testClassifyValid(long a, long b, long c, TriangleType expectedType) {
        Optional<TriangleType> type = TriangleClassifier.classify(a, b, c);
        assertTrue(type.isPresent());
        assertEquals(expectedType, type.get());
    }


    @ParameterizedTest(name = "[{index}] classify invalid => empty ({0},{1},{2})")
    @CsvSource({
            "0,0,0",
            "0,1,1",
            "-1,2,2",
            "1,2,3",
            "5,5,10",
            "1,1000000,1000001"
    })
    void testClassifyInvalid(long a, long b, long c) {
        assertTrue(TriangleClassifier.classify(a, b, c).isEmpty());
    }


    static Stream<long[]> permutationsProvider() {
        return Stream.of(
                new long[]{3,4,5},
                new long[]{4,5,3},
                new long[]{5,3,4},
                new long[]{5,4,3},
                new long[]{4,3,5},
                new long[]{3,5,4}
        );
    }

    @ParameterizedTest(name = "[{index}] permutation classify(3,4,5)")
    @MethodSource("permutationsProvider")
    void testPermutationsInvariant(long[] sides) {
        Optional<TriangleType> type = TriangleClassifier.classify(sides[0], sides[1], sides[2]);
        assertTrue(type.isPresent());
        assertEquals(TriangleType.SCALENE, type.get());
    }

    @ParameterizedTest
    @CsvSource({
            "'1','2','3',1,2,3",
            "'  10','20  ',' 30 ',10,20,30",
            "'0005','0006','0007',5,6,7"
    })
    void parseThreeInts_valid(String a, String b, String c, long ea, long eb, long ec) {
        long[] r = TriangleClassifier.parseThreeInts(a,b,c);
        assertArrayEquals(new long[]{ea,eb,ec}, r);
    }

    @ParameterizedTest
    @CsvSource({
            "'x','2','3'",
            "'1','two','3'",
            "'1','2','9e18'" // слишком большое значение, NumberFormatException
    })
    void parseThreeInts_invalid(String a, String b, String c) {
        assertThrows(NumberFormatException.class, () -> TriangleClassifier.parseThreeInts(a,b,c));
    }

    @ParameterizedTest
    @MethodSource("nullTriples")
    void parseThreeInts_nulls(String a, String b, String c) {
        assertThrows(NullPointerException.class, () -> TriangleClassifier.parseThreeInts(a,b,c));
    }
    static Stream<Arguments> nullTriples() {
        return Stream.of(
                Arguments.of(null, "1", "1"),
                Arguments.of("1", null, "1"),
                Arguments.of("1", "1", null)
        );
    }
}
