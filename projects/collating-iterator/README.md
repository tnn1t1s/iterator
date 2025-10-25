# Collating Iterator

**Generated**: 2025-10-25
**Problem**: K-way merge of sorted iterators with heap-based collation

## Structure

- `01-specification/` - Formal problem specification (LaTeX)
- `02-analysis/` - Algorithmic analysis and complexity proofs (LaTeX)
- `03-design/` - Design decisions and trade-offs (LaTeX)
- `04-implementation/` - Multi-language implementations
- `05-testing/` - Test strategies and results
- `06-benchmarks/` - Performance measurement framework
- `07-report/` - Complete technical report (LaTeX)
- `08-validation/` - Consistency verification

## Build Instructions

### Java
```bash
cd 04-implementation/java
./gradlew build test
```

### C++
```bash
cd 04-implementation/cpp
mkdir build && cd build
cmake ..
make
ctest
```

### Rust
```bash
cd 04-implementation/rust
cargo build
cargo test
```

## LaTeX Compilation

```bash
cd 07-report
pdflatex technical-report.tex
```

## Quick Start

**📖 View Documentation**: Open `INDEX.html` in your browser for easy navigation of all artifacts.

All LaTeX documents have been converted to HTML for easy viewing without PDF tools.

## Overview

This project implements and analyzes CollatingIterator, an interface for merging multiple sorted Iterator<T> streams into a single sorted output stream. The implementation uses a binary min-heap to achieve O(N log k) time complexity where N is the total number of elements and k is the number of input iterators.

Key contributions:
- Formal correctness proof with loop invariants
- Comparative analysis of heap vs tournament tree vs linear scan
- Implementations in Java, C++, and Rust
- Comprehensive benchmarking framework
- CS500-level technical exposition
