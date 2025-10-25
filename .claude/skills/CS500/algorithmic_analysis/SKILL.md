---
name: algorithmic_analysis
description: Applies algorithm design theory including asymptotic analysis, data structure selection, and correctness proofs. References Knuth TAOCP, CLRS, Sedgewick. Use for complexity analysis and algorithmic correctness.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Algorithmic Analysis

## Purpose

Provide rigorous theoretical analysis of algorithms including time/space complexity, correctness proofs, and data structure selection.

## Instructions

When activated, perform:

1. **Asymptotic Analysis**
   - Best, average, worst-case time complexity
   - Space complexity (auxiliary + total)
   - Amortized analysis if applicable
   - Express using Big-O, Θ, Ω notation

2. **Data Structure Selection**
   - Justify choice based on operations needed
   - Consider cache locality, allocation patterns
   - Compare alternatives (heap vs tree, array vs list)

3. **Correctness Proof**
   - Loop invariants for iterative algorithms
   - Induction for recursive algorithms
   - Termination argument
   - Proof that output satisfies postconditions

4. **Constant Factors**
   - Note hidden constants in Big-O
   - Memory overhead per element/node
   - Number of comparisons/swaps in practice

5. **Structure Output**
   ```
   ALGORITHM: [Name]

   TIME COMPLEXITY:
   - Best: ...
   - Average: ...
   - Worst: ...

   SPACE COMPLEXITY:
   - Auxiliary: ...
   - Total: ...

   CORRECTNESS:
   - Invariant: ...
   - Proof: ...

   DATA STRUCTURES:
   - Choice: ...
   - Justification: ...

   CONSTANT FACTORS:
   - ...
   ```

## Examples

### Example 1: K-Way Merge with Min-Heap

```
ALGORITHM: Heap-based k-way merge

TIME COMPLEXITY:
- Best: O(N log k) where N = total elements
- Average: O(N log k)
- Worst: O(N log k)
- Per next(): O(log k) for heap operations

SPACE COMPLEXITY:
- Auxiliary: O(k) for heap storage
- Total: O(k) (input iterators not counted)

CORRECTNESS:
Invariant: Heap contains minimum unconsumed element from each non-empty iterator

Proof:
1. Initially: Extract first element from each iterator → heap
2. Loop: Extract min from heap, advance that iterator, insert next element
3. Invariant maintained: heap always has ≤k elements, each the min of its iterator
4. Termination: All iterators exhausted → heap empty
5. Output: Extracting mins in order produces globally sorted sequence

Loop Invariant Formal:
  ∀i ∈ [1..k]: heap[i] = min(remaining(iterator_i)) ∨ iterator_i.exhausted

DATA STRUCTURES:
- Choice: Binary min-heap (array-based)
- Justification:
  * O(log k) insert/extract vs O(k) for linear scan
  * Cache-friendly array storage vs pointer-based tree
  * k typically small (<100), heap overhead acceptable

CONSTANT FACTORS:
- Heap operations: ~2 log k comparisons (heapify)
- Memory: k pointers + k cached elements ≈ 16k bytes (64-bit)
- Branch mispredictions: log k (heap traversal)

ALTERNATIVES CONSIDERED:
- Tournament tree: Same O(log k) but worse constants (pointer chasing)
- Linear scan: O(k) per element → O(Nk) total, unacceptable for k>10
- Balanced BST: O(log k) but allocation overhead, worse cache behavior
```

### Example 2: Lazy FlatMap

```
ALGORITHM: Nested iterator with on-demand expansion

TIME COMPLEXITY:
- Best: O(1) if first element immediately available
- Average: O(m) where m = average inner iterator size (amortized O(1) per element)
- Worst: O(M) where M = max inner iterator size for single next() call
- Total: O(N) for N total elements across all inner iterators

SPACE COMPLEXITY:
- Auxiliary: O(1) (only current inner iterator reference)
- Total: O(d) where d = recursion depth if nested flatMaps

CORRECTNESS:
Invariant: currentInner = null ∨ currentInner.hasNext() ∨ outer.exhausted

Proof:
1. Initially: currentInner = null, outer points to first element
2. On next():
   a. If currentInner != null && hasNext(): return currentInner.next()
   b. Else: advance outer, apply function, set currentInner, recurse
3. Invariant: Either we have an element ready, or we're fetching the next inner
4. Termination: outer exhausted && currentInner exhausted
5. Lazy evaluation: function applied only when outer element accessed

Amortized Analysis:
- Each element returned: O(1)
- Inner iterator transition: O(1) amortized (bounded by number of outer elements)
- Total: O(N) for N elements

DATA STRUCTURES:
- Choice: Two iterator references (outer, currentInner)
- Justification:
  * No buffering required
  * Minimal memory footprint
  * True lazy evaluation

CONSTANT FACTORS:
- Per next(): 1-2 null checks, 1 function application (amortized)
- Memory: 2 references = 16 bytes (64-bit)
- No allocation unless function allocates

THEORETICAL NOTES:
- This is a catamorphism (fold) in category theory
- Maintains monad laws: flatMap(f).flatMap(g) = flatMap(x → f(x).flatMap(g))
- Deforestation: Chained flatMaps can fuse in optimizing compilers
```

## References

- **Knuth TAOCP**: Vol 1 (data structures), Vol 3 (sorting/searching)
- **CLRS**: Heap operations (Ch 6), Amortized analysis (Ch 17)
- **Sedgewick**: Iterator patterns, lazy evaluation

## Cross-Skill Integration

Requires:
- **problem_specification**: Formal spec before analysis

Feeds into:
- **comparative_complexity**: Provides base analysis for comparison
- **microarchitectural_modeling**: Theoretical → practical mapping
- **technical_exposition**: Analysis forms core of writeup

## Notes

- Distinguish asymptotic from constant factors
- Amortized analysis for sequences of operations
- Cache complexity (I/O model) for large datasets
- Probabilistic analysis for randomized algorithms
