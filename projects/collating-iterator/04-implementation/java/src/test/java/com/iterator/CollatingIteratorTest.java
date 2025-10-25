package com.iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CollatingIteratorTest {

    @Test
    @DisplayName("Empty collection of iterators")
    void testEmptyCollection() {
        CollatingIterator<Integer> iter = new CollatingIterator<>(Collections.emptyList());
        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    @DisplayName("Single iterator")
    void testSingleIterator() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        CollatingIterator<Integer> iter = new CollatingIterator<>(list.iterator());

        List<Integer> result = collectAll(iter);
        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    @DisplayName("Two sorted iterators")
    void testTwoIterators() {
        Iterator<Integer> iter1 = Arrays.asList(1, 3, 5).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 4, 6).iterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2);
        List<Integer> result = collectAll(collating);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), result);
    }

    @Test
    @DisplayName("Multiple sorted iterators (k=5)")
    void testMultipleIterators() {
        Iterator<Integer> iter1 = Arrays.asList(1, 6, 11).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 7, 12).iterator();
        Iterator<Integer> iter3 = Arrays.asList(3, 8, 13).iterator();
        Iterator<Integer> iter4 = Arrays.asList(4, 9, 14).iterator();
        Iterator<Integer> iter5 = Arrays.asList(5, 10, 15).iterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2, iter3, iter4, iter5);
        List<Integer> result = collectAll(collating);

        assertEquals(IntStream.rangeClosed(1, 15).boxed().collect(Collectors.toList()), result);
    }

    @Test
    @DisplayName("Iterators with duplicates")
    void testDuplicates() {
        Iterator<Integer> iter1 = Arrays.asList(1, 3, 5, 5).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 3, 5, 6).iterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2);
        List<Integer> result = collectAll(collating);

        assertEquals(Arrays.asList(1, 2, 3, 3, 5, 5, 5, 6), result);
    }

    @Test
    @DisplayName("Some empty iterators")
    void testSomeEmptyIterators() {
        Iterator<Integer> iter1 = Arrays.asList(1, 3, 5).iterator();
        Iterator<Integer> iter2 = Collections.<Integer>emptyIterator();
        Iterator<Integer> iter3 = Arrays.asList(2, 4, 6).iterator();
        Iterator<Integer> iter4 = Collections.<Integer>emptyIterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2, iter3, iter4);
        List<Integer> result = collectAll(collating);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6), result);
    }

    @Test
    @DisplayName("All empty iterators")
    void testAllEmptyIterators() {
        Iterator<Integer> iter1 = Collections.emptyIterator();
        Iterator<Integer> iter2 = Collections.emptyIterator();
        Iterator<Integer> iter3 = Collections.emptyIterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2, iter3);
        assertFalse(collating.hasNext());
    }

    @Test
    @DisplayName("Single element iterators")
    void testSingleElementIterators() {
        Iterator<Integer> iter1 = Collections.singletonList(3).iterator();
        Iterator<Integer> iter2 = Collections.singletonList(1).iterator();
        Iterator<Integer> iter3 = Collections.singletonList(2).iterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2, iter3);
        List<Integer> result = collectAll(collating);

        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    @DisplayName("Large k (100 iterators)")
    void testLargeK() {
        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final int start = i;
            List<Integer> list = IntStream.range(0, 10)
                    .map(j -> start + j * 100)
                    .boxed()
                    .collect(Collectors.toList());
            iterators.add(list.iterator());
        }

        CollatingIterator<Integer> collating = new CollatingIterator<>(iterators);
        List<Integer> result = collectAll(collating);

        assertEquals(1000, result.size());
        assertTrue(isSorted(result));
    }

    @Test
    @DisplayName("Strings (non-integer type)")
    void testStrings() {
        Iterator<String> iter1 = Arrays.asList("apple", "cherry", "grape").iterator();
        Iterator<String> iter2 = Arrays.asList("banana", "date", "fig").iterator();

        CollatingIterator<String> collating = CollatingIterator.of(iter1, iter2);
        List<String> result = collectAll(collating);

        assertEquals(Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape"), result);
    }

    @Test
    @DisplayName("Null collection throws NPE")
    void testNullCollection() {
        assertThrows(NullPointerException.class, () ->
                new CollatingIterator<Integer>(null));
    }

    @Test
    @DisplayName("Collection containing null iterator throws NPE")
    void testNullIterator() {
        List<Iterator<Integer>> iterators = Arrays.asList(
                Arrays.asList(1, 2).iterator(),
                null,
                Arrays.asList(3, 4).iterator()
        );

        assertThrows(NullPointerException.class, () ->
                new CollatingIterator<>(iterators));
    }

    @Test
    @DisplayName("Remove throws UnsupportedOperationException")
    void testRemoveNotSupported() {
        CollatingIterator<Integer> iter = CollatingIterator.of(
                Arrays.asList(1, 2).iterator()
        );

        iter.next();
        assertThrows(UnsupportedOperationException.class, iter::remove);
    }

    @Test
    @DisplayName("Multiple hasNext calls are idempotent")
    void testHasNextIdempotent() {
        CollatingIterator<Integer> iter = CollatingIterator.of(
                Arrays.asList(1, 2, 3).iterator()
        );

        assertTrue(iter.hasNext());
        assertTrue(iter.hasNext());
        assertTrue(iter.hasNext());

        assertEquals(1, iter.next());

        assertTrue(iter.hasNext());
        assertTrue(iter.hasNext());
    }

    @Test
    @DisplayName("Uneven length iterators")
    void testUnevenLength() {
        Iterator<Integer> iter1 = Arrays.asList(1, 4, 7, 10, 13, 16, 19).iterator();
        Iterator<Integer> iter2 = Arrays.asList(2, 5).iterator();
        Iterator<Integer> iter3 = Arrays.asList(3, 6, 9, 12, 15, 18).iterator();

        CollatingIterator<Integer> collating = CollatingIterator.of(iter1, iter2, iter3);
        List<Integer> result = collectAll(collating);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 10, 12, 13, 15, 16, 18, 19), result);
    }

    private <T> List<T> collectAll(Iterator<T> iterator) {
        List<T> result = new ArrayList<>();
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    private <T extends Comparable<T>> boolean isSorted(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }
}
