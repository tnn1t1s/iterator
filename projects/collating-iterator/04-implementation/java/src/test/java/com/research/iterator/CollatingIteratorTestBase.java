package com.research.iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Base test class for all CollatingIterator variants.
 *
 * Subclasses implement createIterator() to provide specific algorithm.
 * All tests are shared across LinearScan, HeapBased, and LoserTree implementations.
 */
abstract class CollatingIteratorTestBase {

    /**
     * Factory method - subclasses provide specific iterator implementation.
     */
    protected abstract <T extends Comparable<? super T>> Iterator<T> createIterator(
        List<? extends Iterator<T>> iterators
    );

    // ========== Contract Tests ==========

    @Test
    void testHasNextConsistency() {
        Iterator<Integer> iter1 = Arrays.asList(1, 2, 3).iterator();
        Iterator<Integer> iter2 = Arrays.asList(4, 5, 6).iterator();

        Iterator<Integer> merged = createIterator(Arrays.asList(iter1, iter2));

        // Multiple hasNext() calls should be consistent
        assertTrue(merged.hasNext());
        assertTrue(merged.hasNext());
        assertTrue(merged.hasNext());

        assertEquals(1, merged.next());
        assertTrue(merged.hasNext());
    }

    @Test
    void testNextWithoutHasNext() {
        Iterator<Integer> iter1 = Arrays.asList(1, 2).iterator();
        Iterator<Integer> merged = createIterator(Collections.singletonList(iter1));

        // Should work without calling hasNext()
        assertEquals(1, merged.next());
        assertEquals(2, merged.next());
    }

    @Test
    void testNextOnExhaustedIteratorThrows() {
        Iterator<Integer> iter1 = Arrays.asList(1).iterator();
        Iterator<Integer> merged = createIterator(Collections.singletonList(iter1));

        assertEquals(1, merged.next());
        assertFalse(merged.hasNext());

        assertThrows(NoSuchElementException.class, merged::next);
    }

    @Test
    void testRemoveNotSupported() {
        Iterator<Integer> iter1 = Arrays.asList(1, 2, 3).iterator();
        Iterator<Integer> merged = createIterator(Collections.singletonList(iter1));

        assertThrows(UnsupportedOperationException.class, merged::remove);
    }

    // ========== Correctness Tests ==========

    @ParameterizedTest
    @MethodSource("provideBasicMergeCases")
    void testBasicMerge(List<List<Integer>> inputs, List<Integer> expected) {
        List<Iterator<Integer>> iterators = inputs.stream()
            .map(List::iterator)
            .collect(Collectors.toList());

        Iterator<Integer> merged = createIterator(iterators);

        List<Integer> result = new ArrayList<>();
        while (merged.hasNext()) {
            result.add(merged.next());
        }

        assertEquals(expected, result);
    }

