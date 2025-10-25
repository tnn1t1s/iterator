---
name: self_consistency_checker
description: Ensures cross-section consistency by verifying theoretical claims match code, benchmarks reflect analysis, and report accurately cites all stages. Use for final validation.
allowed-tools: Read, Grep, Glob
---

# Self-Consistency Checker

## Purpose

Validate that all artifacts (spec, theory, code, benchmarks, report) are mutually consistent with no contradictions.

## Consistency Checks

### 1. Spec ↔ Theory
**Verify**:
- Complexity analysis uses correct input constraints from spec
- Algorithm satisfies spec invariants
- Postconditions guaranteed by algorithm

**Example Check**:
```
Spec says: "Output sorted if inputs sorted"
Analysis proves: "Heap extracts elements in order"
✓ Consistent
```

### 2. Theory ↔ Code
**Verify**:
- Chosen data structure (e.g., heap) appears in code
- Algorithm complexity matches implementation (O(log k) operations)
- Code comments reference theoretical analysis

**Example Check**:
```
Analysis: "Array-based min-heap"
Java code: Uses PriorityQueue (array-backed) ✓
C++ code: Uses std::priority_queue (array-backed) ✓
Rust code: Uses BinaryHeap (array-backed) ✓
```

### 3. Code ↔ Tests
**Verify**:
- Tests validate all spec invariants
- Edge cases from spec appear in tests
- Test names reference specification

**Example Check**:
```
Spec invariant: "Output size = sum of input sizes"
Test: testOutputSizeMatchesInputSum() ✓
Spec edge case: "Empty iterators"
Test: testEmptyIterators() ✓
```

### 4. Code ↔ Benchmarks
**Verify**:
- Benchmarks measure the implementations that exist
- File references in benchmark setup match actual code locations
- Benchmark configurations reflect code capabilities

**Example Check**:
```
Benchmark imports: "com.example.MergeIterator"
Code exists: src/main/java/.../MergeIterator.java ✓
Benchmark tests: k=10,100,1000
Code supports: Arbitrary k ✓
```

### 5. Theory ↔ Benchmarks
**Verify**:
- Measured complexity matches theoretical prediction (within tolerance)
- Bottleneck identified in analysis appears in measurement
- Crossover points (if predicted) validated

**Example Check**:
```
Theory: O(N log k), predicts 60 cycles/element on M2
Measurement: 58 cycles/element (±4)
Deviation: 3.4% < 20% threshold ✓

Theory: Bottleneck is branches (60%)
Measurement: Profile shows 58% branch cost ✓
```

### 6. Benchmarks ↔ Report
**Verify**:
- All benchmark results cited in report are real (not fabricated)
- Numbers in tables match raw benchmark output
- Analysis claims supported by measurements

**Example Check**:
```
Report Table 2: "Rust: 62M elements/sec at k=100"
Benchmark log: "rust_k100: 62.3M ops/sec" ✓

Report: "Rust 38% faster than Java"
Calculation: (62-45)/45 = 37.8% ≈ 38% ✓
```

### 7. Report Internal Consistency
**Verify**:
- Abstract matches conclusions
- Cross-references valid (Section X exists)
- Figures/tables referenced in text
- Terminology consistent (don't switch between "iterator" and "stream")

## Checker Output

### Report Format

```markdown
## Consistency Check Report

**Generated**: 2024-01-15T12:00:00Z
**Pipeline**: k-way-merge-research

### Summary
- Total checks: 47
- Passed: 45 ✓
- Warnings: 2 ⚠
- Failures: 0 ✗

### Warnings

⚠ **Theory ↔ Benchmark** (Minor)
- Predicted 60 cycles/elem, measured 58 cycles
- Deviation: 3.4% (within tolerance, but model slightly pessimistic)
- Action: Document model assumptions in report

⚠ **Report Cross-Refs** (Minor)
- Section 6.3 referenced but titled "6.2" in document
- Action: Fix section numbering

### Passed Checks
✓ Spec invariants → Code implementation (3/3)
✓ Theory complexity → Code data structures (3/3 languages)
✓ Code → Tests coverage (invariants: 5/5, edges: 8/8)
✓ Benchmarks → Report citations (all 9 results cited correctly)
✓ Report abstract → Conclusions (consistent)

### Details

**Check 1: Spec Invariant "Heap Property"**
- Specified in: docs/spec.md:12
- Implemented in: Java:23, C++:34, Rust:28
- Tested in: Java test:45, C++ test:67, Rust test:52
- Status: ✓ Verified across all implementations

**Check 2: Complexity O(N log k)**
- Analyzed in: docs/analysis.md:23
- Realized in: PriorityQueue (Java), priority_queue (C++), BinaryHeap (Rust)
- Measured: Java 78 cycles (log 100 ≈ 6.6 → ~12 cycles/op ✓)
- Status: ✓ Matches theoretical prediction

[... 45 more checks ...]
```

## Automated Checks

### File Existence
```bash
# Verify all referenced files exist
grep -r 'src/.*\.java' report.md | check_files_exist
```

### Number Consistency
```python
# Extract numbers from benchmark logs and report
bench_nums = extract_numbers("benchmark_results.log")
report_nums = extract_numbers("report.md")
assert all(n in report_nums for n in bench_nums)
```

### Cross-Reference Validation
```python
# Verify section references
sections = extract_sections("report.md")
references = extract_references("report.md")
assert all(ref in sections for ref in references)
```

## Usage in Pipeline

Invoked as final stage:
1. Read all artifacts (spec, analysis, code, benchmarks, report)
2. Extract claims, data, references
3. Cross-check consistency
4. Generate report
5. If failures: Block publication, require fixes
6. If warnings: Log but allow publication

## Tolerance Thresholds

- **Theory vs Measurement**: ±20% acceptable (systems variability)
- **Report vs Raw Data**: ±1% acceptable (rounding)
- **Cross-references**: 0% tolerance (must be exact)

## Cross-Skill Integration

**Requires**: All pipeline artifacts
**Uses**: skill_context_cache for cross-references
**Feeds into**: Final approval for publication
