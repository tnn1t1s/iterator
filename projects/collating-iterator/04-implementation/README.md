# Stage 4: Implementation

## Overview

Java implementation of k-way merge with **three algorithm variants** for empirical comparison:
1. **LinearScanIterator** - O(Nk) naive baseline
2. **HeapBasedIterator** - O(N log k) standard approach
3. **LoserTreeIterator** - O(N log k) optimized (selected in Stage 3)

## Multi-Variant Implementation Strategy

**Why implement multiple variants?**
- Empirical validation of theoretical analysis
- Benchmark comparison baselines (Stage 6)
- Demonstrates understanding of trade-offs
- Validates design decisions with real data
- Identifies crossover points (e.g., when naive wins for small k)

Top candidates implement multiple approaches, not just the "optimal" solution.

## Algorithm Variants

### 1. LinearScanIterator (Naive Baseline)

**File**: `LinearScanIterator.java`

**Algorithm**: Linear scan through k current values to find minimum

**Complexity**:
- Time: O(Nk) - k comparisons per next()
- Space: O(k) - cache current values

**When competitive**: Small k (≤8) where cache locality dominates asymptotic complexity

**Trade-offs**:
- ✓ Perfect cache locality (sequential scan)
- ✓ No tree overhead
- ✓ Branch predictor friendly
- ✗ Poor asymptotic complexity

### 2. HeapBasedIterator (Standard Approach)

**File**: `HeapBasedIterator.java`

**Algorithm**: Binary min-heap using Java's PriorityQueue

**Complexity**:
- Time: O(N log k) - 2 log k comparisons per next() (sift-down)
- Space: O(k) - heap structure

**When competitive**: Standard choice for most k values, excellent cache locality

**Trade-offs**:
- ✓ Simple implementation (leverages stdlib)
- ✓ Array-based heap has excellent cache locality
- ✓ Well-understood and debuggable
- ✗ 2× comparisons vs loser tree

### 3. LoserTreeIterator (Optimized - Selected)

**File**: `LoserTreeIterator.java`

**Algorithm**: Loser tournament tree (Knuth TAOCP §5.4.1)

**Complexity**:
- Time: O(N log k) - log k comparisons per next()
- Space: O(k) - tree structure (k-1 internal nodes)

**When competitive**: Large k where comparison count matters

**Trade-offs**:
- ✓ Half the comparisons of binary heap (log k vs 2 log k)
- ✓ Simpler refill than winner tree (no sibling access)
- ✓ Production validated (Grafana 2024: Loki, Pyroscope, Prometheus)
- ✓ Apache DataFusion: 50% speedup in benchmarks
- ✗ More complex implementation than heap

**Design rationale**: Selected in Stage 3 based on production validation and constant factor improvements.

## Project Structure

```
java/
├── build.gradle                          # Gradle build with run tasks
└── src/main/java/com/research/iterator/
    ├── LinearScanIterator.java           # O(Nk) naive implementation
    ├── LinearScanExample.java            # Demo LinearScanIterator
    ├── HeapBasedIterator.java            # O(N log k) standard (heap)
    ├── HeapBasedExample.java             # Demo HeapBasedIterator
    ├── LoserTreeIterator.java            # O(N log k) optimized (loser tree)
    ├── LoserTreeExample.java             # Demo LoserTreeIterator
    └── ComparisonDemo.java               # Side-by-side comparison
```

**File organization principle**: One class per file, descriptive names, separate examples for each variant.

## Building

```bash
cd java
gradle build
```

**Output**: Compiles all three implementations successfully.

## Running Examples

### Individual Algorithm Demos

```bash
# Run LinearScanIterator example
gradle runLinearScan

# Run HeapBasedIterator example
gradle runHeapBased

# Run LoserTreeIterator example
gradle runLoserTree
```

### Side-by-Side Comparison

```bash
# Compare all three algorithms (default)
gradle run
# or
gradle runComparison
```

**Example output**:
```
=== Small k=3 Comparison ===

Input:
  Iterator 1: [1, 4, 7, 10]
  Iterator 2: [2, 5, 8, 11]
  Iterator 3: [3, 6, 9, 12]

LinearScan (O(Nk)):
  Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  ✓ Sorted correctly

HeapBased (O(N log k), 2 log k comparisons):
  Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  ✓ Sorted correctly

LoserTree (O(N log k), log k comparisons):
  Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
  ✓ Sorted correctly

=== Large k=10 Comparison ===
[... all three variants produce identical correct output ...]
```

