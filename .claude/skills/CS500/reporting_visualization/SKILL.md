---
name: reporting_visualization
description: Formats results into tables, charts, and Markdown summaries for reproducibility and clarity. Use for creating publication-ready performance reports.
allowed-tools: Read, Write, Edit
---

# Reporting & Visualization

## Purpose

Present benchmark results and analysis in clear, reproducible format suitable for academic or professional audiences.

## Report Structure

### 1. Executive Summary
- One paragraph: What was tested, key findings
- Example: "Heap-based merge achieves 60M elements/sec on M2, 2.3× faster than Java due to zero-cost abstractions"

### 2. Experimental Setup
- Hardware: CPU model, clock, cache sizes
- Software: Compiler/JVM version, flags
- Workload: Input sizes, distributions
- Reproducibility: Seed, trial count

### 3. Results
- Tables with absolute numbers
- Charts comparing implementations
- Statistical significance noted

### 4. Analysis
- Interpret results (use performance_interpretation skill)
- Explain bottlenecks
- Compare to theoretical predictions

### 5. Conclusions
- Summary of findings
- Recommendations
- Future work

## Table Formats

### Performance Comparison
```markdown
| Language | k | Elements | Throughput | Latency | Memory |
|----------|---|----------|------------|---------|--------|
| Java | 100 | 10M | 45M/sec | 22ns | 1.6KB |
| C++ | 100 | 10M | 60M/sec | 17ns | 1.6KB |
| Rust | 100 | 10M | 62M/sec | 16ns | 1.6KB |
```

### Scaling Analysis
```markdown
| k | Predicted | Observed | Ratio |
|---|-----------|----------|-------|
| 10 | 50M/sec | 48M/sec | 0.96 |
| 100 | 35M/sec | 37M/sec | 1.06 |
| 1000 | 25M/sec | 23M/sec | 0.92 |
```

## Chart Descriptions (Text-Based)

### Throughput vs k
```
Throughput (M elements/sec)
80 ┤             Linear (k≤8)
70 ┤         ●
60 ┤       ●   ○ C++/Rust Heap
50 ┤     ●       ○
40 ┤   ●           ○ Java Heap
30 ┤ ●               ○
   └─────────────────────────
   10  50  100      1000  k
```

### Latency Distribution
```
Count
100 ┤     ╭─╮
 80 ┤   ╭─╯ ╰─╮
 60 ┤  ╭╯     ╰─╮
 40 ┤ ╭╯        ╰─╮
 20 ┤╭╯           ╰──
    └───────────────
    10  20  30  40ns
```

## Reproducibility Section

Always include:
```markdown
## Reproducibility

**Hardware**:
- CPU: Apple M2, 3.5 GHz, 192KB L1, 16MB L2
- RAM: 16GB unified memory

**Software**:
- Java: OpenJDK 17.0.5, JMH 1.35
- C++: Clang 15.0, -O3 -march=native
- Rust: 1.70.0, --release

**Workload**:
- k iterators, 10K elements each
- Elements: uniform random [0, 100K), seed=42
- Trials: 10 warmup + 50 measurement

**Source**: github.com/user/repo/commit/abc123
```

## Key Findings Format

Use bullet points with numbers:
- Rust 38% faster than Java for k=100 (62M vs 45M elements/sec)
- Linear scan optimal for k ≤ 8 (cache-friendly)
- Heap bottleneck: branches (60%) > memory (30%) > CPU (10%)

## Cross-Skill Integration

Requires: benchmark_design (data), performance_interpretation (analysis)
Feeds into: technical_exposition
