# Benchmark Design

## Objectives

Validate theoretical complexity predictions through empirical measurement:
1. Confirm O(N log k) time complexity
2. Measure constant factors across implementations
3. Compare Java, C++, and Rust performance
4. Identify crossover points (k values where behavior changes)

## Benchmark Parameters

### Independent Variables

| Parameter | Values | Purpose |
|-----------|--------|---------|
| k (iterators) | 2, 5, 10, 20, 50, 100, 500, 1000 | Stress heap operations |
| N (total elements) | 10³, 10⁴, 10⁵, 10⁶ | Test scalability |
| Distribution | Uniform, Skewed, Clustered | Real-world patterns |
| Element type | Integer, String | Type overhead |

### Dependent Variables

| Metric | Unit | Formula |
|--------|------|---------|
| Throughput | elements/second | N / elapsed_time |
| Per-element time | nanoseconds | elapsed_time / N |
| Comparisons | count | Instrumented heap |
| Memory usage | bytes | Heap size × entry size |

## Benchmark Scenarios

### Scenario 1: Varying k (Fixed N = 1M)

**Goal**: Confirm O(log k) per-element cost

| k | Expected Comparisons/elem | Expected Time/elem (ns) |
|---|---------------------------|-------------------------|
| 2 | 3 | 15 |
| 10 | 10 | 35 |
| 100 | 20 | 60 |
| 1000 | 30 | 85 |

**Analysis**: Plot log(k) vs time/elem, expect linear relationship.

### Scenario 2: Varying N (Fixed k = 100)

**Goal**: Confirm O(N) total cost

| N | Expected Time (ms) | Expected Throughput (M elem/s) |
|---|--------------------|---------------------------------|
| 10³ | 0.06 | 16.7 |
| 10⁴ | 0.6 | 16.7 |
| 10⁵ | 6 | 16.7 |
| 10⁶ | 60 | 16.7 |

**Analysis**: Plot N vs total_time, expect linear relationship with constant slope.

### Scenario 3: Crossover Point (Linear Scan vs Heap)

**Goal**: Find k where heap becomes faster than linear scan

| k | Heap Time/elem (ns) | Linear Scan Time/elem (ns) | Winner |
|---|---------------------|-----------------------------|--------|
| 2 | 15 | 10 | Linear |
| 4 | 20 | 20 | Tie |
| 8 | 25 | 40 | Heap |
| 16 | 30 | 80 | Heap |

**Analysis**: Identify crossover k value (expected ~4-8).

### Scenario 4: Language Comparison (k = 100, N = 1M)

**Goal**: Compare implementation efficiency

| Language | Expected Time/elem (ns) | Expected Throughput (M elem/s) |
|----------|-------------------------|---------------------------------|
| Rust | 50 | 20.0 |
| C++ | 60 | 16.7 |
| Java | 80 | 12.5 |

**Analysis**: Rust expected fastest (zero-cost abstractions), Java slowest (virtual dispatch, GC).

## Benchmark Framework

### Java (JMH)

```java
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(1)
public class CollatingIteratorBenchmark {
    @Param({"2", "10", "100", "1000"})
    int k;

    @Param({"1000", "10000", "100000", "1000000"})
    int n;

    @Benchmark
    public int measureCollating(Blackhole bh) {
        // Setup k iterators with n/k elements each
        // Measure throughput
    }
}
```

**Run**: `./gradlew jmh`

### C++ (Google Benchmark)

```cpp
static void BM_CollatingIterator(benchmark::State& state) {
    int k = state.range(0);
    int n = state.range(1);

    for (auto _ : state) {
        // Setup and measure
        state.PauseTiming();
        // ... setup ...
        state.ResumeTiming();
        // ... measure ...
    }

    state.SetItemsProcessed(state.iterations() * n);
}

BENCHMARK(BM_CollatingIterator)
    ->Args({2, 1000000})
    ->Args({10, 1000000})
    ->Args({100, 1000000})
    ->Args({1000, 1000000});
```

**Run**: `./collating_iterator_benchmark`

### Rust (Criterion)

