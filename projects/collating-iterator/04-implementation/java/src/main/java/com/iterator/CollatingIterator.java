package com.iterator;

import java.util.*;

/**
 * CollatingIterator merges k sorted iterators into a single sorted output stream.
 * Uses a binary min-heap to achieve O(N log k) time complexity.
 *
 * @param <T> element type, must be Comparable
 */
public class CollatingIterator<T extends Comparable<T>> implements Iterator<T> {

    private static class HeapEntry<T extends Comparable<T>> implements Comparable<HeapEntry<T>> {
        final T element;
        final Iterator<T> iterator;
        final int index;

        HeapEntry(T element, Iterator<T> iterator, int index) {
            this.element = element;
            this.iterator = iterator;
            this.index = index;
        }

        @Override
        public int compareTo(HeapEntry<T> other) {
            int cmp = this.element.compareTo(other.element);
            if (cmp != 0) return cmp;
            return Integer.compare(this.index, other.index);
        }
    }

    private final PriorityQueue<HeapEntry<T>> heap;

    /**
     * Constructs a CollatingIterator from a collection of sorted iterators.
     *
     * @param iterators collection of sorted iterators
     * @throws NullPointerException if iterators is null or contains null
     */
    public CollatingIterator(Collection<? extends Iterator<T>> iterators) {
        Objects.requireNonNull(iterators, "iterators collection cannot be null");

        this.heap = new PriorityQueue<>(Math.max(1, iterators.size()));

        int index = 0;
        for (Iterator<T> iter : iterators) {
            Objects.requireNonNull(iter, "iterator at index " + index + " is null");
            if (iter.hasNext()) {
                T element = iter.next();
                heap.offer(new HeapEntry<>(element, iter, index));
            }
            index++;
        }
    }

    /**
     * Constructs a CollatingIterator from a variable number of sorted iterators.
     *
     * @param iterators sorted iterators
     */
    @SafeVarargs
    public CollatingIterator(Iterator<T>... iterators) {
        this(Arrays.asList(iterators));
    }

    @Override
    public boolean hasNext() {
        return !heap.isEmpty();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements");
        }

        HeapEntry<T> min = heap.poll();
        T result = min.element;

        if (min.iterator.hasNext()) {
            T nextElement = min.iterator.next();
            heap.offer(new HeapEntry<>(nextElement, min.iterator, min.index));
        }

        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }

    /**
     * Static factory method for convenient construction.
     *
     * @param iterators sorted iterators
     * @param <T>       element type
     * @return CollatingIterator instance
     */
    @SafeVarargs
    public static <T extends Comparable<T>> CollatingIterator<T> of(Iterator<T>... iterators) {
        return new CollatingIterator<>(iterators);
    }

    /**
     * Static factory method from collection.
     *
     * @param iterators collection of sorted iterators
     * @param <T>       element type
     * @return CollatingIterator instance
     */
    public static <T extends Comparable<T>> CollatingIterator<T> from(
            Collection<? extends Iterator<T>> iterators) {
        return new CollatingIterator<>(iterators);
    }
}
