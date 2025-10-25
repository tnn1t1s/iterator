---
name: benchmark_design
description: Generates controlled benchmark setups with workload patterns, throughput/latency metrics, and reproducible seeds. Use for rigorous performance measurement design.
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Benchmark Design

## Purpose

Design rigorous, reproducible benchmarks that isolate performance characteristics and minimize measurement noise.

## Benchmark Principles (1999-2002 Era)

1. **Measure what matters**: Throughput (ops/sec) and latency (ns/op)
2. **Warm up the JIT**: Java needs warmup, C++/Rust don't
3. **Multiple trials**: Run 10-100 times, report median + stddev
4. **Fixed seeds**: Reproducible random data
5. **Isolate variables**: Change one thing at a time

## Benchmark Structure

### Setup
- Generate test data with fixed seed
- Warm up (Java: 10K iterations, C++/Rust: optional)
- Clear caches between trials (if OS allows)

### Measurement
- Use high-resolution timer (nanoseconds)
- Measure large batch (≥1000 ops) to amortize overhead
- Report throughput: ops/sec = batch_size / elapsed_seconds
- Report latency: ns/op = elapsed_ns / batch_size

### Workloads
- **Small**: k=10 iterators, 1K elements each
- **Medium**: k=100 iterators, 100K elements each
- **Large**: k=1000 iterators, 10M elements total

## Framework Usage

### Java (JMH - Java Microbenchmark Harness)
```java
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
public class MergeBenchmark {
    @Param({"10", "100", "1000"})
    private int k;

    @Benchmark
    public long testMerge(Blackhole bh) {
        // Benchmark code
    }
}
```

### C++ (Google Benchmark)
```cpp
static void BM_Merge(benchmark::State& state) {
    int k = state.range(0);
    auto iterators = generate_test_data(k, 10000);

    for (auto _ : state) {
        merge_iterator<int> merger(iterators);
        long count = 0;
        while (merger.has_next()) {
            benchmark::DoNotOptimize(merger.next());
            count++;
        }
        benchmark::DoNotOptimize(count);
    }
}
BENCHMARK(BM_Merge)->Arg(10)->Arg(100)->Arg(1000);
```

### Rust (Criterion)
```rust
fn merge_benchmark(c: &mut Criterion) {
    let mut group = c.benchmark_group("merge");

    for k in [10, 100, 1000].iter() {
        group.bench_with_input(
            BenchmarkId::from_parameter(k),
            k,
            |b, &k| {
                let iterators = generate_test_data(k, 10000);
                b.iter(|| {
                    let merger = MergeIterator::new(iterators.clone());
                    merger.count()
                });
            },
        );
    }
}
```

## Workload Patterns

### Pattern 1: Uniform Distribution
- Each iterator: 10K elements uniformly distributed [0, 100K)
- Tests: Typical case, good branch prediction

### Pattern 2: Skewed Distribution
- Iterator 0: 90% of elements, rest: 10% split
- Tests: Heap imbalance, worst-case behavior

### Pattern 3: Adversarial
- Elements designed to maximize comparisons/cache misses
- Tests: Worst-case complexity

## Metrics to Collect

1. **Throughput**: Elements processed per second
2. **Latency**: Nanoseconds per element
3. **Memory**: Peak allocation (if measurable)
4. **Cache**: Miss rate (if perf counters available)

## Reproducibility Checklist

- [ ] Fixed random seed documented
- [ ] Compiler flags documented (opt level, flags)
- [ ] JVM/runtime version documented
- [ ] CPU model and clock speed documented
- [ ] OS and kernel version documented
- [ ] Number of trials ≥ 10
- [ ] Warmup iterations documented

## Cross-Skill Integration

Requires: multi_language_codegen (code to benchmark)
Feeds into: performance_interpretation, reporting_visualization
