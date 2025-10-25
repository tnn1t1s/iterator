# Test Prompt: Sort Array of Size N

**Scenario**: Test iterative_optimizer skill with classic sorting problem

**Problem Statement**:
```
Given an unsorted array of N integers, sort them in ascending order.

Input: Array of N integers (arbitrary values)
Output: Same array sorted in ascending order
Constraints: N can be very large (up to 10^6 elements)
```

**Initial Approach**:
Start with naive bubble sort solution.

**Expected Iteration Flow**:
1. Bubble sort O(n²) → Challenge to O(n log n)
2. Merge sort O(n log n) → Challenge with radix sort O(n) for integers
3. Radix sort O(n) → Challenge on constant factors
4. User stops when satisfied

---

## Test Execution

### Phase 1: Initial Solution
```
User: "Sort an array of N integers"

Expected response: Basic sorting algorithm (likely bubble sort or similar naive approach)
- Time complexity: O(n²)
- Space complexity: O(1) in-place
```

### Phase 2: First Optimization Challenge
```
User: "Can we do better?"

Expected iterative_optimizer response:
- Challenge 1: Merge sort O(n log n)
- Challenge 2: Quicksort O(n log n) average
- Challenge 3: Heapsort O(n log n) guaranteed
- Next step: Try one of the standard O(n log n) algorithms
```

### Phase 3: Second Optimization Challenge
```
User implements merge sort

User: "Can we do better?"

Expected iterative_optimizer response:
- Challenge 1: Comparison-based lower bound Ω(n log n)
- Challenge 2: For integers [0, k], counting sort O(n+k)
- Challenge 3: For fixed-width integers, radix sort O(n)
- Challenge 4: If nearly sorted, Timsort exploits runs
- Next step: If integers, try radix/counting sort
```

### Phase 4: Constant Factor Optimization
```
User implements radix sort O(n)

User: "Can we do better?"

Expected iterative_optimizer response:
- Challenge 1: O(n) optimal for unbounded integers
- Challenge 2: Radix-256 needs 4 passes for 32-bit, radix-65536 needs 2 passes
- Challenge 3: Cache-aware blocking for better locality
- Challenge 4: SIMD vectorization for parallel comparisons
- Next step: Optimize pass count or cache behavior
```

### Phase 5: Microarchitectural Optimization
```
User: "Can we do better?"

Expected iterative_optimizer response:
- Challenge 1: Branchless sorting networks for small subarrays (k ≤ 16)
- Challenge 2: Cache-oblivious sorting for external memory
- Challenge 3: Parallel sorting (sample sort, multi-threaded merge)
- Challenge 4: GPU-accelerated radix sort
- Next step: User typically stops here ("good enough")
```

---

## Success Criteria

### Skill Activation
✅ iterative_optimizer activates on "can we do better?"
✅ Generates 3-5 challenges per iteration
✅ Escalates through complexity → constants → microarchitecture

### Challenge Quality
✅ Specific suggestions (not vague "try optimizing")
✅ Era-appropriate (1999-2002 voice and references)
✅ Cites theory (lower bounds, known algorithms)
✅ Practical (cache behavior, not just Big-O)

### Loop Behavior
✅ Continues even after optimal O(n) achieved
✅ Only stops when user says "stop"
✅ Each iteration builds on previous

### Voice & Tone
✅ Demanding: "Good. Now make it faster."
✅ Pragmatic: "Measure before you micro-optimize"
✅ Era-accurate: "P4 branch mispredicts cost 20 cycles"

---

## Test Commands

### Standalone Mode Test
```
# Phase 1: Initial problem
"Sort an array of N integers"

# Phase 2: First challenge
"Can we do better?"

# Phase 3: After implementing suggested improvement
"Can we do better?"

# Phase 4: Keep pushing
"Can we do better?"

# Phase 5: User stops
"Stop" or "Good enough, continue to implementation"
```

### Pipeline Mode Test
```
# Trigger with aggressive optimization flag
"Research optimal array sorting: analyze complexity, compare designs, with aggressive optimization"

# Should automatically loop through Stage 2.5 optimization challenges
# Until user stops or proven optimal
```

---

## Expected Output Format

Each iteration should follow this pattern:

```
CURRENT SOLUTION: [Algorithm name, O() complexity]

CHALLENGE 1 (Better Complexity): [Specific algorithm suggestion]
CHALLENGE 2 (Different Approach): [Alternative design]
CHALLENGE 3 (Literature): [Reference to known result]
CHALLENGE 4 (Constant Factors): [Practical optimization]
CHALLENGE 5 (Proof or Stop): [Lower bound or continue]

NEXT STEP: [Specific recommendation]
```

---

## Notes

- This is a **classic problem** with well-known optimization path
- Good test because: multiple clear improvements exist
- Tests escalation: O(n²) → O(n log n) → O(n) → constants
- Tests radical rethinking: comparison-based vs counting-based
- Tests stopping: after O(n), should push constants not complexity

---

*Test Scenario | iterative_optimizer validation | 2025-01-15*
