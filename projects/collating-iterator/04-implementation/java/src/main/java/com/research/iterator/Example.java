package com.research.iterator;

import java.util.*;

/**
 * Example usage of CollatingIterator demonstrating k-way merge.
 */
public class Example {

    public static void main(String[] args) {
        demonstrateBasicMerge();
        demonstrateEdgeCases();
    }

    /**
     * Basic example: merge three sorted sequences.
     */
    private static void demonstrateBasicMerge() {
        System.out.println("=== Basic K-Way Merge Example ===");

        // Three sorted sequences
        Iterator<Integer> iter1 = Arrays.asList(1, 4, 7, 10).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 5, 8, 11).iterator();
        Iterator<Integer> iter3 = Arrays.asList(3, 6, 9, 12).iterator();

        // Merge using CollatingIterator
        CollatingIterator<Integer> merged = new CollatingIterator<>(
            Arrays.asList(iter1, iter2, iter3)
        );

        System.out.print("Merged sequence: ");
        while (merged.hasNext()) {
            System.out.print(merged.next() + " ");
        }
        System.out.println();
        // Expected: 1 2 3 4 5 6 7 8 9 10 11 12
    }

    /**
     * Edge cases: empty iterators, single iterator, unequal lengths.
     */
    private static void demonstrateEdgeCases() {
        System.out.println("\n=== Edge Cases ===");

        // Case 1: Single iterator
        System.out.println("Single iterator:");
        Iterator<String> single = Arrays.asList("alpha", "beta", "gamma").iterator();
        CollatingIterator<String> merged1 = new CollatingIterator<>(
            Collections.singletonList(single)
        );
        while (merged1.hasNext()) {
            System.out.print(merged1.next() + " ");
        }
        System.out.println();

        // Case 2: Empty iterator in mix
        System.out.println("\nEmpty iterator in mix:");
        Iterator<Integer> iter1 = Arrays.asList(1, 3, 5).iterator();
        Iterator<Integer> iter2 = Collections.emptyIterator();
        Iterator<Integer> iter3 = Arrays.asList(2, 4, 6).iterator();

        CollatingIterator<Integer> merged2 = new CollatingIterator<>(
            Arrays.asList(iter1, iter2, iter3)
        );
        while (merged2.hasNext()) {
            System.out.print(merged2.next() + " ");
        }
        System.out.println();

        // Case 3: Unequal lengths
        System.out.println("\nUnequal lengths:");
        Iterator<Integer> short1 = Arrays.asList(1).iterator();
        Iterator<Integer> long1 = Arrays.asList(2, 4, 6, 8, 10).iterator();
        Iterator<Integer> medium = Arrays.asList(3, 5, 7).iterator();

        CollatingIterator<Integer> merged3 = new CollatingIterator<>(
            Arrays.asList(short1, long1, medium)
        );
        while (merged3.hasNext()) {
            System.out.print(merged3.next() + " ");
        }
        System.out.println();

        // Case 4: Large k (demonstrates scalability)
        System.out.println("\nLarge k (10 iterators):");
        List<Iterator<Integer>> manyIterators = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            // Iterator with values i, i+10, i+20, ...
            final int start = i;
            List<Integer> values = new ArrayList<>();
            for (int j = start; j < 100; j += 10) {
                values.add(j);
            }
            manyIterators.add(values.iterator());
        }

        CollatingIterator<Integer> merged4 = new CollatingIterator<>(manyIterators);
        int count = 0;
        while (merged4.hasNext()) {
            Integer val = merged4.next();
            if (count < 20 || count >= 80) {  // Print first 20 and last 20
                System.out.print(val + " ");
            } else if (count == 20) {
                System.out.print("... ");
            }
            count++;
        }
        System.out.println("\nTotal elements: " + count);
    }
}
