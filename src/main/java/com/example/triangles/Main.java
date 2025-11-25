package com.example.triangles;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            if (!sc.hasNextLong()) {
                System.out.println("INVALID_INPUT");
                return;
            }
            long a = sc.nextLong();

            if (!sc.hasNextLong()) {
                System.out.println("INVALID_INPUT");
                return;
            }
            long b = sc.nextLong();

            if (!sc.hasNextLong()) {
                System.out.println("INVALID_INPUT");
                return;
            }
            long c = sc.nextLong();

            Optional<TriangleTypeEnum> type = TriangleClassifier.classify(a, b, c);
            if (type.isEmpty()) {
                System.out.println("NOT_TRIANGLE");
            } else {
                System.out.println(type.get().name());
            }
        } catch (Exception e) {
            System.out.println("INVALID_INPUT");
        }
    }
}
