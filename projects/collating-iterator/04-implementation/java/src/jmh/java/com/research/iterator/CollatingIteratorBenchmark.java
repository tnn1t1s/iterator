package com.research.iterator;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * JMH Benchmarks for CollatingIterator variants.
 *
 * Systematically explores input space across k, N, distribution, and pattern
 * to validate theoretical predictions from Stage 3.
 *
 * Key predictions to validate:
 * 1. Linear scan competitive for k ≤ 8
 * 2. Heap/loser tree 5-10× faster for k=50
 * 3. Loser tree ~2× faster than heap for k ≥ 100
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class CollatingIteratorBenchmark {

    // ========== Parameters ==========

    @Param({"2", "3", "5", "8", "10", "50", "100"})
    int k;  // Number of iterators

    @Param({"10000", "100000"})
    int n;  // Total elements

    @Param({"uniform", "skewed"})
    String distribution;

    @Param({"random", "sequential"})
    String pattern;

    // ========== State ==========

    private List<List<Integer>> testData;
    private TestDataGenerator generator;

    @Setup(Level.Trial)
    public void generateTestData() {
        generator = new TestDataGenerator();
        testData = generator.generate(k, n, distribution, pattern);
    }

    // ========== Benchmarks ==========

    @Benchmark
    public int linearScan(Blackhole bh) {
        List<Iterator<Integer>> iterators = generator.toIterators(testData);
        LinearScanIterator<Integer> merged = new LinearScanIterator<>(iterators);

        int count = 0;
        while (merged.hasNext()) {
            bh.consume(merged.next());
            count++;
        }
        return count;
    }

    @Benchmark
    public int heapBased(Blackhole bh) {
        List<Iterator<Integer>> iterators = generator.toIterators(testData);
        HeapBasedIterator<Integer> merged = new HeapBasedIterator<>(iterators);

        int count = 0;
        while (merged.hasNext()) {
            bh.consume(merged.next());
            count++;
        }
        return count;
    }

    @Benchmark
    public int loserTree(Blackhole bh) {
        List<Iterator<Integer>> iterators = generator.toIterators(testData);
        LoserTreeIterator<Integer> merged = new LoserTreeIterator<>(iterators);

        int count = 0;
        while (merged.hasNext()) {
            bh.consume(merged.next());
            count++;
        }
        return count;
    }
}
