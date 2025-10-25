---
name: language_comparative_runtime
description: Compares runtime semantics across Java, C++, and Rust. Understands GC vs borrow checker vs RAII impacts on iterator implementations. Use when implementing across multiple languages.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Language Comparative Runtime

## Purpose

Understand and explain how Java, C++, and Rust runtime semantics affect iterator design, performance, and safety.

## Language Models

### Java
- **Memory**: GC (generational), young gen fast, old gen expensive
- **Dispatch**: Virtual by default, JIT devirtualization when monomorphic
- **Safety**: Runtime checks, NullPointerException, ClassCastException
- **Iterators**: Interface-based, allocation per wrapper, megamorphic dispatch
- **Performance**: JIT can optimize hot paths, but unpredictable

### C++
- **Memory**: Manual (RAII), deterministic destructors, allocator customizable
- **Dispatch**: Virtual opt-in, templates zero-cost, inline everything
- **Safety**: Undefined behavior if misused, no bounds checking by default
- **Iterators**: Template-based, zero abstraction cost, fully inlined
- **Performance**: What you write is what you get (no JIT surprises)

### Rust
- **Memory**: Borrow checker, no GC, deterministic drop, zero-cost
- **Dispatch**: Static by default, trait objects opt-in, monomorphization
- **Safety**: Compile-time memory safety, no data races, bounds checked
- **Iterators**: Zero-cost abstractions, full fusion, excellent codegen
- **Performance**: C++ speed with safety guarantees

## Trade-off Matrix

| Concern | Java | C++ | Rust |
|---------|------|-----|------|
| Memory Safety | Runtime checks | Undefined behavior | Compile-time guarantees |
| Abstraction Cost | Virtual dispatch | Zero (templates) | Zero (traits) |
| Iterator Fusion | JIT-dependent | Optimizer-dependent | Guaranteed |
| Allocation | GC overhead | Explicit control | Explicit control |
| Compilation | Fast | Slow (templates) | Slow (monomorphization) |

## Cross-Skill Integration

Requires: problem_specification
Feeds into: multi_language_codegen, safety_invariants
