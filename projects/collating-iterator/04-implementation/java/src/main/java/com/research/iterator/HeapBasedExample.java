package com.research.iterator;

import java.util.*;

/**
 * Demonstrates HeapBasedIterator usage and edge cases.
 *
 * HeapBasedIterator is the standard O(N log k) textbook implementation
 * using Java's PriorityQueue (binary min-heap). This is the most common
 * approach with excellent cache locality despite 2 log k comparisons.
 */
public class HeapBasedExample {

    public static void main(String[] args) {
        demonstrateBasicUsage();
        demonstrateEdgeCases();
    }

    private static void demonstrateBasicUsage() {
        System.out.println("=== HeapBasedIterator: Basic K-Way Merge ===");

        Iterator<Integer> iter1 = Arrays.asList(1, 4, 7, 10).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 5, 8, 11).iterator();
        Iterator<Integer> iter3 = Arrays.asList(3, 6, 9, 12).iterator();

        HeapBasedIterator<Integer> merged = new HeapBasedIterator<>(
            Arrays.asList(iter1, iter2, iter3)
        );

        System.out.print("Merged sequence: ");
        while (merged.hasNext()) {
            System.out.print(merged.next() + " ");
        }
        System.out.println();
        System.out.println("Expected: 1 2 3 4 5 6 7 8 9 10 11 12");
    }

    private static void demonstrateEdgeCases() {
        System.out.println("\n=== HeapBasedIterator: Edge Cases ===");

        System.out.println("Single iterator:");
        Iterator<String> single = Arrays.asList("alpha", "beta", "gamma").iterator();
        HeapBasedIterator<String> merged1 = new HeapBasedIterator<>(
            Collections.singletonList(single)
        );
        while (merged1.hasNext()) {
            System.out.print(merged1.next() + " ");
        }
        System.out.println();

        System.out.println("\nEmpty iterator in mix:");
        Iterator<Integer> iter1 = Arrays.asList(1, 3, 5).iterator();
        Iterator<Integer> iter2 = Collections.emptyIterator();
        Iterator<Integer> iter3 = Arrays.asList(2, 4, 6).iterator();

        HeapBasedIterator<Integer> merged2 = new HeapBasedIterator<>(
            Arrays.asList(iter1, iter2, iter3)
        );
        while (merged2.hasNext()) {
            System.out.print(merged2.next() + " ");
        }
        System.out.println();

        System.out.println("\nUnequal lengths:");
        Iterator<Integer> short1 = Arrays.asList(1).iterator();
        Iterator<Integer> long1 = Arrays.asList(2, 4, 6, 8, 10).iterator();
        Iterator<Integer> medium = Arrays.asList(3, 5, 7).iterator();

        HeapBasedIterator<Integer> merged3 = new HeapBasedIterator<>(
            Arrays.asList(short1, long1, medium)
        );
        while (merged3.hasNext()) {
            System.out.print(merged3.next() + " ");
        }
        System.out.println();
    }
}
