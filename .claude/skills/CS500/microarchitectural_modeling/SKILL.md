---
name: microarchitectural_modeling
description: Models cache lines, branch prediction, allocation patterns, and lock contention. Converts algorithmic reasoning into CPU-era reality spanning P4 to Xeon to M4. Use for performance modeling and bottleneck prediction.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Microarchitectural Modeling

## Purpose

Translate algorithmic designs into concrete microarchitectural behavior, predicting performance on real hardware from P4 era through modern architectures.

## Instructions

When activated:

1. **Identify Target Architecture**
   - Era: P4 Northwood (2001-2003), Early Xeon, Athlon XP
   - Modern: M1/M2/M3/M4, Zen 3/4, Intel Alder Lake
   - Specify cache hierarchy, pipeline depth, execution units

2. **Model Memory Hierarchy**
   - Cache line size (typically 64 bytes)
   - Cache capacity (L1/L2/L3)
   - Miss latency at each level
   - Prefetch behavior (sequential, stride)

3. **Model Execution Pipeline**
   - Branch misprediction penalty
   - Instruction-level parallelism (ILP)
   - Out-of-order window size
   - Execution port contention

4. **Model Memory Operations**
   - Allocation cost (malloc/new)
   - GC overhead (if applicable)
   - False sharing penalties
   - Memory bandwidth limits

5. **Predict Bottlenecks**
   - Memory-bound vs CPU-bound
   - Cache miss rates
   - Branch misprediction rates
   - TLB thrashing (large datasets)

## Architecture Profiles

### P4 Northwood (2001-2003)
- L1: 8 KB / 2 cycles, L2: 512 KB / 7 cycles
- Branch mispredict: 20 cycles (deep pipeline)
- Weak prefetcher, ~2 GB/s bandwidth

### Apple M1/M2/M3/M4 (2020-2024)
- L1: 128-192 KB / 3 cycles (huge)
- L2: 12-16 MB / 15 cycles
- Branch mispredict: ~18 cycles
- 100-400 GB/s unified memory bandwidth

## Cross-Skill Integration

Requires: algorithmic_analysis, systems_design_patterns
Feeds into: performance_interpretation, technical_exposition
