package com.research.iterator;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Focused 5-minute benchmark validating key predictions.
 *
 * Tests critical points only:
 * - k=3: Linear scan should win (cache locality)
 * - k=10: Crossover point (heap/tree start to win)
 * - k=50: Large k (loser tree advantage emerges)
 *
 * Time budget: ~5 minutes total
 * - 3 k values × 3 algorithms = 9 benchmarks
 * - ~30 seconds each = ~4.5 minutes
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class FocusedBenchmark {

    @Param({"3", "10", "50"})
    int k;

    @Param({"10000"})
    int n;

    private List<List<Integer>> testData;
    private TestDataGenerator generator;

    @Setup(Level.Trial)
    public void generateTestData() {
        generator = new TestDataGenerator();
        testData = generator.generate(k, n, "uniform", "random");
    }

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
