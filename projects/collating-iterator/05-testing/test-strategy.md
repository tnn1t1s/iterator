# Test Strategy

## Overview

Comprehensive testing strategy for CollatingIterator across Java, C++, and Rust implementations.

## Test Categories

### 1. Boundary Conditions
- **Empty Input**: No iterators, all empty iterators
- **Single Iterator**: One sorted sequence
- **Single Element**: Iterators with one element each

### 2. Functional Correctness
- **Two Iterators**: Basic merge scenario
- **Multiple Iterators**: k=5, k=10, k=100
- **Sorted Output**: Verify output maintains sorted order
- **Element Conservation**: All input elements present exactly once

### 3. Edge Cases
- **Duplicates**: Elements appearing multiple times within and across iterators
- **Uneven Lengths**: Iterators of varying sizes
- **Mixed Empty**: Some iterators empty, others non-empty
- **Single vs Multiple**: Stress test with k=1 vs k=100

### 4. Data Types
- **Integers**: Standard numeric testing
- **Strings**: Lexicographic ordering
- **Custom Types**: Types with custom Comparable/Ord implementations

### 5. API Contract
- **Null Handling** (Java): NullPointerException for null inputs
- **Iterator Protocol**: hasNext() idempotence, next() exhaustion
- **remove() Unsupported** (Java): UnsupportedOperationException
- **End Iteration**: Proper termination when all inputs exhausted

## Test Matrix

| Test Case              | Java | C++ | Rust | Expected |
|------------------------|------|-----|------|----------|
| Empty collection       | ✓    | ✓   | ✓    | Empty output |
| Single iterator        | ✓    | ✓   | ✓    | Identity |
| Two sorted             | ✓    | ✓   | ✓    | Merged sorted |
| k=5 sorted             | ✓    | ✓   | ✓    | Merged sorted |
| Duplicates             | ✓    | ✓   | ✓    | All preserved, sorted |
| Some empty             | ✓    | ✓   | ✓    | Non-empty merged |
| All empty              | ✓    | ✓   | ✓    | Empty output |
| Single elements        | ✓    | ✓   | ✓    | Sorted |
| Large k (100)          | ✓    | ✓   | ✓    | 1000 elements sorted |
| Strings                | ✓    | ✓   | ✓    | Lexicographic order |
| Uneven lengths         | ✓    | ✓   | ✓    | All merged, sorted |
| Null input (Java)      | ✓    | N/A | N/A  | NPE |
| hasNext() idempotence  | ✓    | N/A | ✓    | Multiple calls safe |
| remove() unsupported   | ✓    | N/A | N/A  | UnsupportedOperationException |

## Coverage Goals

- **Line Coverage**: ≥95%
- **Branch Coverage**: ≥90%
- **Edge Case Coverage**: 100% (all boundary conditions)

## Test Implementation

### Java (JUnit 5)
- Location: `src/test/java/com/iterator/CollatingIteratorTest.java`
- Framework: JUnit 5.10
- Assertions: JUnit assertions
- Total tests: 14

### C++ (GoogleTest)
- Location: `tests/test_collating_iterator.cpp`
- Framework: GoogleTest 1.12.1
- Total tests: 13

### Rust (Built-in)
- Location: `src/lib.rs` (#[cfg(test)] module)
- Framework: cargo test
- Total tests: 11

## Invariant Checks

Each test implicitly verifies:
1. **Sorted Output**: All output elements in non-decreasing order
2. **Conservation**: Output size = sum of input sizes
3. **No Loss**: All input elements present in output
4. **No Duplication**: Elements appear correct number of times

## Failure Scenarios Tested

1. **Empty heap extraction**: Should throw/panic appropriately
2. **Null iterator**: Should reject with NPE (Java)
3. **Improper API usage**: remove() should fail (Java)
4. **Concurrent modification**: Not tested (out of scope for single-threaded)

## Performance Tests

Performance testing moved to Stage 6 (Benchmarking).

## Execution

### Java
```bash
cd 04-implementation/java
./gradlew test
```

### C++
```bash
cd 04-implementation/cpp
mkdir build && cd build
cmake ..
make
ctest --verbose
```

### Rust
```bash
cd 04-implementation/rust
cargo test
```

## Success Criteria

All tests must pass with:
- No failures
- No memory leaks (C++)
- No undefined behavior
- Consistent behavior across languages
