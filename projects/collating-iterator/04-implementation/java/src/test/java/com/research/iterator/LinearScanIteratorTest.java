package com.research.iterator;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LinearScanIterator (O(Nk) naive baseline).
 *
 * Inherits all shared tests from CollatingIteratorTestBase.
 * Adds variant-specific tests if needed.
 */
class LinearScanIteratorTest extends CollatingIteratorTestBase {

    @Override
    protected <T extends Comparable<? super T>> Iterator<T> createIterator(
        List<? extends Iterator<T>> iterators
    ) {
        return new LinearScanIterator<>(iterators);
    }

    // Variant-specific tests (if any)

    @Test
    void testSmallKPerformance() {
        // LinearScan should be competitive for small k
        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = i; j < 1000; j += 5) {
                values.add(j);
            }
            iterators.add(values.iterator());
        }

        LinearScanIterator<Integer> merged = new LinearScanIterator<>(iterators);

        // Just verify it completes reasonably (not a benchmark)
        int count = 0;
        while (merged.hasNext()) {
            merged.next();
            count++;
        }

        assertEquals(1000, count);
    }
}
