# CS500 Skills Quick Reference

One-line descriptions for all 19 skills + orchestrator.

## 🧠 Core Reasoning & Research

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **problem_specification** | "formalize", "specify", "constraints" | Formal spec: types, invariants, contracts |
| **algorithmic_analysis** | "complexity", "analyze algorithm", "prove correctness" | O() analysis + correctness proof |
| **comparative_complexity** | "compare designs", "alternatives", "trade-offs" | Comparison table of designs |

## ⚙️ Design & Optimization

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **systems_design_patterns** | "data structure choice", "cache-aware", "heap vs tree" | Design patterns + justification |
| **microarchitectural_modeling** | "performance model", "cache behavior", "predict cycles" | Cycle-level performance prediction |
| **language_comparative_runtime** | "Java vs C++ vs Rust", "GC overhead", "abstraction cost" | Language runtime comparison |

## 🔄 Iterative Improvement

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **iterative_optimizer** | "can we do better?", "optimize", "find optimal", "aggressive optimization" | Challenge list: 3-5 improvements, radical alternatives, never stops until user satisfied |

## 💻 Implementation & Code Synthesis

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **multi_language_codegen** | "implement in Java/C++/Rust", "idiomatic code" | Three implementations |
| **unit_test_generation** | "write tests", "test edge cases", "validate invariants" | JUnit/Catch2/Rust tests |
| **safety_invariants** | "add assertions", "bounds checks", "safety checks" | Code with invariant validation |

## 📊 Benchmarking & Measurement

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **benchmark_design** | "benchmark", "measure performance", "throughput" | JMH/Google/Criterion benchmarks |
| **performance_interpretation** | "interpret results", "explain bottleneck", "why slower" | Systems-level analysis |
| **reporting_visualization** | "format results", "tables", "charts" | Markdown tables + charts |

## 🧾 Report Writing & Pedagogical

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **technical_exposition** | "write report", "CS500 paper", "academic writeup" | Complete technical document |
| **pedagogical_reflection** | "lessons learned", "future work", "takeaways" | Reflections + extensions |
| **temporal_style_adapter** | "1999-2002 voice", "era-appropriate", "pragmatic tone" | Voice consistency layer |

## 🧩 Meta-Composition / Control

| Skill | Trigger Keywords | Output |
|-------|-----------------|--------|
| **research_to_code_pipeline** | "full pipeline", "end-to-end", "research project" | Orchestrates all 19 skills |
| **skill_context_cache** | (automatic) | Persistent cross-stage memory |
| **self_consistency_checker** | "validate consistency", "check contradictions" | Consistency validation report |

## Activation Examples

### Quick Single-Skill
```
"Analyze complexity of heap-based merge"
→ algorithmic_analysis
```

### Multi-Skill Composition
```
"Compare heap vs tournament tree considering cache behavior"
→ comparative_complexity + systems_design_patterns + microarchitectural_modeling
```

### Full Pipeline
```
"Research k-way merge: theory, code, benchmarks, report"
→ research_to_code_pipeline (all 19 skills)
```

### Iterative Optimization
```
"Can we do better than O(n²)?"
→ iterative_optimizer (challenges with O(n log n), then constants, then radical alternatives)
```

## Skill Dependency Quick View

```
Standalone (no dependencies):
  - problem_specification

Light dependencies (1-2 prior skills):
  - algorithmic_analysis (needs spec)
  - comparative_complexity (needs analysis)
  - multi_language_codegen (needs design)

Heavy dependencies (3+ prior skills):
  - technical_exposition (needs all research/impl/bench)
  - self_consistency_checker (needs all artifacts)

Cross-cutting (used by many):
  - skill_context_cache (storage for all)
  - temporal_style_adapter (applied to writing)
  - research_to_code_pipeline (coordinates all)
```

## Typical Workflows

**Theory Only**: problem_specification → algorithmic_analysis → comparative_complexity
**Implementation Only**: [design given] → multi_language_codegen → unit_test_generation
**Benchmarking Only**: [code given] → benchmark_design → performance_interpretation
**Documentation Only**: [all artifacts] → reporting_visualization → technical_exposition
**Full Research**: research_to_code_pipeline (all stages)

---

*Quick reference for CS500 skill stack | See INTEGRATION_GUIDE.md for workflows | README.md for overview*
