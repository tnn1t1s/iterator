---
name: systems_design_patterns
description: Encodes classic systems design heuristics including heap vs tree selection, merge strategies, and cache-aware design. Reasons about data locality and virtual call overhead. Use for data structure selection and systems-level design choices.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Systems Design Patterns

## Purpose

Apply battle-tested systems design heuristics focusing on data structure selection, algorithmic strategies, and microarchitectural awareness.

## Instructions

When activated:

1. **Pattern Identification**
   - Recognize problem class (streaming, batching, composition, aggregation)
   - Map to canonical patterns (producer-consumer, pipeline, scatter-gather)

2. **Data Structure Selection**
   - Consider access patterns (sequential, random, mixed)
   - Evaluate cache behavior (locality, prefetch, alignment)
   - Compare heap vs tree vs hash table vs array

3. **Algorithm Strategy**
   - Streaming vs batching trade-offs
   - Lazy vs eager evaluation
   - In-place vs allocation-heavy approaches

4. **Systems-Level Concerns**
   - Memory layout and alignment
   - Virtual dispatch overhead
   - Branch prediction friendliness
   - Lock contention and false sharing

5. **Era-Appropriate Idioms** (1999-2002)
   - "Keep data hot in cache"
   - "Don't trust the JIT"
   - "Profile before you optimize, but think before you code"

## Classic Patterns

### Pattern: Heap vs Tree for Priority Operations

**Decision Criteria**:
- **Array-based heap**: Best default
  * O(log n) insert/extract
  * Excellent cache locality (contiguous memory)
  * No pointer overhead
  * Simple implementation

- **Balanced tree (RB, AVL)**: When you need ordered iteration
  * O(log n) insert/delete/search
  * Poor cache locality (pointer chasing)
  * 3-5x memory overhead (pointers, color bits)
  * More complex implementation

- **Skip list**: When you need concurrent access
  * Probabilistic O(log n)
  * Lock-free variants possible
  * Poor cache locality
  * Simple lock-free algorithm

**Recommendation**:
- Priority queue operations only → **Heap**
- Need in-order traversal → **Tree**
- Concurrent access → **Skip list** or segmented heap

### Pattern: Linear Scan vs Binary Search

**Crossover Analysis** (1999-2002 hardware: P4, early Athlon):

```
Cost Model:
- Sequential access: ~1 cycle + prefetch
- Random access: ~100 cycles (L2 miss)
- Comparison: ~1 cycle
- Branch mispredict: ~20 cycles

Linear scan (n elements):
  Cost = n × (1 comparison + 1 cycle) ≈ 2n cycles

Binary search (n elements):
  Cost = log₂(n) × (1 comparison + 100 cycles + 20 cycles mispredict)
       ≈ 120 log₂(n) cycles

Crossover: 2n = 120 log₂(n)
          n ≈ 50-100 elements (depends on element size)
```

**Recommendation**:
- n < 64: Linear scan (fits in cache line or two)
- n > 100: Binary search
- 64 ≤ n ≤ 100: Measure (platform-dependent)
- Always: Consider sorted SIMD scan for small n

### Pattern: Eager vs Lazy Evaluation

**Eager (Materialize Intermediate Results)**:
```
Pros:
- Simple mental model
- GC-friendly (short-lived objects)
- JIT can optimize monomorphic calls
- Parallelizable (each stage independent)

Cons:
- O(n) allocation per stage
- Memory bandwidth limited
- No short-circuit evaluation

When: Short pipelines (≤3 stages), large datasets
```

**Lazy (Iterator Chaining)**:
```
Pros:
- Zero intermediate allocation
- Short-circuit evaluation
- Composable abstraction
- Constant space

Cons:
- Virtual dispatch overhead (megamorphic in Java)
- Poor instruction cache (scattered code)
- Pointer chasing (poor data cache)
- Nested calls (stack pressure)

When: Long pipelines, need composition, small datasets
```

**Hybrid (Staged/Batched)**:
```
Pros:
- Batches reuse cache
- Amortizes dispatch overhead
- SIMD-friendly
- Balances cache vs composition

Cons:
- API complexity
- Batch size tuning required
- Some allocation (batch buffers)

When: Long pipelines + large datasets (hot path)
```

**Decision Matrix**:
| Pipeline Length | Dataset Size | Recommendation |
|-----------------|--------------|----------------|
| ≤3 stages | Any | Lazy (simplicity) |
| >3 stages | <10³ elements | Lazy (allocation irrelevant) |
| >3 stages | >10⁶ elements | Staged (cache matters) |
| Hot path | Any | Profile, likely staged |

### Pattern: Merge Strategies

**Binary Merge Tree** (Divide and Conquer):
```
Structure: ((A ⊕ B) ⊕ (C ⊕ D)) for 4 inputs
Time: O(n log k) where k = number of inputs
Space: O(k) temporary storage for intermediate results

Pros:
- Parallelizable (tree structure)
- Simple recursive implementation
- Balanced workload

Cons:
- Materializes intermediate results
- O(k) extra space
- Multiple passes over data

When: Parallel merge, batch processing
```

**Heap-Based Multi-Way Merge**:
```
Structure: Min-heap of size k, extract-min + refill
Time: O(n log k)
Space: O(k) heap only

Pros:
- Single pass
- Minimal space (no intermediates)
- Online algorithm (streaming)

Cons:
- Not parallelizable (single heap)
- Virtual dispatch in Java (Comparable)

When: Sequential streaming, memory-constrained
```

