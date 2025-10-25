package com.research.iterator;

import java.util.*;

/**
 * HeapBasedIterator merges k sorted iterators using a binary min-heap (PriorityQueue).
 *
 * <p>Complexity: O(N log k) time, O(k) space
 * <p>Comparisons: 2 log k per next() (standard approach)
 *
 * <p>This is the standard textbook implementation using Java's PriorityQueue.
 * Advantages:
 * - Simple implementation (leverages stdlib)
 * - Array-based heap has excellent cache locality
 * - Well-understood and debuggable
 *
 * Disadvantages compared to loser tree:
 * - 2× comparisons (2 log k vs log k)
 * - Sift-down compares with both children at each level
 *
 * @param <T> element type, must be Comparable
 */
public class HeapBasedIterator<T extends Comparable<? super T>> implements Iterator<T> {

    /**
     * Entry in the priority queue, containing a value and its source iterator.
     */
    private static class Entry<T extends Comparable<? super T>> implements Comparable<Entry<T>> {
        final T value;
        final Iterator<T> source;

        Entry(T value, Iterator<T> source) {
            this.value = value;
            this.source = source;
        }

        @Override
        public int compareTo(Entry<T> other) {
            return this.value.compareTo(other.value);
        }
    }

    private final PriorityQueue<Entry<T>> heap;

    /**
     * Constructs a HeapBasedIterator from multiple sorted iterators.
     *
     * @param iterators list of sorted iterators (must not be null or contain nulls)
     * @throws IllegalArgumentException if iterators is null, empty, or contains nulls
     */
    public HeapBasedIterator(List<? extends Iterator<T>> iterators) {
        Objects.requireNonNull(iterators, "iterators must not be null");
        if (iterators.isEmpty()) {
            throw new IllegalArgumentException("iterators must not be empty");
        }
        if (iterators.contains(null)) {
            throw new IllegalArgumentException("iterators must not contain null");
        }

        this.heap = new PriorityQueue<>(iterators.size());

        // Initialize: add first element from each iterator
        for (Iterator<T> iterator : iterators) {
            if (iterator.hasNext()) {
                heap.offer(new Entry<>(iterator.next(), iterator));
            }
        }
    }

    @Override
    public boolean hasNext() {
        return !heap.isEmpty();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iterator exhausted");
        }

        // Extract minimum
        Entry<T> entry = heap.poll();
        T result = entry.value;

        // Refill from same source
        if (entry.source.hasNext()) {
            heap.offer(new Entry<>(entry.source.next(), entry.source));
        }

        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }
}