## Common Interface

All three implementations:
- Implement `Iterator<T extends Comparable<? super T>>`
- Handle edge cases: empty iterators, single iterator, unequal lengths
- Use null sentinels for exhausted iterators
- Throw `NoSuchElementException` when exhausted
- `remove()` not supported

## Implementation Highlights

### LinearScanIterator

**Key algorithm**:
```java
public T next() {
    // Linear scan to find minimum among current values
    for (int i = 0; i < k; i++) {
        if (currentValues[i] != null && currentValues[i] < minValue) {
            minValue = currentValues[i];
            minIndex = i;
        }
    }
    // Refill from winner's source
    currentValues[minIndex] = sources[minIndex].hasNext()
        ? sources[minIndex].next()
        : null;
    return minValue;
}
```

### HeapBasedIterator

**Key algorithm**:
```java
public T next() {
    Entry<T> entry = heap.poll();  // Extract min (2 log k comparisons)
    T result = entry.value;

    // Refill from same source
    if (entry.source.hasNext()) {
        heap.offer(new Entry<>(entry.source.next(), entry.source));
    }
    return result;
}
```

### LoserTreeIterator

**Key algorithm**:
```java
public T next() {
    T result = winnerValue;

    // Refill: replay tournament comparing against losers (log k comparisons)
    T newValue = sources[winnerIndex].hasNext()
        ? sources[winnerIndex].next()
        : null;

    for (int i = 0; i < tree.length; i++) {
        if (newValue > tree[i].value) {
            swap(newValue, tree[i]);  // Current loses, advance previous winner
        }
    }
    winnerValue = newValue;
    return result;
}
```

## Edge Cases Handled (All Variants)

1. **Single iterator (k=1)**: No tree/heap overhead, direct passthrough
2. **Empty iterators**: Null sentinels (always lose comparisons)
3. **All exhausted**: hasNext() returns false
4. **Unequal lengths**: Works correctly as iterators exhaust independently
5. **Duplicates**: Handled correctly (stable order not guaranteed)

## Correctness Invariants (All Variants)

**Loop invariant**: At start of each next():
- Data structure (heap/tree/cache) contains minimum from each non-exhausted source
- Returned value is global minimum among all remaining elements
- All previously returned elements ≤ all remaining elements
- Output is sorted

## Performance Predictions (to be validated in Stage 6)

| Algorithm | Small k (≤8) | Medium k (10-100) | Large k (≥1000) |
|-----------|--------------|-------------------|-----------------|
| LinearScan | **Competitive** | Slower | Much slower |
| HeapBased | Good | **Best** | Good |
| LoserTree | Good | **Best** | **Best** |

**Crossover analysis**: Linear scan expected to win for k ≤ 8 due to cache effects. Loser tree expected to win for k ≥ 100 where comparison count dominates.

## Gradle Tasks Reference

```bash
gradle build           # Compile all implementations
gradle test            # Run unit tests (Stage 5)
gradle run             # Run ComparisonDemo (default)
gradle runLinearScan   # Run LinearScanExample
gradle runHeapBased    # Run HeapBasedExample
gradle runLoserTree    # Run LoserTreeExample
gradle runComparison   # Run ComparisonDemo (same as 'run')
```

## Next Stages

- **Stage 5**: Comprehensive unit testing (JUnit)
- **Stage 6**: Empirical benchmarking (JMH) to validate predictions
- **Stage 7**: Technical report synthesizing theory + implementation + benchmarks
- **Stage 8**: Cross-artifact consistency validation

## Limitations

- `remove()` not supported (throws UnsupportedOperationException)
- Not thread-safe
- Input iterators must be pre-sorted (not validated - caller responsibility)
- Generic type must implement Comparable
- No custom comparator support

## Future Extensions

From Stage 3 analysis:
- Adaptive algorithm selection (switch based on k)
- Primitive specializations (int, long) to avoid boxing
- Custom comparator support (Comparator<T> parameter)
- Concurrent variant for parallel processing
- Iterator validation mode (debug builds check sortedness)
