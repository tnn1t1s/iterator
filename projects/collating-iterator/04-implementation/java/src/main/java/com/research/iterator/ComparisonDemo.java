package com.research.iterator;

import java.util.*;

/**
 * Compares LinearScanIterator, HeapBasedIterator, and LoserTreeIterator
 * on same input to verify correctness and demonstrate usage.
 *
 * All three implementations should produce identical output, demonstrating
 * that they all correctly implement k-way merge. The differences lie in
 * internal complexity and performance characteristics measured in Stage 6.
 */
public class ComparisonDemo {

    public static void main(String[] args) {
        demonstrateSmallK();
        demonstrateLargeK();
    }

    /**
     * Small k=3 example where all algorithms produce identical results.
     */
    private static void demonstrateSmallK() {
        System.out.println("=== Small k=3 Comparison ===\n");

        List<List<Integer>> testData = Arrays.asList(
            Arrays.asList(1, 4, 7, 10),
            Arrays.asList(2, 5, 8, 11),
            Arrays.asList(3, 6, 9, 12)
        );

        System.out.println("Input:");
        for (int i = 0; i < testData.size(); i++) {
            System.out.println("  Iterator " + (i + 1) + ": " + testData.get(i));
        }
        System.out.println();

        runTest(testData, "LinearScan (O(Nk))", "linear");
        runTest(testData, "HeapBased (O(N log k), 2 log k comparisons)", "heap");
        runTest(testData, "LoserTree (O(N log k), log k comparisons)", "loser");
    }

    /**
     * Large k=10 example demonstrating scalability.
     */
    private static void demonstrateLargeK() {
        System.out.println("\n=== Large k=10 Comparison ===\n");

        List<List<Integer>> testData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = i; j < 50; j += 10) {
                values.add(j);
            }
            testData.add(values);
        }

        System.out.println("Input: 10 iterators, each with 5 elements (0-49 interleaved)");
        System.out.println("Expected: 0 1 2 3 4 5 6 7 8 9 10 11 ... 48 49\n");

        runTest(testData, "LinearScan (O(Nk))", "linear");
        runTest(testData, "HeapBased (O(N log k), 2 log k comparisons)", "heap");
        runTest(testData, "LoserTree (O(N log k), log k comparisons)", "loser");
    }

    /**
     * Run a single test with specified algorithm variant.
     */
    private static void runTest(List<List<Integer>> data, String description, String variant) {
        System.out.println(description + ":");

        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (List<Integer> list : data) {
            iterators.add(list.iterator());
        }

        Iterator<Integer> merged;
        switch (variant) {
            case "linear":
                merged = new LinearScanIterator<>(iterators);
                break;
            case "heap":
                merged = new HeapBasedIterator<>(iterators);
                break;
            case "loser":
                merged = new LoserTreeIterator<>(iterators);
                break;
            default:
                throw new IllegalArgumentException("Unknown variant: " + variant);
        }

        List<Integer> result = new ArrayList<>();
        while (merged.hasNext()) {
            result.add(merged.next());
        }

        System.out.print("  Output: ");
        if (result.size() <= 20) {
            System.out.println(result);
        } else {
            for (int i = 0; i < 10; i++) {
                System.out.print(result.get(i) + " ");
            }
            System.out.print("... ");
            for (int i = result.size() - 10; i < result.size(); i++) {
                System.out.print(result.get(i) + " ");
            }
            System.out.println("(total: " + result.size() + " elements)");
        }

        verifySorted(result);
    }

    /**
     * Verify that output is sorted (basic correctness check).
     */
    private static void verifySorted(List<Integer> result) {
        for (int i = 1; i < result.size(); i++) {
            if (result.get(i - 1) > result.get(i)) {
                System.out.println("  ❌ ERROR: Not sorted!");
                return;
            }
        }
        System.out.println("  ✓ Sorted correctly\n");
    }
}
