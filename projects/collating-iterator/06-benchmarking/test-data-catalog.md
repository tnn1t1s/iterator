# Benchmark Test Data Catalog

## Purpose

Systematic exploration of k-way merge input space to expose performance characteristics across all algorithm variants (LinearScan, HeapBased, LoserTree).

**Key insight**: Top candidates design data-driven benchmarks that systematically vary independent dimensions rather than ad-hoc "typical" cases.

## Dimension Analysis

For k-way merge CollatingIterator, the critical dimensions are:

### 1. k (number of iterators)
**Drives asymptotic complexity**: O(Nk) vs O(N log k)

- **k=1**: Degenerate case (passthrough)
- **k=2**: Minimal merge
- **k=3-8**: Small k where linear scan competitive (cache locality dominates)
- **k=10-50**: Medium k where heap/tree advantages emerge
- **k=100-1000**: Large k where comparison count dominates

### 2. N (total elements)
**Drives total work**

- **N=1K**: Tiny (overhead dominates)
- **N=10K**: Small (typical in-memory)
- **N=100K**: Medium (realistic)
- **N=1M**: Large (stress test)

### 3. Distribution (how elements spread across iterators)
**Affects heap/tree balance**

- **Uniform**: N/k elements per iterator (balanced)
- **Skewed**: First iterator has 80%, rest share 20% (unbalanced)
- **Power-law**: Few iterators have most elements
- **Single-dominant**: One iterator has almost all N elements

### 4. Value Pattern (how values are distributed)
**Affects comparison patterns and cache behavior**

- **Random**: Uniformly random values in [0, MAX)
- **Sequential**: Values 0, 1, 2, ..., N-1 interleaved across iterators
- **Clustered**: Values grouped in ranges
- **Alternating**: Designed to maximize comparisons (adversarial)

### 5. Iterator Exhaustion Pattern
**Tests end-game behavior**

- **Simultaneous**: All iterators finish at roughly same time
- **Sequential**: Iterators exhaust one-by-one
- **Early**: Most iterators finish early, one long tail
- **Random**: Unpredictable exhaustion order

## Test Case Matrix

### Baseline Cases (Small Scale)

| ID | k | N | Distribution | Pattern | Purpose |
|----|---|---|--------------|---------|---------|
| `baseline-k2` | 2 | 10K | Uniform | Random | Minimal merge baseline |
| `baseline-k3` | 3 | 10K | Uniform | Random | Classic 3-way merge |
| `baseline-k5` | 5 | 10K | Uniform | Random | Small k sweet spot |

**Prediction**: All algorithms competitive, linear scan may win on small k due to cache locality.

### Small k (Linear Scan Competitive)

| ID | k | N | Distribution | Pattern | Purpose |
|----|---|---|--------------|---------|---------|
| `small-k3-uniform` | 3 | 100K | Uniform | Random | Linear scan favorable |
| `small-k5-uniform` | 5 | 100K | Uniform | Random | Linear scan still good |
| `small-k8-uniform` | 8 | 100K | Uniform | Random | Crossover point? |
| `small-k3-sequential` | 3 | 100K | Uniform | Sequential | Cache-friendly for linear scan |

**Prediction**: Linear scan competitive or wins due to perfect cache locality, no tree overhead.

### Medium k (Heap vs Tree)

| ID | k | N | Distribution | Pattern | Purpose |
|----|---|---|--------------|---------|---------|
| `medium-k10-uniform` | 10 | 100K | Uniform | Random | Heap/tree start to win |
| `medium-k50-uniform` | 50 | 100K | Uniform | Random | Clear heap/tree advantage |
| `medium-k100-uniform` | 100 | 100K | Uniform | Random | Log k dominates |
| `medium-k50-skewed` | 50 | 100K | Skewed | Random | Tests unbalanced heaps |

**Prediction**: Heap and loser tree significantly faster than linear scan. Loser tree ~10-20% faster than heap (log k vs 2 log k comparisons).

### Large k (Loser Tree Advantage)

| ID | k | N | Distribution | Pattern | Purpose |
|----|---|---|--------------|---------|---------|
| `large-k100-uniform` | 100 | 1M | Uniform | Random | Loser tree pulls ahead |
| `large-k500-uniform` | 500 | 1M | Uniform | Random | Comparison count critical |
| `large-k1000-uniform` | 1000 | 1M | Uniform | Random | Maximum k tested |

**Prediction**: Loser tree clearly fastest (half the comparisons of heap). Linear scan impractically slow.

### Edge Cases

| ID | k | N | Distribution | Pattern | Purpose |
|----|---|---|--------------|---------|---------|
| `edge-k1-single` | 1 | 100K | N/A | Random | Degenerate passthrough |
| `edge-k10-empty` | 10 | 0 | All empty | N/A | Handle empty gracefully |
| `edge-k100-tiny` | 100 | 1K | Uniform | Random | Overhead dominates |
| `edge-k10-onesided` | 10 | 100K | Single-dominant | Random | 99% in one iterator |

