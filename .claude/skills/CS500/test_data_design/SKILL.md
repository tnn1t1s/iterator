---
name: test_data_design
description: Designs optimal test data and benchmark inputs for algorithm evaluation. Identifies edge cases, outliers, stress cases, and realistic distributions. Data-driven approach to expose performance characteristics.
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Test Data Design for Benchmarking

## Purpose

Generate comprehensive test data sets that expose algorithmic behavior across the full spectrum of inputs: edge cases, outliers, typical cases, adversarial inputs, and realistic distributions.

**Key Insight**: Top candidates don't just test "happy path" - they systematically enumerate the input space and design data that stresses each dimension independently and in combination.

## Critical: Problem-Specific Analysis

**Before generating data, analyze the problem dimensions:**

1. **Parameter Space**: What are the independent variables?
2. **Edge Cases**: What are the boundary conditions?
3. **Complexity Drivers**: What input characteristics affect performance?
4. **Algorithmic Weaknesses**: What inputs expose worst-case or best-case behavior?
5. **Realistic Distributions**: What do real-world inputs look like?

## K-Way Merge Test Data Design

### Dimension Analysis

For CollatingIterator k-way merge, the critical dimensions are:

1. **k (number of iterators)**: Drives asymptotic complexity
   - Edge: k=1, k=2
   - Small: k=3-8 (linear scan competitive)
   - Medium: k=10-100 (log k matters)
   - Large: k=1000+ (stress test)

2. **N (total elements)**: Drives total work
   - Tiny: N=10-100 (overhead dominates)
   - Small: N=1K-10K (typical)
   - Medium: N=100K-1M (realistic)
   - Large: N=10M+ (stress test)

3. **Distribution of elements across iterators**:
   - Uniform: N/k elements per iterator
   - Skewed: Some iterators much longer than others
   - Power-law: Few iterators have most elements
   - Degenerate: One iterator has all N elements

4. **Value distribution**:
   - Uniform random: [0, MAX)
   - Clustered: Values grouped in ranges
   - Nearly sorted: Small perturbations
   - Adversarial: Alternating between iterators

5. **Iterator exhaustion pattern**:
   - Simultaneous: All finish together
   - Sequential: One-by-one exhaustion
   - Early: Most finish early, one long tail
   - Random: Unpredictable exhaustion

### Test Suite Structure

```
benchmarks/
├── edge_cases/
│   ├── k1_single_iterator.json
│   ├── k2_two_iterators.json
│   ├── all_empty.json
│   ├── one_element_total.json
│   └── one_iterator_dominates.json
├── small_k/
│   ├── k3_uniform_n1000.json
│   ├── k5_uniform_n10000.json
│   └── k8_skewed_n10000.json
├── medium_k/
│   ├── k10_uniform_n100k.json
│   ├── k50_power_law_n100k.json
│   └── k100_clustered_n1m.json
├── large_k/
│   ├── k1000_uniform_n1m.json
│   └── k10000_tiny_n10k.json
├── adversarial/
│   ├── alternating_min_max.json
│   ├── reverse_sorted.json
│   └── worst_case_comparisons.json
└── realistic/
    ├── log_merge_simulation.json
    ├── database_index_merge.json
    └── timeseries_merge.json
```

## Generic Test Data Design Template

### Step 1: Enumerate Dimensions

For any algorithm problem:

1. **Input parameters**: What varies? (size, count, range, etc.)
2. **Input structure**: What patterns matter? (sorted, random, duplicates, etc.)
3. **Edge conditions**: What are the boundaries? (empty, single, max, etc.)
4. **Complexity drivers**: What makes it slow/fast?

### Step 2: Design Test Matrix

Create a matrix crossing dimensions:

```
Dimension 1 (size):     small | medium | large
Dimension 2 (pattern):  random | sorted | adversarial
Dimension 3 (distribution): uniform | skewed | clustered

Total test cases: 3 × 3 × 3 = 27 combinations
```

### Step 3: Add Special Cases

Beyond the matrix:
- **Edge cases**: Boundaries, empty, single element
- **Stress tests**: Maximum scale, pathological inputs
- **Realistic**: Production-like distributions
- **Regression**: Known bugs, past failures

### Step 4: Parameterize for JMH

Use JMH `@Param` annotation for data-driven benchmarks:

```java
@State(Scope.Benchmark)
public class MergeBenchmark {

    @Param({"1", "2", "3", "5", "8", "10", "50", "100", "1000"})
    int k;  // Number of iterators

    @Param({"1000", "10000", "100000", "1000000"})
    int n;  // Total elements

    @Param({"uniform", "skewed", "power_law"})
    String distribution;

    @Param({"random", "clustered", "alternating"})
    String pattern;

    @Setup
    public void setup() {
        // Generate test data based on parameters
        testData = generateTestData(k, n, distribution, pattern);
    }

    @Benchmark
    public void benchmarkLinearScan() {
        // Use testData
    }

    @Benchmark
    public void benchmarkHeapBased() {
        // Use testData
    }

    @Benchmark
    public void benchmarkLoserTree() {
        // Use testData
    }
}
```

## Test Data Generators

