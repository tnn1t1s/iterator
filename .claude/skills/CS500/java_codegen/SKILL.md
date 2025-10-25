---
name: java_codegen
description: Generates idiomatic, compiling Java code. Handles Java-specific syntax, generics, and stdlib patterns. Implements multiple algorithm variants with proper file organization.
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Java Code Generation

## Purpose

Generate correct, idiomatic Java implementations of algorithms, respecting Java conventions, generics, and professional file organization.

## Critical: File Organization & Separation of Concerns

**CRITICAL**: Professional candidates organize code cleanly with clear separation.

**Requirements**:
1. **One class per file** - Never mix multiple implementations in one file
2. **Descriptive filenames** - Name matches class name exactly
3. **Separate examples** - Dedicated example/demo files for each variant
4. **Logical grouping** - Related functionality together, unrelated apart

**Anti-patterns to AVOID**:
- ❌ Multiple algorithm implementations in one file
- ❌ Generic names like `Example.java` for all demonstrations
- ❌ Mixing interface definitions with implementations
- ❌ Bundling unrelated functionality

**Best practices**:
- ✅ `LinearScanIterator.java` contains only LinearScanIterator class
- ✅ `LinearScanExample.java` demonstrates LinearScanIterator usage
- ✅ `HeapBasedExample.java` demonstrates HeapBasedIterator usage
- ✅ `ComparisonDemo.java` compares all variants side-by-side
- ✅ Clear naming reveals purpose without reading code

## Critical: Implement Multiple Variants for Comparison

**IMPORTANT**: Implement multiple algorithm variants, not just the "optimal" one selected in Stage 3.

**Why**:
- Empirical validation of theoretical analysis
- Benchmark comparison baselines
- Demonstrates understanding of trade-offs
- Validates design decision with real data

**Minimum Implementation Set**:
1. **Naive baseline** - O(N²) or O(Nk) approach for comparison
2. **Standard approach** - Common textbook solution (e.g., binary heap)
3. **Selected optimal** - Algorithm chosen in Stage 3 (e.g., loser tree)

**Benefits**:
- Stage 6 benchmarks can measure actual performance differences
- Validates constant factor analysis from Stage 3
- Identifies crossover points (e.g., when naive wins for small k)
- Shows candidate can implement multiple approaches, not just one

## Java Implementation Guidelines

- Use standard library types: `Iterator<T>`, `PriorityQueue<T>`, `Comparator<T>`
- Implement `Iterator<T>` interface with `hasNext()`, `next()`, `remove()` optional
- Handle generics properly with type erasure constraints
- Document with Javadoc
- Package structure: `com.research.iterator`
- Null checks: `Objects.requireNonNull()`
- Exceptions: `NoSuchElementException` for exhausted iterator
- Generics: Use bounded types `<T extends Comparable<? super T>>`
- Collections: Prefer `ArrayList`, `PriorityQueue` from stdlib

## Multi-Variant Implementation Pattern

Structure code to support multiple algorithm implementations with same interface:

### Java Package Structure
```
com/research/iterator/
├── LinearScanIterator.java          # O(Nk) naive baseline
├── LinearScanExample.java           # Demo LinearScanIterator
├── HeapBasedIterator.java           # O(N log k) standard (binary heap)
├── HeapBasedExample.java            # Demo HeapBasedIterator
├── LoserTreeIterator.java           # O(N log k) optimized (selected)
├── LoserTreeExample.java            # Demo LoserTreeIterator
└── ComparisonDemo.java              # Compare all variants side-by-side
```

### Naming Convention
- **Naive**: `LinearScanIterator`, `NaiveIterator`
- **Standard**: `HeapBasedIterator`, `StandardIterator`
- **Optimized**: `LoserTreeIterator`, `TournamentTreeIterator`
- **Examples**: `<Algorithm>Example.java` for individual demos
- **Comparison**: `ComparisonDemo.java` for side-by-side comparison

### Iterator Pattern Template
```java
public class MergeIterator<T extends Comparable<? super T>> implements Iterator<T> {
    private PriorityQueue<Entry<T>> heap;

    public MergeIterator(List<? extends Iterator<T>> iterators) {
        // Initialize data structure
    }

    @Override
    public boolean hasNext() { /* ... */ }

    @Override
    public T next() { /* ... */ }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove() not supported");
    }
}
```

### Example File Template
```java
package com.research.iterator;

import java.util.*;

/**
 * Demonstrates <AlgorithmName> usage and edge cases.
 */
public class AlgorithmNameExample {

    public static void main(String[] args) {
        demonstrateBasicUsage();
        demonstrateEdgeCases();
    }

    private static void demonstrateBasicUsage() {
        // Simple example with clear expected output
    }

    private static void demonstrateEdgeCases() {
        // Empty iterators, single iterator, unequal lengths
    }
}
```

### Comparison Demo Template
```java
package com.research.iterator;

import java.util.*;

/**
 * Compares LinearScanIterator, HeapBasedIterator, and LoserTreeIterator
 * on same input to verify correctness and demonstrate usage.
 */
public class ComparisonDemo {

    public static void main(String[] args) {
        // Prepare input data
        List<List<Integer>> testData = prepareTestData();

        // Run same test with all three variants
        System.out.println("=== Linear Scan (O(Nk)) ===");
        runTest(testData, "linear");

        System.out.println("\n=== Heap Based (O(N log k)) ===");
        runTest(testData, "heap");

        System.out.println("\n=== Loser Tree (O(N log k)) ===");
        runTest(testData, "loser");
    }

    private static void runTest(List<List<Integer>> data, String variant) {
        // Create iterators from data
        // Instantiate appropriate algorithm variant
        // Print results
    }

    private static List<List<Integer>> prepareTestData() {
        // Return test data sets
    }
}
```

## Cross-Skill Integration

Requires: problem_specification, algorithmic_analysis
Feeds into: unit_test_generation, benchmark_design