**Tournament Tree**:
```
Structure: Complete binary tree, bottom-up min propagation
Time: O(n log k)
Space: O(k) tree nodes

Pros:
- Stable merge (preserves order of ties)
- Fewer comparisons than heap (log k vs 2 log k)

Cons:
- Pointer chasing (cache-hostile)
- More complex implementation
- Rebuild cost on element consumption

When: Stability required, comparisons expensive
```

**Recommendation**:
- Default: **Heap** (single pass, minimal space, cache-friendly)
- Parallel: **Binary tree** (divide-and-conquer)
- Stable: **Tournament** (preserves order)

### Pattern: Cache-Aware Design

**Principle 1: Sequential > Random**
```
Sequential scan: 1 cycle/element (prefetch hides latency)
Random access: 100+ cycles/element (cache miss)

Implication:
- Array iteration: ~0.5 GB/s → ~2 GB/s (P4 era)
- Hash table lookup: ~10M ops/s (random access bound)

Design: Prefer sequential data structures (array, vector) over pointer-based (linked list, tree)
```

**Principle 2: Fit in Cache**
```
L1: 16-32 KB (1-2 cycles)
L2: 256-512 KB (10-20 cycles)
L3/RAM: >1MB (100+ cycles)

Sweet spot: Working set < 256 KB fits in L2

Implication:
- Priority queue k ≤ 1000: Heap array fits in L2 (8KB @ 8 bytes/element)
- Sorting n ≤ 16K elements: Quicksort fits in L2, optimal
- Hash table: 256KB / 16 bytes ≈ 16K entries fit in L2

Design: Size data structures to fit in L2 if possible
```

**Principle 3: Avoid False Sharing**
```
Cache line: 64 bytes (P4 era)
False sharing: Two threads access different data in same cache line

Bad:
struct Counters {
    long threadACount;  // offset 0
    long threadBCount;  // offset 8  ← SAME CACHE LINE!
};

Good:
struct Counters {
    long threadACount;
    char padding[56];   // Force different cache lines
    long threadBCount;
};

Design: Pad shared data structures to cache line boundaries
```

**Principle 4: Prefetch-Friendly Patterns**
```
Hardware prefetcher detects:
- Sequential access (stride = 1 element)
- Constant stride access (stride = k elements)

Misses:
- Pointer chasing (next = *ptr)
- Irregular access patterns

Design:
- Iterate arrays forward (not backward, not pointer-based)
- Use structure-of-arrays (SoA) not array-of-structures (AoS) for hot fields
```

## Example Application: Iterator Merge Design

**Problem**: Merge k sorted iterators

**Analysis**:
1. **Pattern**: Multi-way merge, priority queue problem
2. **Options**: Heap, tree, linear scan
3. **Expected k**: Typically 2-10 (rarely >100)
4. **Performance**: Hot path, called millions of times

**Design Decision**:
```
If k ≤ 8:
  → Branchless SIMD min (8 elements fit in one AVX register)
  → Or simple linear scan (8 compares = ~16 cycles vs heap ~20 cycles)

If 8 < k ≤ 1000:
  → Array-based binary heap
  → Heap size: k × (8 bytes ptr + 8 bytes value) = 16k bytes
  → For k=100: 1.6 KB fits entirely in L1
  → Cache-friendly, excellent constants

If k > 1000:
  → Still heap, but consider segmented priority queue
  → Heap size: 16 KB fits in L1/L2
  → Profile: Is heap access the bottleneck?
  → Probably not; I/O bound for large k

Chosen: Array-based heap (optimal for expected case k ≤ 100)
```

**Implementation Notes**:
- Use `PriorityQueue` in Java (backed by array)
- Use `std::priority_queue` in C++ (backed by vector)
- Use `BinaryHeap` in Rust (backed by Vec)
- All use array storage → good cache locality
- All O(log k) operations
- Simple, well-tested, optimal

## Era-Appropriate Voice (1999-2002)

**Idioms**:
- "Keep it simple; the optimizer won't save you from stupid"
- "Profile in production, not in toy benchmarks"
- "Cache misses kill; think about your data layout"
- "Virtual dispatch is not free; don't layer adapters"
- "The best optimization is a better algorithm"

**Skepticism**:
- Don't trust JIT to fuse iterator chains (Java reality)
- Don't trust compiler to vectorize (GCC 3.x limitations)
- Do measure; intuition fails on modern CPUs

**Priorities**:
1. Correct
2. Simple
3. Measurably fast (not theoretically fast)

## Cross-Skill Integration

Requires:
- **comparative_complexity**: Complexity analysis of options
- **microarchitectural_modeling**: Cache/CPU models

Feeds into:
- **multi_language_codegen**: Design informs implementation
- **benchmark_design**: Design assumptions need validation

## References

- Knuth TAOCP Vol 3: Sorting and Searching
- Hennessy & Patterson: Computer Architecture (3rd ed, 2002)
- "What Every Programmer Should Know About Memory" (Drepper, 2007 but foundational)

## Notes

- Patterns are heuristics, not laws
- Measure before and after optimizations
- Era-aware: P4 Northwood, Athlon XP, early Xeons
- Modern CPUs differ but principles hold
