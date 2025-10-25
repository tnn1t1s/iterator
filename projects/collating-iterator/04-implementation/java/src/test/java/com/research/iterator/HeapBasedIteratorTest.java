package com.research.iterator;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for HeapBasedIterator (O(N log k) standard approach using PriorityQueue).
 *
 * Inherits all shared tests from CollatingIteratorTestBase.
 * Adds variant-specific tests if needed.
 */
class HeapBasedIteratorTest extends CollatingIteratorTestBase {

    @Override
    protected <T extends Comparable<? super T>> Iterator<T> createIterator(
        List<? extends Iterator<T>> iterators
    ) {
        return new HeapBasedIterator<>(iterators);
    }

    // Variant-specific tests (if any)

    @Test
    void testMediumKPerformance() {
        // HeapBased should be efficient for medium k
        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = i; j < 10000; j += 50) {
                values.add(j);
            }
            iterators.add(values.iterator());
        }

        HeapBasedIterator<Integer> merged = new HeapBasedIterator<>(iterators);

        // Just verify it completes reasonably (not a benchmark)
        int count = 0;
        while (merged.hasNext()) {
            merged.next();
            count++;
        }

        assertEquals(10000, count);
    }
}
