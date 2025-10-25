---
name: unit_test_generation
description: Writes parameterized, edge-aware tests using JUnit, Catch2, and Rust test macros. Validates iterator contracts, edge cases, and fuzz inputs. Use for comprehensive test coverage.
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Unit Test Generation

## Purpose

Generate comprehensive test suites validating correctness, edge cases, and iterator contracts across Java, C++, and Rust.

## Test Strategy

1. **Contract Tests**: Verify iterator protocol (hasNext/next consistency)
2. **Correctness Tests**: Validate output matches expected for known inputs
3. **Edge Cases**: Empty, single element, duplicates, large inputs
4. **Property Tests**: Invariants hold for random inputs (fuzzing)
5. **Performance Tests**: Sanity check (not benchmarks, just "fast enough")

## Framework Usage

### Java (JUnit 5)
```java
@ParameterizedTest
@MethodSource("provideTestCases")
void testMerge(List<List<Integer>> inputs, List<Integer> expected) {
    // Test implementation
}

@Test
void testEmptyIterators() {
    // Edge case
}
```

### C++ (Catch2)
```cpp
TEST_CASE("Merge iterator combines sorted sequences", "[merge]") {
    SECTION("empty input") {
        // Edge case
    }

    SECTION("single iterator") {
        // Edge case
    }
}
```

### Rust (Built-in + proptest)
```rust
#[test]
fn merge_empty_iterators() {
    // Edge case
}

#[test]
fn merge_maintains_order() {
    // Correctness
}

proptest! {
    #[test]
    fn merge_property(inputs: Vec<Vec<i32>>) {
        // Property-based test
    }
}
```

## Test Categories

### 1. Iterator Contract Tests
- `hasNext()` true ⟹ `next()` succeeds
- `hasNext()` false ⟹ `next()` throws/panics
- Multiple `hasNext()` calls without `next()` consistent

### 2. Correctness Tests
- Merge [1,3,5] + [2,4,6] → [1,2,3,4,5,6]
- Flat map [1,2] → [[10,11], [20,21]] → [10,11,20,21]
- Filter even [1,2,3,4] → [2,4]

### 3. Edge Cases
- Empty input: [] → []
- Single element: [1] → [1]
- All duplicates: [5,5,5] → [5,5,5]
- Large inputs: 1M elements

### 4. Property Tests (Fuzzing)
- Output size = sum of input sizes
- Output sorted if inputs sorted
- All input elements appear in output

## Cross-Skill Integration

Requires: multi_language_codegen (code to test)
Feeds into: self_consistency_checker (validation)
