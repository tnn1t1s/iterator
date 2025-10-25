---
name: comparative_complexity
description: Evaluates multiple algorithmic designs for complexity and constant factors. Generates tabular comparison of Big-O and empirical considerations. Use when comparing design alternatives.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Comparative Complexity Analysis

## Purpose

Compare multiple algorithm designs across asymptotic complexity, constant factors, memory usage, and implementation complexity.

## Instructions

When activated:

1. **Identify Alternatives**
   - List all viable designs
   - Note key distinguishing characteristics
   - Consider both obvious and non-obvious options

2. **Asymptotic Comparison**
   - Time complexity for each
   - Space complexity for each
   - Identify complexity class winners

3. **Constant Factor Analysis**
   - Memory overhead per operation
   - Number of comparisons/allocations
   - Cache behavior expectations

4. **Empirical Considerations**
   - Expected input characteristics
   - Practical crossover points (e.g., k=10, N=1000)
   - Implementation difficulty

5. **Generate Comparison Table**
   ```
   | Design | Time | Space | Constants | Cache | Impl | Notes |
   ```

6. **Recommendation**
   - Best for specific scenarios
   - Trade-offs
   - Decision criteria

## Examples

### Example: K-Way Merge Strategies

```
PROBLEM: Merge k sorted iterators

ALTERNATIVES:
1. Binary min-heap
2. Tournament tree
3. Linear scan
4. Pairwise reduction
5. Segmented priority queue

ASYMPTOTIC COMPARISON:

| Design | Time (per elem) | Space | Worst Time |
|--------|----------------|-------|------------|
| Min-Heap | O(log k) | O(k) | O(N log k) |
| Tournament | O(log k) | O(k) | O(N log k) |
| Linear Scan | O(k) | O(1) | O(Nk) |
| Pairwise | O(log k) | O(k) | O(N log k) |
| Segmented PQ | O(log k) | O(k√B) | O(N log k) |

CONSTANT FACTOR ANALYSIS:

| Design | Comparisons | Allocations | Cache Lines | Branch Mispred |
|--------|-------------|-------------|-------------|----------------|
| Min-Heap | ~2 log k | 0 (array) | ⌈k/8⌉ (64B) | log k |
| Tournament | ~log k | k-1 nodes | k/2 (pointer) | log k |
| Linear Scan | k | 0 | ⌈k/8⌉ | ~k/2 |
| Pairwise | 2 log k | O(k) temp | Variable | log k |
| Segmented | log k + √B | √B | √B | log k + √B |

EMPIRICAL CONSIDERATIONS:

**Small k (k ≤ 8)**:
- Linear scan competitive: 8 compares vs 3 log 8 heap ops
- Cache fits entirely in L1 (64 bytes)
- Branch predictor learns pattern
→ Recommendation: Linear scan or branchless SIMD min

**Medium k (8 < k ≤ 1000)**:
- Heap dominates: O(log k) vs O(k)
- Array-based heap: excellent cache locality
- Tournament tree: pointer chasing kills performance
→ Recommendation: Binary min-heap (array-based)

**Large k (k > 1000)**:
- Heap still O(log k) but deep (log₂ 1000 ≈ 10)
- Cache misses likely (1000 ptrs = 8KB > L1)
- Segmented PQ: batching reduces overhead
→ Recommendation: Segmented PQ or cache-oblivious design

**Crossover Analysis**:
- Linear scan vs heap: k ≈ 8–10 (depends on comparison cost)
- Tournament vs heap: Heap always wins (cache locality)
- Pairwise vs heap: Heap wins unless k is power of 2 and merge cheap

IMPLEMENTATION COMPLEXITY:

| Design | LoC | Tricky Bits | Test Burden |
|--------|-----|-------------|-------------|
| Min-Heap | ~50 | Heapify logic | Medium |
| Tournament | ~80 | Tree rebuild | High |
| Linear Scan | ~20 | None | Low |
| Pairwise | ~60 | Recursion depth | Medium |
| Segmented | ~150 | Buffer mgmt | High |

RECOMMENDATION:

**Default**: Binary min-heap (array-based)
- Best asymptotic for k > 8
- Excellent cache behavior
- Moderate implementation complexity
- Well-understood, easy to optimize

**Special Cases**:
- k ≤ 8: Linear scan (simpler, potentially SIMD)
- k > 1000: Consider segmented PQ if profiling shows bottleneck
- Stable merge required: Tournament tree (preserves ties)
- External memory: Cache-oblivious funnelsort

**Trade-off Summary**:
- Heap: Best all-around (time, space, cache)
- Linear: Simplest, good for tiny k
- Tournament: Use only if stability critical
- Pairwise: No advantage over heap
- Segmented: Overkill unless k very large
```

### Example: Iterator Chaining Strategies