```rust
fn benchmark_collating(c: &mut Criterion) {
    let mut group = c.benchmark_group("collating_iterator");

    for k in [2, 10, 100, 1000] {
        for n in [1_000, 10_000, 100_000, 1_000_000] {
            group.bench_with_input(
                BenchmarkId::from_parameter(format!("k{}_n{}", k, n)),
                &(k, n),
                |b, &(k, n)| {
                    b.iter(|| {
                        // Setup and measure
                    });
                },
            );
        }
    }
}
```

**Run**: `cargo bench`

## Data Collection

### Output Format

JSON results with:
- Benchmark name
- Parameters (k, N)
- Mean time
- Standard deviation
- Iterations
- Samples

### Storage

- Raw data: `06-benchmarks/results/raw/`
- Processed data: `06-benchmarks/results/processed/`
- Plots: `06-benchmarks/results/plots/`

## Analysis Plan

### 1. Complexity Validation

**Hypothesis**: Time = c₁ × N × log₂(k) + c₂

**Method**: Linear regression on log-transformed data
- Independent: log₂(k), N
- Dependent: total_time
- Expected: R² > 0.95

### 2. Constant Factor Extraction

From regression:
- c₁ = coefficient of N×log₂(k) term
- Interpretation: nanoseconds per comparison
- Expected: c₁ ∈ [20, 100] ns (depends on architecture)

### 3. Scalability Assessment

Plot:
- X-axis: k (log scale)
- Y-axis: Time per element (linear scale)
- Lines: Different N values

Expected: Lines roughly parallel (confirms O(log k)).

### 4. Language Comparison

Normalize by Rust performance:
- Rust: 1.0× (baseline)
- C++: 1.2× (expected)
- Java: 1.5-2.0× (expected, due to JIT/GC)

## Hardware Environment

**Document**:
- CPU: Model, cores, frequency
- RAM: Size, speed
- Cache: L1/L2/L3 sizes
- OS: Version, kernel
- Compiler/VM: GCC/Clang version, JVM version, rustc version

**Example**:
```
CPU: Intel Core i7-9700K @ 3.6GHz (8 cores)
RAM: 32GB DDR4-3200
Cache: 32KB L1d, 256KB L2, 12MB L3
OS: Linux 5.15.0, Ubuntu 22.04
Compilers: GCC 11.3, Clang 14.0, rustc 1.70, OpenJDK 17
```

## Success Criteria

1. **Complexity Confirmed**: R² > 0.95 for O(N log k) model
2. **Predictions Within ±20%**: Measured times match theoretical estimates
3. **Crossover Identified**: Linear scan faster for k < 8, heap faster for k ≥ 8
4. **Language Ranking**: Rust ≥ C++ > Java (expected order)
5. **Scalability**: Performance degradation < 10% for N = 10³ to 10⁶

## Visualization

### Plot 1: Complexity Validation
- Line plot: k (x-axis, log scale) vs time/elem (y-axis)
- Multiple lines for different N
- Overlay: Theoretical O(log k) line

### Plot 2: Scalability
- Line plot: N (x-axis) vs total time (y-axis)
- Multiple lines for different k
- Overlay: Linear fit

### Plot 3: Language Comparison
- Bar chart: Language (x-axis) vs throughput (y-axis)
- Error bars: Standard deviation
- Grouped by k value

### Plot 4: Crossover Point
- Line plot: k (x-axis) vs time/elem (y-axis)
- Two lines: Heap vs Linear Scan
- Highlight intersection point

## Limitations

1. **Synthetic Data**: Real-world data may have different patterns
2. **Warm Cache**: Benchmarks measure hot-path performance
3. **Single-Threaded**: No concurrency testing
4. **JIT Effects** (Java): JMH mitigates but warmup still critical

## Future Work

- **Parallel Merge**: Compare with parallel tournament tree
- **External Memory**: Benchmark with disk-based iterators
- **Adaptive Strategies**: Auto-switch between linear/heap based on k
- **SIMD Optimization**: Vectorized minimum for k ≤ 8