### Uniform Distribution
```java
/**
 * Generates k iterators with approximately N/k elements each.
 * Values uniformly distributed in [0, maxValue).
 */
List<Iterator<Integer>> generateUniform(int k, int n, int maxValue) {
    List<List<Integer>> lists = new ArrayList<>();
    Random rand = new Random(42);  // Fixed seed for reproducibility

    // Distribute elements
    for (int i = 0; i < k; i++) {
        List<Integer> list = new ArrayList<>();
        int elementsThisIterator = n / k + (i < n % k ? 1 : 0);

        for (int j = 0; j < elementsThisIterator; j++) {
            list.add(rand.nextInt(maxValue));
        }

        Collections.sort(list);  // Ensure sorted
        lists.add(list);
    }

    return lists.stream().map(List::iterator).collect(Collectors.toList());
}
```

### Skewed Distribution
```java
/**
 * Generates k iterators where first iterator has ~80% of elements.
 * Exposes handling of unbalanced inputs.
 */
List<Iterator<Integer>> generateSkewed(int k, int n, int maxValue) {
    List<List<Integer>> lists = new ArrayList<>();
    Random rand = new Random(42);

    // 80% in first iterator
    int firstSize = (int)(n * 0.8);
    int remaining = n - firstSize;

    // First iterator gets most elements
    List<Integer> first = new ArrayList<>();
    for (int i = 0; i < firstSize; i++) {
        first.add(rand.nextInt(maxValue));
    }
    Collections.sort(first);
    lists.add(first);

    // Remaining k-1 iterators share the rest
    for (int i = 1; i < k; i++) {
        List<Integer> list = new ArrayList<>();
        int elementsThisIterator = remaining / (k - 1) + (i - 1 < remaining % (k - 1) ? 1 : 0);

        for (int j = 0; j < elementsThisIterator; j++) {
            list.add(rand.nextInt(maxValue));
        }
        Collections.sort(list);
        lists.add(list);
    }

    return lists.stream().map(List::iterator).collect(Collectors.toList());
}
```

### Adversarial Pattern
```java
/**
 * Generates data that alternates between iterators, maximizing comparisons.
 * Values: [0, k, 2k, ...] in iter 0
 *         [1, k+1, 2k+1, ...] in iter 1
 *         etc.
 */
List<Iterator<Integer>> generateAlternating(int k, int n) {
    List<List<Integer>> lists = new ArrayList<>();

    for (int i = 0; i < k; i++) {
        lists.add(new ArrayList<>());
    }

    // Distribute values in alternating pattern
    for (int val = 0; val < n; val++) {
        int iteratorIndex = val % k;
        lists.get(iteratorIndex).add(val);
    }

    return lists.stream().map(List::iterator).collect(Collectors.toList());
}
```

## Output: Test Data Catalog

Generate a markdown file documenting all test cases:

```markdown
# Benchmark Test Data Catalog

## Edge Cases

### k1_single_iterator
- **k**: 1
- **N**: 1000
- **Distribution**: N/A (single iterator)
- **Purpose**: Verify no overhead for trivial case
- **Expected**: All algorithms equivalent

### all_empty
- **k**: 10
- **N**: 0
- **Distribution**: All empty
- **Purpose**: Verify empty input handling
- **Expected**: No crashes, zero output

## Small k (Linear Scan Competitive)

### k3_uniform_n1000
- **k**: 3
- **N**: 1000
- **Distribution**: Uniform (333/333/334 elements)
- **Pattern**: Random values [0, 1000000)
- **Purpose**: Baseline small k case
- **Expected**: Linear scan may win due to cache locality

[... continue for all test cases ...]
```

## Integration with JMH

### Benchmark Class Structure

```java
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class CollatingIteratorBenchmark {

    @Param({"1", "3", "10", "100", "1000"})
    int k;

    @Param({"1000", "100000", "1000000"})
    int n;

    @Param({"uniform", "skewed", "alternating"})
    String pattern;

    private List<List<Integer>> testData;

    @Setup(Level.Trial)
    public void generateTestData() {
        TestDataGenerator generator = new TestDataGenerator();
        testData = generator.generate(k, n, pattern);
    }

    @Benchmark
    public List<Integer> linearScan(Blackhole bh) {
        List<Iterator<Integer>> iterators = copyIterators(testData);
        LinearScanIterator<Integer> merged = new LinearScanIterator<>(iterators);

        List<Integer> result = new ArrayList<>();
        while (merged.hasNext()) {
            result.add(merged.next());
        }
        bh.consume(result);  // Prevent dead code elimination
        return result;
    }

    @Benchmark
    public List<Integer> heapBased(Blackhole bh) {
        // Similar
    }

    @Benchmark
    public List<Integer> loserTree(Blackhole bh) {
        // Similar
    }

    private List<Iterator<Integer>> copyIterators(List<List<Integer>> data) {
        // Deep copy to allow multiple runs
        return data.stream()
            .map(ArrayList::new)
            .map(List::iterator)
            .collect(Collectors.toList());
    }
}
```

## Key Principles

1. **Reproducibility**: Use fixed random seeds
2. **Independence**: Each test case varies one dimension at a time
3. **Coverage**: Matrix of dimension combinations
4. **Realism**: Include production-like distributions
5. **Adversarial**: Include worst-case inputs
6. **Documentation**: Catalog explains purpose of each test case
7. **Parameterization**: JMH @Param for systematic exploration

## Cross-Skill Integration

Requires: algorithmic_analysis (understand complexity drivers)
Feeds into: benchmark_design (use test data in JMH benchmarks)
Related: unit_test_generation (unit tests vs benchmark data)
