package com.research.iterator;

import java.util.*;

/**
 * LinearScanIterator merges k sorted iterators using naive linear scan.
 *
 * <p>Complexity: O(Nk) time, O(k) space
 * <p>Comparisons: k per next() (naive baseline)
 *
 * <p>This is the naive baseline implementation for comparison. Despite poor
 * asymptotic complexity, it can be competitive for very small k (≤ 8) due to:
 * - Perfect cache locality (sequential array scan)
 * - No tree overhead
 * - Branch predictor friendly
 *
 * @param <T> element type, must be Comparable
 */
public class LinearScanIterator<T extends Comparable<? super T>> implements Iterator<T> {

    private final List<Iterator<T>> sources;
    private final List<T> currentValues;  // Cached current value from each iterator
    private final int k;

    /**
     * Constructs a LinearScanIterator from multiple sorted iterators.
     *
     * @param iterators list of sorted iterators (must not be null or contain nulls)
     * @throws IllegalArgumentException if iterators is null, empty, or contains nulls
     */
    public LinearScanIterator(List<? extends Iterator<T>> iterators) {
        Objects.requireNonNull(iterators, "iterators must not be null");
        if (iterators.isEmpty()) {
            throw new IllegalArgumentException("iterators must not be empty");
        }
        if (iterators.contains(null)) {
            throw new IllegalArgumentException("iterators must not contain null");
        }

        this.sources = new ArrayList<>(iterators);
        this.k = iterators.size();
        this.currentValues = new ArrayList<>(Collections.nCopies(k, null));

        // Initialize: load first element from each iterator
        for (int i = 0; i < k; i++) {
            if (sources.get(i).hasNext()) {
                currentValues.set(i, sources.get(i).next());
            }
        }
    }

    @Override
    public boolean hasNext() {
        // Has next if any iterator has a current value
        for (T value : currentValues) {
            if (value != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iterator exhausted");
        }

        // Linear scan to find minimum
        int minIndex = -1;
        T minValue = null;

        for (int i = 0; i < k; i++) {
            T value = currentValues.get(i);
            if (value != null) {
                if (minValue == null || value.compareTo(minValue) < 0) {
                    minValue = value;
                    minIndex = i;
                }
            }
        }

        // Refill from source
        if (sources.get(minIndex).hasNext()) {
            currentValues.set(minIndex, sources.get(minIndex).next());
        } else {
            currentValues.set(minIndex, null);  // Exhausted
        }

        return minValue;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }
}