**Prediction**: k=1 all equal (passthrough), empty all equal (trivial), overhead cases unpredictable, one-sided favors simpler algorithms.

### Adversarial Cases

| ID | k | N | Distribution | Pattern | Purpose |
|----|---|---|--------------|---------|---------|
| `adversarial-alternating` | 10 | 100K | Uniform | Alternating | Maximize comparisons |
| `adversarial-clustered` | 50 | 100K | Uniform | Clustered | Poor cache locality |
| `adversarial-sequential-reverse` | 10 | 100K | Uniform | Reverse sequential | Worst-case for some algorithms |

**Prediction**: Adversarial cases stress algorithms differently. Alternating maximizes comparison cost (favors loser tree). Clustered stresses cache (favors linear scan's locality).

### Realistic Cases

| ID | k | N | Distribution | Pattern | Description |
|----|---|---|--------------|---------|-------------|
| `realistic-log-merge` | 20 | 500K | Power-law | Random | Simulates merging log segments (few large, many small) |
| `realistic-db-index` | 50 | 1M | Uniform | Clustered | Simulates database index merge |
| `realistic-timeseries` | 100 | 1M | Uniform | Sequential | Simulates timeseries data merge |

**Prediction**: Realistic cases favor heap/loser tree. Real workloads typically have k ≥ 10.

## Total Test Cases

- **Baseline**: 3 cases
- **Small k**: 4 cases
- **Medium k**: 4 cases
- **Large k**: 3 cases
- **Edge cases**: 4 cases
- **Adversarial**: 3 cases
- **Realistic**: 3 cases

**Total**: 24 test cases

Plus JMH parameterization will cross some dimensions for total ~50-100 benchmark combinations.

## Data Generation Specifications

### Uniform Distribution
```
k iterators, N total elements
Each iterator: floor(N/k) or ceil(N/k) elements
Values: Random integers in [0, 1000000), sorted per iterator
Seed: Fixed (42) for reproducibility
```

### Skewed Distribution
```
k iterators, N total elements
Iterator 0: 80% of elements
Remaining k-1 iterators: 20% of elements divided equally
Values: Random integers in [0, 1000000), sorted per iterator
Seed: Fixed (42) for reproducibility
```

### Sequential Pattern
```
k iterators, N total elements
Iterator i gets values: i, i+k, i+2k, i+3k, ...
Example (k=3, N=12):
  iter[0] = [0, 3, 6, 9]
  iter[1] = [1, 4, 7, 10]
  iter[2] = [2, 5, 8, 11]
Already sorted, no randomness needed
```

### Alternating Pattern (Adversarial)
```
Same as sequential but designed to maximize comparisons
Forces algorithm to compare across all k iterators at each step
```

### Clustered Pattern
```
k iterators, N total elements
Divide value space [0, 1000000) into k ranges
Iterator i gets random values from range i only
Results in poor cache locality when iterators interleave
```

## Performance Predictions

Based on Stage 3 theoretical analysis:

| Algorithm | Small k (≤8) | Medium k (10-100) | Large k (≥100) |
|-----------|--------------|-------------------|----------------|
| LinearScan | **Best** (cache locality) | 5-10× slower | 50-100× slower |
| HeapBased | Good | **Best** or tied | Good |
| LoserTree | Good | **Best** or tied | **Best** (2× faster than heap) |

**Crossover points to identify**:
1. When does linear scan lose to heap? (Expected: k=8-10)
2. When does loser tree beat heap? (Expected: k=50-100)
3. Do cache effects ever make linear scan win for k>8? (Investigate)

## Benchmark Methodology

1. **Warmup**: 5 iterations, 1 second each (JVM JIT warmup)
2. **Measurement**: 10 iterations, 1 second each
3. **Fork**: 1 JVM fork (reduces variance)
4. **Mode**: Average time per operation (microseconds)
5. **Blackhole**: Consume results to prevent dead code elimination

## Success Criteria

Benchmarks validate Stage 3 predictions if:
1. ✓ Linear scan competitive for k ≤ 8
2. ✓ Heap/loser tree 5-10× faster than linear scan for k=50
3. ✓ Loser tree 1.5-2× faster than heap for k ≥ 100
4. ✓ Results within ±30% of theoretical predictions (accounting for constant factors)

## Next Steps

1. Implement `TestDataGenerator.java` with all patterns
2. Implement JMH benchmark harness with `@Param` for dimensions
3. Run benchmarks, collect results
4. Analyze with `performance_interpretation` skill
5. Visualize with `reporting_visualization` skill
