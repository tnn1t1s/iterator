package com.research.iterator;

import java.util.*;

/**
 * Generates test data for benchmarking CollatingIterator variants.
 *
 * Supports multiple distribution patterns and value patterns to systematically
 * explore the input space and expose performance characteristics.
 */
public class TestDataGenerator {

    private final Random random;

    /**
     * Creates generator with fixed seed for reproducibility.
     */
    public TestDataGenerator() {
        this(42);
    }

    /**
     * Creates generator with specified seed.
     */
    public TestDataGenerator(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Generates test data based on parameters.
     *
     * @param k number of iterators
     * @param n total number of elements
     * @param distribution how elements are distributed across iterators
     * @param pattern how values are generated
     * @return list of sorted lists (one per iterator)
     */
    public List<List<Integer>> generate(int k, int n, String distribution, String pattern) {
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }

        // Generate distribution (how many elements per iterator)
        int[] counts = generateDistribution(k, n, distribution);

        // Generate values based on pattern
        return generateValues(k, counts, pattern);
    }

    /**
     * Generates element counts for each iterator based on distribution type.
     */
    private int[] generateDistribution(int k, int n, String distribution) {
        int[] counts = new int[k];

        switch (distribution.toLowerCase()) {
            case "uniform":
                // Distribute evenly: N/k elements per iterator
                int base = n / k;
                int remainder = n % k;
                for (int i = 0; i < k; i++) {
                    counts[i] = base + (i < remainder ? 1 : 0);
                }
                break;

            case "skewed":
                // First iterator gets 80%, rest share 20%
                if (k == 1) {
                    counts[0] = n;
                } else {
                    counts[0] = (int)(n * 0.8);
                    int remaining = n - counts[0];
                    int baseRest = remaining / (k - 1);
                    int remainderRest = remaining % (k - 1);
                    for (int i = 1; i < k; i++) {
                        counts[i] = baseRest + (i - 1 < remainderRest ? 1 : 0);
                    }
                }
                break;

            case "power_law":
                // Power-law: a few iterators have most elements
                // Use Zipf-like distribution: iter i gets N / (i+1)
                double sum = 0;
                for (int i = 0; i < k; i++) {
                    sum += 1.0 / (i + 1);
                }
                int assigned = 0;
                for (int i = 0; i < k - 1; i++) {
                    counts[i] = (int)(n / ((i + 1) * sum));
                    assigned += counts[i];
                }
                counts[k - 1] = n - assigned;  // Remainder
                break;

            case "single_dominant":
                // One iterator has 99%, rest share 1%
                if (k == 1) {
                    counts[0] = n;
                } else {
                    counts[0] = (int)(n * 0.99);
                    int remaining = n - counts[0];
                    for (int i = 1; i < k; i++) {
                        counts[i] = (i == 1 ? remaining : 0);
                    }
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown distribution: " + distribution);
        }

        return counts;
    }

    /**
     * Generates values for each iterator based on pattern type.
     */
    private List<List<Integer>> generateValues(int k, int[] counts, String pattern) {
        List<List<Integer>> result = new ArrayList<>();

        switch (pattern.toLowerCase()) {
            case "random":
                // Random values in [0, 1000000), sorted per iterator
                for (int i = 0; i < k; i++) {
                    List<Integer> values = new ArrayList<>();
                    for (int j = 0; j < counts[i]; j++) {
                        values.add(random.nextInt(1000000));
                    }
                    Collections.sort(values);
                    result.add(values);
                }
                break;

            case "sequential":
                // Values interleaved: iter i gets i, i+k, i+2k, ...
                for (int i = 0; i < k; i++) {
                    List<Integer> values = new ArrayList<>();
                    for (int j = 0; j < counts[i]; j++) {
                        values.add(i + j * k);
                    }
                    // Already sorted
                    result.add(values);
                }
                break;

            case "alternating":
                // Same as sequential (designed to maximize comparisons)
                for (int i = 0; i < k; i++) {
                    List<Integer> values = new ArrayList<>();
                    for (int j = 0; j < counts[i]; j++) {
                        values.add(i + j * k);
                    }
                    result.add(values);
                }
                break;

            case "clustered":
                // Divide value space into k ranges, iterator i uses range i
                int rangeSize = 1000000 / k;
                for (int i = 0; i < k; i++) {
                    List<Integer> values = new ArrayList<>();
                    int rangeStart = i * rangeSize;
                    int rangeEnd = (i == k - 1) ? 1000000 : (i + 1) * rangeSize;
                    for (int j = 0; j < counts[i]; j++) {
                        values.add(rangeStart + random.nextInt(rangeEnd - rangeStart));
                    }
                    Collections.sort(values);
                    result.add(values);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown pattern: " + pattern);
        }

        return result;
    }

    /**
     * Creates iterators from generated lists (deep copy to allow multiple runs).
     */
    public List<Iterator<Integer>> toIterators(List<List<Integer>> data) {
        List<Iterator<Integer>> iterators = new ArrayList<>();
        for (List<Integer> list : data) {
            iterators.add(new ArrayList<>(list).iterator());
        }
        return iterators;
    }
}
