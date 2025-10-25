---
name: technical_exposition
description: Writes in academic engineering tone, clearly separating theory, implementation, and empirical observation. Use for CS500-level technical documentation and papers.
allowed-tools: Read, Write, Edit
---

# Technical Exposition

## Purpose

Produce clear, rigorous technical writing suitable for graduate-level systems courses, separating theory, implementation, and measurement.

## Document Structure

### 1. Abstract (150-250 words)
- Problem statement
- Approach
- Key findings (quantitative)
- Significance

### 2. Introduction
- Motivation: Why does this problem matter?
- Background: Brief literature/context
- Contributions: What's new or validated?

### 3. Problem Specification (use problem_specification skill)
- Formal definition
- Inputs, outputs, constraints
- Invariants and contracts

### 4. Theoretical Analysis (use algorithmic_analysis skill)
- Algorithm description
- Complexity analysis
- Correctness argument
- Design alternatives (use comparative_complexity skill)

### 5. Implementation
- Design decisions (use systems_design_patterns skill)
- Language-specific considerations (use language_comparative_runtime skill)
- Code structure (high-level, not full listing)

### 6. Experimental Evaluation
- Setup (use benchmark_design skill)
- Results (use reporting_visualization skill)
- Analysis (use performance_interpretation skill)

### 7. Discussion
- Interpretation of results
- Theoretical vs empirical comparison
- Limitations and threats to validity

### 8. Related Work
- Prior art (brief, 2-3 key references)
- How this work differs/extends

### 9. Conclusions
- Summary
- Key takeaways
- Future directions (use pedagogical_reflection skill)

## Writing Style

### Tone
- Formal but readable
- Active voice preferred: "We measured" not "It was measured"
- Present tense for established facts, past for experiments
- Example: "The heap maintains O(log k) complexity. We observed 60M ops/sec."

### Precision
- Quantify claims: Not "faster" but "38% faster"
- Cite sources: Algorithm from [Knuth TAOCP Vol 3, Section 5.4.1]
- Distinguish prediction from measurement: "Model predicts 60ns; we observed 58ns (±4ns)"

### Clarity
- One idea per paragraph
- Signpost structure: "First, we analyze... Next, we implement... Finally, we measure..."
- Define before using: "K-way merge (combining k sorted sequences into one)"

### Separation of Concerns
- Theory section: No implementation details, no measurements
- Implementation section: References theory, no measurements
- Evaluation section: References both, focuses on observations

## Example Section: Theoretical Analysis

```markdown
## Theoretical Analysis

### Algorithm Description

We employ a heap-based k-way merge. Given k sorted iterators, we maintain a min-heap of size at most k, where each entry contains the current minimum element from one iterator.

**Invariant**: The heap root contains the global minimum among all unconsumed elements.

### Complexity Analysis

**Time**: Each element passes through the heap exactly once. Heap insertion and extraction require O(log k) comparisons. With N total elements, overall complexity is O(N log k).

**Space**: The heap stores at most k elements, requiring O(k) auxiliary space. Input iterators are not counted toward space complexity.

**Proof of Correctness**:
1. Initially, the heap contains the first element from each of k iterators.
2. Loop invariant: At iteration i, the heap contains the minimum unconsumed element from each non-exhausted iterator.
3. Extracting the heap minimum yields the global minimum.
4. Advancing the corresponding iterator and reinserting maintains the invariant.
5. Termination: All iterators exhausted implies heap empty.

Therefore, the algorithm produces elements in sorted order.

### Alternative Designs

We considered three alternatives (Table 1):

| Design | Time | Space | Best For |
|--------|------|-------|----------|
| Min-Heap | O(N log k) | O(k) | General (k > 8) |
| Tournament Tree | O(N log k) | O(k) | Stable merge |
| Linear Scan | O(Nk) | O(1) | Small k (k ≤ 8) |

The heap approach balances time complexity with simplicity. For k ≤ 8, linear scan may be faster due to cache locality (see Section 6.2).
```

## Example Section: Experimental Evaluation

```markdown
## Experimental Evaluation

### Setup

We evaluated implementations in Java, C++, and Rust on an Apple M2 (3.5 GHz, 192KB L1, 16MB L2). Workloads consisted of k sorted iterators, each containing 10K uniformly distributed elements (range [0, 100K), seed 42).

Benchmarks used JMH 1.35 (Java), Google Benchmark (C++), and Criterion (Rust), each with 10 warmup and 50 measurement iterations. Full configuration details appear in Appendix A.

### Results

Table 2 presents throughput (elements/sec) across languages and k values:

| k | Java | C++ | Rust |
|---|------|-----|------|
| 10 | 52M | 68M | 70M |
| 100 | 45M | 60M | 62M |
| 1000 | 38M | 51M | 52M |

All implementations scale as O(N log k) (Figure 3). Rust achieves 38% higher throughput than Java for k=100, attributed to zero-cost abstractions and eliminated virtual dispatch.

### Analysis

The 60-cycle-per-element cost predicted by our microarchitectural model (Section 4.3) closely matches observed performance: at 3.5 GHz, 60 cycles ≈ 17ns/element = 58M elements/sec, within 5% of measured C++/Rust performance (60-62M/sec).

Java's lower throughput (45M/sec ≈ 78 cycles/element) reflects megamorphic dispatch overhead (~10 cycles) and young-generation allocation (~8 cycles).
```

## Cross-Skill Integration

Requires: All analysis, implementation, and benchmarking skills
Uses: temporal_style_adapter for voice consistency
Feeds into: Final documentation/paper output