    static Stream<Arguments> provideBasicMergeCases() {
        return Stream.of(
            // Two sequences
            Arguments.of(
                Arrays.asList(
                    Arrays.asList(1, 3, 5),
                    Arrays.asList(2, 4, 6)
                ),
                Arrays.asList(1, 2, 3, 4, 5, 6)
            ),

            // Three sequences
            Arguments.of(
                Arrays.asList(
                    Arrays.asList(1, 4, 7),
                    Arrays.asList(2, 5, 8),
                    Arrays.asList(3, 6, 9)
                ),
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
            ),

            // Non-overlapping ranges
            Arguments.of(
                Arrays.asList(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(4, 5, 6),
                    Arrays.asList(7, 8, 9)
                ),
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)
            ),

            // Overlapping values
            Arguments.of(
                Arrays.asList(
                    Arrays.asList(1, 3, 5, 7),
                    Arrays.asList(2, 3, 4, 5)
                ),
                Arrays.asList(1, 2, 3, 3, 4, 5, 5, 7)
            )
        );
    }

    // ========== Edge Cases ==========

    @Test
    void testEmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            createIterator(Collections.emptyList());
        });
    }

    @Test
    void testNullInput() {
        assertThrows(NullPointerException.class, () -> {
            createIterator(null);
        });
    }

    @Test
    void testNullIteratorInList() {
        List<Iterator<Integer>> iterators = new ArrayList<>();
        iterators.add(Arrays.asList(1, 2).iterator());
        iterators.add(null);

        assertThrows(IllegalArgumentException.class, () -> {
            createIterator(iterators);
        });
    }

    @Test
    void testSingleIterator() {
        Iterator<Integer> iter = Arrays.asList(1, 2, 3, 4, 5).iterator();
        Iterator<Integer> merged = createIterator(Collections.singletonList(iter));

        List<Integer> result = collectAll(merged);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
    }

    @Test
    void testAllIteratorsEmpty() {
        Iterator<Integer> iter1 = Collections.emptyIterator();
        Iterator<Integer> iter2 = Collections.emptyIterator();
        Iterator<Integer> iter3 = Collections.emptyIterator();

        Iterator<Integer> merged = createIterator(Arrays.asList(iter1, iter2, iter3));

        assertFalse(merged.hasNext());
        assertThrows(NoSuchElementException.class, merged::next);
    }

    @Test
    void testSomeIteratorsEmpty() {
        Iterator<Integer> iter1 = Arrays.asList(1, 3, 5).iterator();
        Iterator<Integer> iter2 = Collections.emptyIterator();
        Iterator<Integer> iter3 = Arrays.asList(2, 4, 6).iterator();

        Iterator<Integer> merged = createIterator(Arrays.asList(iter1, iter2, iter3));

        List<Integer> result = collectAll(merged);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), result);
    }

    @Test
    void testUnequalLengths() {
        Iterator<Integer> iter1 = Arrays.asList(1).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 4, 6, 8, 10).iterator();
        Iterator<Integer> iter3 = Arrays.asList(3, 5, 7).iterator();

        Iterator<Integer> merged = createIterator(Arrays.asList(iter1, iter2, iter3));

        List<Integer> result = collectAll(merged);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 10), result);
    }

    @Test
    void testSingleElementPerIterator() {
        Iterator<Integer> iter1 = Arrays.asList(3).iterator();
        Iterator<Integer> iter2 = Arrays.asList(1).iterator();
        Iterator<Integer> iter3 = Arrays.asList(2).iterator();

        Iterator<Integer> merged = createIterator(Arrays.asList(iter1, iter2, iter3));

        List<Integer> result = collectAll(merged);
        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    void testDuplicateValues() {
        Iterator<Integer> iter1 = Arrays.asList(1, 2, 2, 3).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 2, 4).iterator();
        Iterator<Integer> iter3 = Arrays.asList(1, 3, 3).iterator();

        Iterator<Integer> merged = createIterator(Arrays.asList(iter1, iter2, iter3));

        List<Integer> result = collectAll(merged);
        assertEquals(Arrays.asList(1, 1, 2, 2, 2, 2, 3, 3, 3, 4), result);
    }

    @Test
    void testLargeK() {
        // 100 iterators with 10 elements each
        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = i; j < 1000; j += 100) {
                values.add(j);
            }
            iterators.add(values.iterator());
        }

        Iterator<Integer> merged = createIterator(iterators);
        List<Integer> result = collectAll(merged);

        // Should have 1000 elements
        assertEquals(1000, result.size());

        // Should be sorted
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, result.get(i));
        }
    }

    @Test
    void testStringType() {
        Iterator<String> iter1 = Arrays.asList("apple", "cherry", "grape").iterator();
        Iterator<String> iter2 = Arrays.asList("banana", "date", "fig").iterator();

        Iterator<String> merged = createIterator(Arrays.asList(iter1, iter2));

        List<String> result = collectAll(merged);
        assertEquals(Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape"), result);
    }

    // ========== Property Tests ==========

    @Test
    void testOutputSizeMatchesInputSize() {
        List<List<Integer>> inputs = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6, 7, 8, 9)
        );

        int expectedSize = inputs.stream().mapToInt(List::size).sum();

        List<Iterator<Integer>> iterators = inputs.stream()
            .map(List::iterator)
            .collect(Collectors.toList());

        Iterator<Integer> merged = createIterator(iterators);
        List<Integer> result = collectAll(merged);

        assertEquals(expectedSize, result.size());
    }

    @Test
    void testOutputIsSorted() {
        Random rand = new Random(42);
        List<Iterator<Integer>> iterators = new ArrayList<>();

        // 10 iterators with random sorted sequences
        for (int i = 0; i < 10; i++) {
            List<Integer> values = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                values.add(rand.nextInt(1000));
            }
            Collections.sort(values);
            iterators.add(values.iterator());
        }

        Iterator<Integer> merged = createIterator(iterators);
        List<Integer> result = collectAll(merged);

        // Verify sorted
        for (int i = 1; i < result.size(); i++) {
            assertTrue(result.get(i - 1) <= result.get(i),
                "Result not sorted at index " + i + ": " + result.get(i - 1) + " > " + result.get(i));
        }
    }

    @Test
    void testAllInputElementsPresent() {
        List<List<Integer>> inputs = Arrays.asList(
            Arrays.asList(1, 3, 5),
            Arrays.asList(2, 4, 6),
            Arrays.asList(7, 8, 9)
        );

        // Flatten inputs
        List<Integer> allInputs = inputs.stream()
            .flatMap(List::stream)
            .sorted()
            .collect(Collectors.toList());

        List<Iterator<Integer>> iterators = inputs.stream()
            .map(List::iterator)
            .collect(Collectors.toList());

        Iterator<Integer> merged = createIterator(iterators);
        List<Integer> result = collectAll(merged);

        assertEquals(allInputs, result);
    }

    // ========== Helper Methods ==========

    private <T> List<T> collectAll(Iterator<T> iterator) {
        List<T> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }
}
