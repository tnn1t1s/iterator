---
name: performance_interpretation
description: Interprets benchmark results with systems-engineer realism including cache behavior, allocator overhead, and concurrency bottlenecks. Produces plausible synthetic metrics consistent with analysis.
allowed-tools: Read, Write, Grep, Glob, WebFetch, WebSearch
---

# Performance Interpretation

## Purpose

Analyze benchmark results through systems-level lens, explaining observed performance via microarchitectural reasoning.

## Interpretation Framework

### 1. Sanity Check
- Does result match Big-O prediction?
- Are absolute numbers plausible for hardware?
- Example: 2.4 GHz CPU, O(log k) ⟹ ~100-200 cycles reasonable

### 2. Identify Bottleneck
- **Memory-bound**: Throughput scales with cache hierarchy
- **CPU-bound**: Throughput scales with clock speed
- **Branch-bound**: Throughput improves with better prediction

### 3. Explain Variance
- Small stddev (< 5%): Consistent, deterministic
- Large stddev (> 20%): GC pauses, OS interference, thermal throttling

### 4. Compare to Model
- Microarchitectural model predicts X cycles
- Measurement shows Y ns
- Difference explained by: [cache behavior, prefetch, ILP]

## Interpretation Patterns

### Pattern: Heap-Based Merge

**Observation**: 50M elements/sec on M2, k=100

**Analysis**:
```
Predicted: 60 cycles/element (see microarchitectural_modeling)
Clock: 3.5 GHz = 3.5B cycles/sec
Expected: 3.5B / 60 = 58M elements/sec
Observed: 50M elements/sec

Difference: 14% slower than model
Likely causes:
- Iterator dispatch overhead (~10 cycles unmodeled)
- Background tasks (OS, spotlight, etc.)
- Thermal throttling (sustained load)

Conclusion: Within expected variance, model validated
```

### Pattern: Iterator Chain (Java)

**Observation**: 10M elements/sec for 5-stage chain

**Analysis**:
```
Per element cost: 3.5 GHz / 10M = 350 cycles

Breakdown:
- Actual work (compare, transform): ~10 cycles
- Virtual dispatch (5 × 20 cycles megamorphic): ~100 cycles
- GC (young gen allocation): ~200 cycles
- CPU overhead: ~40 cycles

Total: 350 cycles ✓

Bottleneck: GC allocation (200/350 = 57%)
Mitigation: Eager evaluation (reduce allocation) or manual fusion
```

### Pattern: Crossover Validation

**Observation**: Linear scan faster than heap for k ≤ 12

**Analysis**:
```
Linear scan: 6 cycles/element × 12 = 72 cycles total
Heap: 12 cycles (heap op) + 36 cycles (branches) = 48 cycles/element

Wait, heap should be slower than observed!

Re-measure with larger dataset:
- Small dataset (1K elements): Linear wins (cache fits L1)
- Large dataset (10M elements): Heap wins (amortized)

Conclusion: Crossover depends on dataset size, not just k
```

## Synthetic Metrics (When Real Measurement Unavailable)

If cannot run benchmarks, generate plausible synthetic data:

1. Use microarchitectural model
2. Add realistic noise: ±10% stddev
3. Document as "synthetic (modeled)" clearly

**Example**:
```
Heap-based merge (P4 Northwood 2.4 GHz, synthetic):
- Throughput: 26M elements/sec (±2M)
- Latency: 38ns/element (±3ns)
- Model basis: 90 cycles × 2.4 GHz

Note: Synthetic metric based on microarchitectural modeling
```

## Comparison Table Template

| Implementation | Throughput | Latency | Memory | Notes |
|----------------|------------|---------|--------|-------|
| Java (heap) | 45M/sec | 22ns | 1.6KB | GC pauses |
| C++ (heap) | 60M/sec | 17ns | 1.6KB | Zero overhead |
| Rust (heap) | 62M/sec | 16ns | 1.6KB | Optimal codegen |
| Java (linear, k=8) | 80M/sec | 13ns | 64B | Cache-friendly |

## Era-Appropriate Voice

"The numbers look reasonable; P4 branch mispredicts cost ~20 cycles, and we're seeing 70 cycles overhead per element with 7 comparisons. That's 3-4 mispredicts per operation—about what you'd expect for data-dependent branches. The heap fits in L1, so memory's not the bottleneck. Could try branchless heap to shave 50% off that branch cost."

## Cross-Skill Integration

Requires: benchmark_design (measurements), microarchitectural_modeling (predictions)
Feeds into: reporting_visualization, technical_exposition
