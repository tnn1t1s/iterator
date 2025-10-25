---
name: algorithmic_analysis
description: Applies algorithm design theory including asymptotic analysis, data structure selection, and correctness proofs. References Knuth TAOCP, CLRS, Sedgewick. Use for complexity analysis and algorithmic correctness.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Algorithmic Analysis

## Purpose

Provide rigorous theoretical analysis of algorithms including time/space complexity, correctness proofs, and data structure selection.

## Methodology: Discovery, Not Justification

**CRITICAL**: This skill performs DISCOVERY, not post-hoc rationalization.

- **Wrong approach**: "Use O(N log k) heap algorithm. Let me prove it's optimal."
- **Right approach**: "Establish lower bound. Explore candidates. Discover which achieve bound."

Process:
1. Lower bounds BEFORE specific algorithms
2. Multiple candidates WITHOUT assuming winner
3. Systematic comparison reveals optimal solutions
4. Defer constant factors to comparative_complexity skill

## Instructions

When activated, perform in two phases:

### Phase A: Lower Bound Analysis (Discovery)

Establish theoretical limits BEFORE analyzing specific algorithms:

1. **Comparison Lower Bound**
   - Use decision tree argument for comparison-based algorithms
   - Count possible outputs (e.g., multinomial coefficients for k-way merge)
   - Compute log₂(outputs) for comparison lower bound
   - Information-theoretic argument

2. **Space Lower Bound**
   - Minimum state required (e.g., k iterator positions)
   - Memory access patterns

3. **Establish Ω() Bounds**
   - Worst-case lower bound
   - Average-case if applicable
   - Prove bounds are tight (achievable)

### Phase B: Candidate Algorithm Analysis (Discovery)

Systematically explore multiple approaches WITHOUT assuming answer:

1. **Enumerate Candidates**
   - List 4-6 natural approaches
   - Include naive, standard, and clever alternatives
   - Example: linear scan, heap, tree, pairwise merge, collect-sort

2. **Analyze Each Candidate**
   - Time complexity (best, average, worst)
   - Space complexity (auxiliary + total)
   - Does it satisfy problem constraints? (lazy evaluation, etc.)
   - Does it achieve lower bound?

3. **Comparison Table**
   - Create table comparing all candidates
   - Columns: Algorithm, Time, Space, Constraints Met, Optimal?
   - Identify which (if any) achieve lower bound

4. **Correctness Proof** (for optimal candidates only)
   - Loop invariants for iterative algorithms
   - Induction for recursive algorithms
   - Termination argument
   - Proof that output satisfies postconditions

5. **Constant Factors** (for optimal candidates)
   - Note hidden constants in Big-O
   - Memory overhead per element/node
   - Number of comparisons/swaps in practice

6. **Output Structure**

Phase A (lower-bound.tex):
```
RESEARCH QUESTION: What is theoretically impossible to beat?

LOWER BOUND ANALYSIS:
- Problem: ...
- Possible outputs: ... (count using combinatorics)
- Decision tree depth: log₂(outputs) = ...
- Theorem: Ω(...) comparisons required

PROOF:
- Decision tree model
- Leaf counting
- Information-theoretic argument

SPACE BOUND: Ω(...) required because ...

CONCLUSION: Bounds are tight (will be achieved in Phase B)
```

Phase B (candidate-algorithms.tex):
```
RESEARCH QUESTION: Which algorithms (if any) achieve the lower bound?

CANDIDATE 1: [Naive approach]
- Time: ...
- Space: ...
- Achieves bound? No
- Why: ...

CANDIDATE 2: [Standard approach]
- Algorithm: ...
- Time: ...
- Space: ...
- Correctness: Invariant + Proof
- Achieves bound? Yes/No

[Continue for 4-6 candidates]

COMPARISON TABLE:
| Algorithm | Time | Space | Constraints | Optimal? |
|-----------|------|-------|-------------|----------|
| ...       | ...  | ...   | ...         | ...      |

DISCOVERY: N algorithms achieve lower bound: [...list...]

CONCLUSION:
- Optimal algorithms identified
- Defer constant factor comparison to Stage 3
```

## Examples

