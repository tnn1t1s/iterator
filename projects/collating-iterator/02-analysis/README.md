# Stage 2: Algorithmic Analysis

## Overview

This stage answers the theoretical complexity questions from Stage 1 through genuine discovery (not justification of a predetermined answer).

## Methodology

**CRITICAL**: Literature review conducted BEFORE enumeration:
- Consulted TAOCP Vol 3 §5.4.1, CLRS Ch 6
- WebSearch for k-way merge algorithms, tournament trees, loser trees
- Found 8 candidates (4 optimal, 4 sub-optimal)

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

**Literature Sources**:
- TAOCP Vol 3 §5.4.1: heap, winner tree, **loser tree**
- CLRS Ch 6, Problem 6-2: binary heap, d-ary heaps
- Grafana Labs (2024): loser tree in production
- Frigo et al. (1999): cache-oblivious funnelsort
- WebSearch: modern surveys and discussions

**Approaches Analyzed** (8 total):
1. **Linear Scan**: O(Nk) - simple but sub-optimal
2. **Binary Heap (Priority Queue)**: O(N log k) - **OPTIMAL** ✓
3. **Winner Tournament Tree**: O(N log k) - **OPTIMAL** ✓
4. **Loser Tournament Tree**: O(N log k) - **OPTIMAL** ✓ (Knuth preferred)
5. **D-ary Heap**: O(N log k) - **OPTIMAL** ✓ (d=4 common)
6. **Pairwise Merge**: O(N log k) but violates lazy evaluation (needs O(N) space)
7. **Collect-and-Sort**: O(N log N) - sub-optimal
8. **Cache-Oblivious Funnelsort**: O(N log N) - different problem domain

**Discovery**: Four algorithms match the lower bound:
- Binary heap
- Winner tournament tree
- **Loser tournament tree** (simpler refill, production-proven)
- D-ary heap (d=4)

All achieve O(N log k) time and O(k) space with lazy evaluation.

## Research Questions Answered

From Stage 1 specification:

### Theoretical Complexity

✅ **Lower Bound**: Ω(N log k) comparisons required (proven via decision tree)

✅ **Optimal Time Complexity**: O(N log k) achievable (four algorithms achieve this)

✅ **Optimal Space Complexity**: O(k) achievable (much better than O(N))

✅ **Per-Operation Costs**:
- hasNext(): O(1)
- next(): O(log k)

### Design Alternatives Discovered

Eight candidates analyzed. Four are optimal:
1. **Binary heap** - O(N log k) time, O(k) space, 2 log k comparisons ✓
2. **Winner tree** - O(N log k) time, O(k) space, log k comparisons ✓
3. **Loser tree** - O(N log k) time, O(k) space, log k comparisons ✓
4. **D-ary heap** - O(N log k) time, O(k) space, trade-off parameter ✓

Others sub-optimal:
- Linear scan: Too slow (O(Nk)) but competitive for k ≤ 8
- Pairwise: Wrong space complexity (O(N))
- Collect-sort: Doesn't exploit pre-sorted property (O(N log N))
- Funnelsort: Different problem domain

## What's Next?

**Stage 3 (Design Selection)** will answer:
- Four algorithms are asymptotically optimal. Which is better in practice?
- Constant factor differences:
  * Comparisons: Heap 2 log k vs Tree log k
  * Cache behavior: Heap (array) vs Tree (pointers)
  * When does linear scan become competitive (small k)?
- Loser tree advantages: Simpler refill logic, production-proven (Grafana)

**Key Insight**: We now have FOUR optimal solutions. Stage 3 must compare them without bias to determine which to implement.

## Notes

This analysis was conducted WITHOUT assuming the answer upfront:

1. ✅ Established theoretical limits FIRST
2. ✅ Literature review BEFORE enumeration
3. ✅ Found loser tree (missed in first attempt!)
4. ✅ Explored 8 candidates to see which achieve bounds
5. ✅ Discovered multiple optimal solutions
6. ✅ Deferred constant factor comparison (Stage 3)

This is proper research methodology: establish bounds → research literature → discover optimal solutions → compare optimal solutions.