```
PROBLEM: Compose N iterator transformations (map, filter, flatMap)

ALTERNATIVES:
1. Eager evaluation (collect intermediate)
2. Lazy evaluation (nested iterators)
3. Fusion/deforestation (compile-time)
4. Staged evaluation (batch-wise)

COMPARISON TABLE:

| Design | Time | Space | Compile | Syntax | Optimization |
|--------|------|-------|---------|--------|--------------|
| Eager | O(N×M) | O(N) | Simple | Natural | JIT-friendly |
| Lazy | O(N+M) | O(depth) | Simple | Natural | Deforest |
| Fusion | O(N) | O(1) | Complex | Macro | Optimal |
| Staged | O(N+M/B) | O(B) | Moderate | Natural | SIMD |

Where N = elements, M = transformations, B = batch size

DETAILED ANALYSIS:

**Eager Evaluation**:
- Time: O(N) per transformation, O(N×M) total
- Space: O(N) intermediate collections
- Pros: Simple, GC-friendly (short-lived objects), JIT optimizes well
- Cons: O(N) allocation per stage, not truly composable

**Lazy Evaluation**:
- Time: O(N) total (single pass), O(M) overhead per next()
- Space: O(M) stack depth (nested iterator wrappers)
- Pros: True composition, no intermediate allocation, short-circuits
- Cons: Pointer chasing, virtual dispatch overhead, poor cache locality

**Fusion (Compile-Time)**:
- Time: O(N) single pass, minimal overhead
- Space: O(1) per element
- Pros: Optimal code generation, SIMD-friendly, zero abstraction cost
- Cons: Requires sophisticated compiler, language support (Rust, Haskell)

**Staged (Batch-Wise)**:
- Time: O(N + M×B) where B = batch size
- Space: O(B) batch buffer
- Pros: Balances cache reuse with composition, SIMD opportunities
- Cons: API complexity, batch size tuning required

EMPIRICAL CONSIDERATIONS:

**Short chains (M ≤ 3)**:
- Lazy overhead acceptable: ~5-10ns per next() per wrapper
- Fusion gains minimal: ~2x vs lazy
→ Recommendation: Lazy (simplicity wins)

**Long chains (M > 3)**:
- Lazy overhead accumulates: 10M ns per element
- Fusion: Constant ~2ns per element
→ Recommendation: Fusion if available, else staged

**Large datasets (N > 10⁶)**:
- Cache matters more than abstraction
- Staged: Better cache reuse than lazy
→ Recommendation: Staged with B ≈ cache_size / element_size

CONSTANT FACTORS:

| Design | Per-element cost | Memory/element | Virtual calls |
|--------|------------------|----------------|---------------|
| Eager | 50ns (allocate) | 16B (pointer) | 0 (monomorphic) |
| Lazy | 10M ns (dispatch) | 0 (transient) | M (megamorphic) |
| Fusion | 2ns (inline) | 0 | 0 (inlined) |
| Staged | 5ns (amortized) | B×16B (batch) | M/B |

LANGUAGE CONSIDERATIONS:

**Java**:
- Stream API: Lazy with fusion attempts
- Reality: Megamorphic dispatch often prevents fusion
- Recommendation: Eager for short chains, manual fusion for hot paths

**C++**:
- Ranges: Lazy with strong fusion (zero-cost abstractions)
- Templates enable complete inlining
- Recommendation: Lazy (ranges), trust optimizer

**Rust**:
- Iterators: Lazy with excellent fusion
- No virtual dispatch, monomorphization
- Recommendation: Lazy always (zero cost)

DECISION MATRIX:

| Condition | Recommended | Rationale |
|-----------|-------------|-----------|
| M ≤ 3, any N | Lazy | Simplicity, adequate perf |
| M > 3, N < 10³ | Lazy/Eager | Small dataset, perf irrelevant |
| M > 3, N > 10⁶ | Fusion/Staged | Overhead matters |
| Hot path | Fusion | Always optimize hot paths |
| Java | Eager or manual | JIT fusion unreliable |
| C++/Rust | Lazy | Trust zero-cost abstractions |
```

## Output Format

1. List alternatives with key characteristics
2. Asymptotic comparison table
3. Constant factor analysis table
4. Empirical crossover points
5. Implementation complexity assessment
6. Clear recommendation with decision criteria

## Cross-Skill Integration

Requires:
- **problem_specification**: Clear problem to compare
- **algorithmic_analysis**: Base complexity for each design

Feeds into:
- **systems_design_patterns**: Informs design choice
- **technical_exposition**: Comparison forms key section of writeup

## Notes

- Don't just list Big-O; include constants
- Real-world crossover points matter more than asymptotic limits
- Implementation complexity is a valid consideration
- Language/platform affects constant factors significantly
