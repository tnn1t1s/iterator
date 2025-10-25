---
name: multi_language_codegen
description: Generates idiomatic, compiling code in Java, C++, and Rust. Handles language-specific syntax, idioms, and memory models. Use for implementing algorithms across all three languages.
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Multi-Language Code Generation

## Purpose

Generate correct, idiomatic implementations of algorithms in Java, C++, and Rust, respecting each language's conventions and memory models.

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

## Instructions

For each language:

1. **Java Implementation**
   - Use standard library types: `Iterator<T>`, `PriorityQueue<T>`, `Comparator<T>`
   - Implement `Iterator<T>` interface with `hasNext()`, `next()`, `remove()` optional
   - Handle generics properly with type erasure constraints
   - Document with Javadoc
   - Package structure: `com.example.iterators`

2. **C++ Implementation**
   - Follow STL iterator concepts: input, forward, bidirectional, random-access
   - Use templates for generic code: `template<typename T, typename Iter>`
   - RAII for resource management, no manual cleanup in destructors
   - Const-correctness: `const T& operator*() const`
   - Header/implementation split: `.hpp` and `.cpp`
   - Namespace: `iterator_research`

3. **Rust Implementation**
   - Implement `Iterator` trait with `next(&mut self) -> Option<T>`
   - Use lifetime parameters when needed: `'a`
   - Leverage zero-cost abstractions: iterator adapters
   - Follow ownership rules strictly
   - Module structure: `mod iterators`
   - Documentation with `///` and examples

## Code Structure Templates

### Java Iterator Pattern
```java
public class MergeIterator<T extends Comparable<T>> implements Iterator<T> {
    private PriorityQueue<Entry<T>> heap;

    public MergeIterator(List<Iterator<T>> iterators) {
        // Initialize heap
    }

    @Override
    public boolean hasNext() { /* ... */ }

    @Override
    public T next() { /* ... */ }
}
```

### C++ Iterator Pattern
```cpp
template<typename T, typename Iter>
class merge_iterator {
    using value_type = T;
    using reference = const T&;

    std::priority_queue<entry<T, Iter>> heap_;

public:
    explicit merge_iterator(std::vector<Iter> iterators);

    bool has_next() const;
    T next();
};
```

### Rust Iterator Pattern
```rust
pub struct MergeIterator<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    heap: BinaryHeap<Entry<T, I>>,
}

impl<T, I> Iterator for MergeIterator<T, I>
where
    T: Ord,
    I: Iterator<Item = T>,
{
    type Item = T;

    fn next(&mut self) -> Option<T> { /* ... */ }
}
```

## Multi-Variant Implementation Pattern

Structure code to support multiple algorithm implementations with same interface:

### Java Package Structure
```
com/research/iterator/
├── CollatingIterator.java           # Interface or factory
├── LinearScanIterator.java          # O(Nk) naive baseline
├── HeapBasedIterator.java           # O(N log k) standard (binary heap)
├── LoserTreeIterator.java           # O(N log k) optimized (selected)
└── Example.java                     # Demonstrates all variants
```

### Naming Convention
- **Naive**: `LinearScanIterator`, `NaiveIterator`
- **Standard**: `HeapBasedIterator`, `StandardIterator`
- **Optimized**: `LoserTreeIterator`, `TournamentTreeIterator`

### Common Interface
All variants implement same interface for benchmarking:
```java
public interface CollatingIterator<T> extends Iterator<T> {
    // Standard Iterator interface
    boolean hasNext();
    T next();

    // Optional: expose metrics for benchmarking
    default long getComparisonCount() { return -1; }
}
```

### Factory Pattern
```java
public class CollatingIterators {
    public static <T extends Comparable<T>> Iterator<T> linearScan(List<Iterator<T>> iters) {
        return new LinearScanIterator<>(iters);
    }

    public static <T extends Comparable<T>> Iterator<T> heapBased(List<Iterator<T>> iters) {
        return new HeapBasedIterator<>(iters);
    }

    public static <T extends Comparable<T>> Iterator<T> loserTree(List<Iterator<T>> iters) {
        return new LoserTreeIterator<>(iters);
    }
}
```

## Language-Specific Idioms

### Java
- Null checks: `Objects.requireNonNull()`
- Exceptions: `NoSuchElementException` for exhausted iterator
- Generics: Use bounded types `<T extends Comparable<? super T>>`
- Collections: Prefer `ArrayList`, `PriorityQueue` from stdlib

### C++
- Move semantics: `std::move()` for expensive objects
- Smart pointers: `std::unique_ptr`, avoid raw `new`/`delete`
- Const: Mark everything `const` that doesn't mutate
- Algorithms: Use `<algorithm>` library when possible

### Rust
- Ownership: Consume iterators, return owned values
- Borrowing: Use `&mut self` in `next()`
- Options: `Option<T>` for nullable, never panic in iterators
- Derive: `#[derive(Debug, Clone)]` for common traits

## Cross-Skill Integration

Requires: problem_specification, algorithmic_analysis, language_comparative_runtime
Feeds into: unit_test_generation, benchmark_design
