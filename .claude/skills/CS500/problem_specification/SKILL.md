---
name: problem_specification
description: Extracts formal problem definitions from vague requirements. Identifies type constraints, invariants, assumptions, and contracts. Use when converting informal problem descriptions into rigorous specifications.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Problem Specification

## Purpose

Transform informal problem statements into formal specifications suitable for algorithm design and implementation.

## Instructions

When activated, perform these steps:

1. **Extract Core Requirements**
   - Identify input types and constraints
   - Identify output types and guarantees
   - Note any performance requirements

2. **Formalize Invariants**
   - State what must remain true throughout operation
   - Identify pre-conditions and post-conditions
   - Document ordering guarantees, uniqueness constraints

3. **Enumerate Assumptions**
   - Explicit: stated by user
   - Implicit: required for correctness
   - Edge cases: empty inputs, duplicates, ordering

4. **Define Contracts**
   - Method signatures with type parameters
   - Exception/error conditions
   - Resource ownership semantics

5. **Structure Output**
   ```
   PROBLEM: [One-line summary]

   INPUTS:
   - Type: ...
   - Constraints: ...

   OUTPUTS:
   - Type: ...
   - Guarantees: ...

   INVARIANTS:
   - ...

   ASSUMPTIONS:
   - ...

   CONTRACT:
   - Preconditions: ...
   - Postconditions: ...
   - Exceptions: ...

   RESEARCH QUESTIONS:
   - What is the optimal complexity? (don't prescribe - let analysis discover)
   - What design alternatives exist?
   - Can we improve constant factors beyond optimal big-O?
   - When do naive approaches outperform sophisticated ones?
   ```

6. **Avoid Prescribing Solutions**
   - DON'T specify O(N log k) or name data structures (heap, tree)
   - DO pose questions: "What is the minimum complexity achievable?"
   - Frame as discovery: Analysis will determine optimal approach
   - Use subsection headers (`\subsection{}`) not inline bold (`\textbf{}`)

## Examples

### Example 1: Merge Sorted Iterators

**Vague**: "Combine multiple sorted sequences into one sorted output"

**Formal**:
```
PROBLEM: K-way merge of sorted iterators

INPUTS:
- Type: Collection<Iterator<T>> where T: Comparable
- Constraints: Each iterator yields elements in non-decreasing order
- Constraints: K ≥ 0 iterators

OUTPUTS:
- Type: Iterator<T>
- Guarantees: Elements in non-decreasing order
- Guarantees: All elements from all inputs appear exactly once

INVARIANTS:
- Each input iterator advanced at most once ahead of output
- No iterator advanced past its exhaustion
- Output ordering consistent with input orderings

ASSUMPTIONS:
- Comparable.compareTo() is consistent with equals()
- Iterators are single-pass, forward-only
- No concurrent modification of source collections

CONTRACT:
- Preconditions: All input iterators sorted per compareTo()
- Postconditions: Output exhausted ⟺ all inputs exhausted
- Exceptions: NullPointerException if iterator collection is null
- Exceptions: ClassCastException if elements not mutually comparable
```

### Example 2: Flat Map Iterator

**Vague**: "Apply function returning iterator to each element"

**Formal**:
```
PROBLEM: Lazy flat-mapping over iterator

INPUTS:
- Type: Iterator<T>, Function<T, Iterator<U>>
- Constraints: None on input iterator ordering
- Constraints: Function must not return null

OUTPUTS:
- Type: Iterator<U>
- Guarantees: Lazy evaluation (no pre-computation)
- Guarantees: Preserves encounter order within each mapped iterator

INVARIANTS:
- Current inner iterator fully consumed before advancing outer
- Function applied to each element exactly once
- No buffering beyond current inner iterator

ASSUMPTIONS:
- Function is stateless or properly handles state
- Mapped iterators are independent
- Memory sufficient for one inner iterator at a time

CONTRACT:
- Preconditions: Function returns non-null Iterator<U>
- Postconditions: All elements from all mapped iterators returned
- Exceptions: NullPointerException if function or result is null
```

## Output Format

Always structure as:
1. One-line PROBLEM statement
2. INPUTS with types and constraints
3. OUTPUTS with types and guarantees
4. INVARIANTS (≥3 for non-trivial problems)
5. ASSUMPTIONS (explicit + implicit)
6. CONTRACT (pre/post conditions, exceptions)

## Cross-Skill Integration

Feeds into:
- **algorithmic_analysis**: Formal spec enables complexity analysis
- **comparative_complexity**: Clear constraints allow design comparison
- **multi_language_codegen**: Contract informs implementation

## Notes

- Precision over brevity
- Type constraints must be checkable
- Invariants must be testable
- Distinguish assumptions from requirements