### Example 1: K-Way Merge Analysis (Two-Phase)

**Phase A: Lower Bound (lower-bound.tex)**

```
RESEARCH QUESTION: What is the minimum number of comparisons required?

LOWER BOUND ANALYSIS:
Problem: Merge k sorted sequences with N total elements

Possible outputs: Multinomial coefficient
  C(N; n₀,n₁,...,n_{k-1}) = N! / (n₀! · n₁! · ... · n_{k-1}!)

Decision tree depth: log₂(C) comparisons required

Equal-length case (n_i = N/k):
  log₂(N!/(N/k!)^k) = N log N - k·(N/k)·log(N/k)
                    = N log N - N(log N - log k)
                    = N log k

THEOREM: Any comparison-based k-way merge requires Ω(N log k) comparisons

PROOF:
- Decision tree must have C(N;...) leaves (one per output permutation)
- Binary tree of depth d has ≤ 2^d leaves
- Therefore d ≥ log₂(C) = Ω(N log k)

SPACE BOUND: Ω(k) required
- Must track position in each of k iterators
- Minimum k references/pointers

CONCLUSION: Bounds are tight (achievable - see Phase B)
```

**Phase B: Candidate Algorithms (candidate-algorithms.tex)**

```
RESEARCH QUESTION: Which algorithms (if any) achieve Ω(N log k)?

CANDIDATE 1: Linear Scan
Algorithm: Check all k iterators for minimum each time
Time: O(Nk) - scan k iterators N times
Space: O(k) - iterator references only
Achieves bound? NO (O(Nk) >> Ω(N log k) for k > log k)
When competitive: k ≤ 8 (simple, cache-friendly)

CANDIDATE 2: Priority Queue (Binary Min-Heap)
Algorithm: Maintain heap of k elements (one per iterator)
Time: O(N log k) - N operations × log k heap cost
Space: O(k) - heap storage
Correctness:
  Invariant: Heap contains min unconsumed element from each non-empty iterator
  Proof: Extract min, refill from source iterator, repeat
Achieves bound? YES ✓

CANDIDATE 3: Tournament Tree
Algorithm: Binary tree with k leaves, internal nodes = min(children)
Time: O(N log k) - propagate up log k levels per extraction
Space: O(k) - tree nodes
Achieves bound? YES ✓

CANDIDATE 4: Pairwise Merge
Algorithm: Recursively merge pairs (I₀⊕I₁)⊕(I₂⊕I₃)...
Time: O(N log k) if materialized
Space: O(N) - must store intermediate results
Achieves bound? NO (violates lazy evaluation, wrong space)

CANDIDATE 5: Collect-and-Sort
Algorithm: Consume all iterators → array, sort, return iterator
Time: O(N log N)
Space: O(N)
Achieves bound? NO (doesn't exploit pre-sorted property)

COMPARISON TABLE:
| Algorithm      | Time         | Space | Lazy? | Optimal? |
|----------------|--------------|-------|-------|----------|
| Linear Scan    | O(Nk)        | O(k)  | Yes   | No       |
| Priority Queue | O(N log k)   | O(k)  | Yes   | YES ✓    |
| Tournament     | O(N log k)   | O(k)  | Yes   | YES ✓    |
| Pairwise       | O(N log k)   | O(N)  | No    | No       |
| Collect-Sort   | O(N log N)   | O(N)  | No    | No       |

DISCOVERY: Two algorithms achieve Ω(N log k) lower bound:
1. Priority Queue (binary min-heap)
2. Tournament Tree

CONCLUSION:
- Both asymptotically optimal
- Constant factor comparison deferred to Stage 3
- Open question: Which is better in practice?
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

### Two-Document Pattern

For complex problems, split into separate LaTeX documents:

1. **lower-bound.tex**: Establishes Ω() bounds via decision trees, information theory
2. **candidate-algorithms.tex**: Analyzes 4-6 approaches, comparison table, discovery

Benefits:
- Separation of concerns (theory vs algorithms)
- Avoids assuming solution while proving lower bound
- Clear discovery narrative: limits → candidates → optimal solutions
- Matches research methodology: establish bounds THEN find algorithms achieving them
