package com.research.iterator;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LoserTreeIterator (O(N log k) optimized with log k comparisons).
 *
 * Inherits all shared tests from CollatingIteratorTestBase.
 * Adds variant-specific tests if needed.
 */
class LoserTreeIteratorTest extends CollatingIteratorTestBase {

    @Override
    protected <T extends Comparable<? super T>> Iterator<T> createIterator(
        List<? extends Iterator<T>> iterators
    ) {
        return new LoserTreeIterator<>(iterators);
    }

    // Variant-specific tests (if any)

    @Test
    void testLargeKPerformance() {
        // LoserTree should be efficient for large k
        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = i; j < 100000; j += 1000) {
                values.add(j);
            }
            iterators.add(values.iterator());
        }

        LoserTreeIterator<Integer> merged = new LoserTreeIterator<>(iterators);

        // Just verify it completes reasonably (not a benchmark)
        int count = 0;
        while (merged.hasNext()) {
            merged.next();
            count++;
        }

        assertEquals(100000, count);
    }

    @Test
    void testTournamentTreeCorrectness() {
        // Verify loser tree maintains correct tournament structure
        // This is a spot check, not exhaustive
        Iterator<Integer> iter1 = Arrays.asList(1, 5, 9).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 6, 10).iterator();
        Iterator<Integer> iter3 = Arrays.asList(3, 7, 11).iterator();
        Iterator<Integer> iter4 = Arrays.asList(4, 8, 12).iterator();

        LoserTreeIterator<Integer> merged = new LoserTreeIterator<>(
            Arrays.asList(iter1, iter2, iter3, iter4)
        );

        // Extract in order
        List<Integer> result = new ArrayList<>();
        while (merged.hasNext()) {
            result.add(merged.next());
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), result);
    }
}
