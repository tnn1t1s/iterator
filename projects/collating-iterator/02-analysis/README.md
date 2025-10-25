# Stage 2: Algorithmic Analysis

## Overview

This stage answers the theoretical complexity questions from Stage 1 through genuine discovery (not justification of a predetermined answer).

## Documents

### 2A: Lower Bound Analysis (`lower-bound.tex`)

**Discovers**: What is theoretically impossible to beat?

**Key Results**:
- Comparison lower bound: Ω(N log k) via decision tree argument
- Space lower bound: Ω(k)
- Proof uses multinomial coefficients and information theory
- This bound is **tight** (achievable)

**Method**: Information-theoretic analysis, not assuming any specific algorithm.

### 2B: Candidate Algorithms (`candidate-algorithms.tex`)

**Discovers**: Which algorithms (if any) achieve the lower bound?

**Approaches Analyzed**:
1. **Linear Scan**: O(Nk) - simple but sub-optimal
2. **Priority Queue (Heap)**: O(N log k) - **OPTIMAL** ✓
3. **Tournament Tree**: O(N log k) - **OPTIMAL** ✓
4. **Pairwise Merge**: O(N log k) but violates lazy evaluation (needs O(N) space)
5. **Collect-and-Sort**: O(N log N) - sub-optimal

**Discovery**: Two algorithms match the lower bound:
- Priority queue (binary min-heap)
- Tournament tree

Both achieve O(N log k) time and O(k) space with lazy evaluation.

## Research Questions Answered

From Stage 1 specification:

### Theoretical Complexity

✅ **Lower Bound**: Ω(N log k) comparisons required (proven via decision tree)

✅ **Optimal Time Complexity**: O(N log k) achievable (heap and tournament both achieve this)

✅ **Optimal Space Complexity**: O(k) achievable (much better than O(N))

✅ **Per-Operation Costs**:
- hasNext(): O(1)
- next(): O(log k)

### Design Alternatives Discovered

Five candidates analyzed. Two are optimal:
1. **Heap-based** - O(N log k) time, O(k) space ✓
2. **Tournament tree** - O(N log k) time, O(k) space ✓

Others sub-optimal:
- Linear scan: Too slow (O(Nk))
- Pairwise: Wrong space complexity (O(N))
- Collect-sort: Doesn't exploit pre-sorted property (O(N log N))

## What's Next?

**Stage 3 (Design Selection)** will answer:
- Both heap and tournament are asymptotically optimal. Which is better in practice?
- What are the constant factor differences?
- When does linear scan become competitive (small k)?
- Cache behavior analysis

**Key Insight**: We now have TWO optimal solutions. Stage 3 must compare them without bias to determine which to implement.

## Notes

This analysis was conducted WITHOUT assuming the answer upfront. The lower bound was established first, then algorithms were explored to see which achieve it. This is proper research methodology:

1. Establish theoretical limits
2. Explore candidate solutions
3. Discover which solutions are optimal
4. Compare optimal solutions (Stage 3)
