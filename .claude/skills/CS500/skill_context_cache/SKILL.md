---
name: skill_context_cache
description: Maintains continuity across pipeline steps, tracking design decisions, measurements, and cross-references. Emulates persistent agentic memory. Use for multi-stage workflows.
allowed-tools: Read, Write, Edit, Grep, Glob
---

# Skill Context Cache

## Purpose

Provide persistent memory across skill activations, ensuring decisions and data from one stage inform later stages.

## Cache Structure

### Stored Information

```yaml
context_cache:
  problem:
    specification: "K-way merge of sorted iterators"
    inputs: "Collection<Iterator<T>> where T: Comparable"
    outputs: "Iterator<T> in sorted order"
    invariants: ["Heap property maintained", "Output ordering preserved"]

  analysis:
    time_complexity: "O(N log k)"
    space_complexity: "O(k)"
    design_choice: "Array-based min-heap"
    rationale: "Cache-friendly, simple, optimal for k>8"

  implementation:
    languages: ["Java", "C++", "Rust"]
    files:
      java: "src/main/java/MergeIterator.java"
      cpp: "src/merge_iterator.hpp"
      rust: "src/lib.rs"
    status: "All compile, tests pass"

  benchmarks:
    platform: "Apple M2, 3.5 GHz"
    results:
      java_k100: "45M elements/sec"
      cpp_k100: "60M elements/sec"
      rust_k100: "62M elements/sec"

  cross_references:
    spec_to_code:
      - "Invariant 'heap property' verified in unit tests (line 45)"
      - "O(log k) complexity achieved via PriorityQueue (Java line 23)"
    theory_to_measurement:
      - "Predicted 60 cycles/elem, measured 58 cycles (Rust)"
      - "Model error: 3.4% (within tolerance)"
```

## Operations

### Store
```
store(key, value)
  → Saves information for later retrieval
  → Example: store("design_choice", "heap")
```

### Retrieve
```
retrieve(key)
  → Fetches previously stored information
  → Example: retrieve("design_choice") → "heap"
```

### Link
```
link(source, target, relationship)
  → Creates cross-reference
  → Example: link("spec_invariant_1", "test_line_45", "verified_by")
```

### Validate
```
validate()
  → Checks for orphaned references
  → Ensures all links point to valid targets
```

## Usage Patterns

### Pattern 1: Design Decision Propagation
```
Stage 2 (Analysis): store("data_structure", "array_heap")
Stage 4 (Implementation): data_struct = retrieve("data_structure")
                          # Use array_heap in code generation
```

### Pattern 2: Cross-Referencing
```
Stage 1 (Spec): store("invariant_1", "Heap property maintained")
Stage 5 (Testing): link("invariant_1", "test_heap_property", "tested_by")
Stage 7 (Report): reference = retrieve_links("invariant_1")
                  # "Invariant verified by test_heap_property"
```

### Pattern 3: Feedback Loop
```
Stage 6 (Benchmarking): store("unexpected_result", "k=12 crossover")
Stage 2 (Re-analysis): issue = retrieve("unexpected_result")
                       # Investigate why prediction was wrong
```

## Cache Lifecycle

### Initialization
- Create empty cache at pipeline start
- Load any prior cache if resuming

### Active Phase
- Skills store/retrieve as needed
- Cache grows with each stage

### Finalization
- Validate all links
- Generate cross-reference report
- Persist cache for future runs

### Persistence
- Save to `.cache/context.yaml`
- Allows resume after interruption
- Enables incremental research

## Integration with Pipeline

### Stage Transitions
```
problem_specification → Store spec
algorithmic_analysis → Retrieve spec, store analysis
systems_design_patterns → Retrieve analysis, store design
multi_language_codegen → Retrieve design, store code locations
benchmark_design → Retrieve code locations, store results
technical_exposition → Retrieve all, generate report
```

### Consistency Checking
```
self_consistency_checker uses cache to verify:
  - Analysis references correct spec
  - Code implements chosen design
  - Benchmarks measure implemented code
  - Report cites correct results
```

## Example Cache File

```yaml
# .cache/context.yaml
generated: 2024-01-15T10:30:00Z
pipeline: "k-way-merge-research"

problem:
  spec_file: "docs/specification.md"
  key_constraints:
    - "Iterators pre-sorted"
    - "K ≥ 0"

analysis:
  complexity: "O(N log k) time, O(k) space"
  alternatives_considered: 3
  chosen: "min_heap"

implementations:
  java:
    file: "src/main/java/MergeIterator.java"
    lines: 87
    tests: "src/test/java/MergeIteratorTest.java"
  cpp:
    file: "src/merge_iterator.hpp"
    lines: 62
    tests: "test/test_merge.cpp"
  rust:
    file: "src/lib.rs"
    lines: 54
    tests: "tests/merge_test.rs"

benchmarks:
  timestamp: 2024-01-15T11:45:00Z
  platform: "Apple M2"
  java_throughput: 45000000  # elements/sec
  cpp_throughput: 60000000
  rust_throughput: 62000000

cross_refs:
  - source: "spec:invariant_heap"
    target: "code:java:23"
    type: "implemented_by"
  - source: "analysis:complexity_log_k"
    target: "code:rust:heap.pop()"
    type: "realized_in"
  - source: "benchmark:rust_62M"
    target: "report:section_6.2"
    type: "reported_in"
```

## Cross-Skill Integration

**Used by**: research_to_code_pipeline (primary consumer)
**Populated by**: All skills throughout pipeline
**Verified by**: self_consistency_checker
