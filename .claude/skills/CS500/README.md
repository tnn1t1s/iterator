# CS500 Systems Research & Implementation Skills

Comprehensive skill stack for graduate-level systems research, algorithm design, multi-language implementation, and performance engineering.

**Era**: 1999-2002 engineering register | Pragmatic, precise, measurement-driven

## Skill Categories

### 🧠 Core Reasoning & Research (3 skills)
- **problem_specification**: Vague text → formal spec w/ constraints, invariants, contracts
- **algorithmic_analysis**: Design theory, asymptotic analysis, correctness proofs (Knuth, CLRS, Sedgewick)
- **comparative_complexity**: Multi-design evaluation, O() comparison, constant factors

### ⚙️ Design & Optimization (3 skills)
- **systems_design_patterns**: Classic heuristics, data structure selection, cache-aware design
- **microarchitectural_modeling**: Cache lines, branch prediction, allocation, lock contention (P4 → M4)
- **language_comparative_runtime**: Java vs C++ vs Rust semantics, GC vs borrow checker vs RAII

### 🔄 Iterative Improvement (1 skill)
- **iterative_optimizer**: The Relentless Interviewer - challenges every solution with "Can you do better?", pushes beyond optimal complexity to constant factors, never stops until user satisfied

### 💻 Implementation & Code Synthesis (3 skills)
- **multi_language_codegen**: Idiomatic code in Java, C++, Rust w/ proper memory models
- **unit_test_generation**: Parameterized tests, edge cases, iterator contracts (JUnit, Catch2, Rust)
- **safety_invariants**: Assertions, borrow-safety, UB guards, cross-language equivalence

### 📊 Benchmarking & Measurement (3 skills)
- **benchmark_design**: Controlled setups, workload patterns, reproducible metrics
- **performance_interpretation**: Systems-level analysis, cache behavior, allocator overhead
- **reporting_visualization**: Tables, charts, reproducible Markdown summaries

### 🧾 Report Writing & Pedagogical (3 skills)
- **technical_exposition**: Academic engineering tone, theory/implementation/empirical separation
- **pedagogical_reflection**: Lessons learned, follow-ups, broader systems connections
- **temporal_style_adapter**: 1999-2002 voice consistency, era-accurate idioms

### 🧩 Meta-Composition / Control (3 skills)
- **research_to_code_pipeline**: Orchestrates research → theory → design → code → benchmark → report
- **skill_context_cache**: Maintains cross-step continuity, agentic memory
- **self_consistency_checker**: Verifies theory/code/benchmark alignment

## Activation Patterns

**Automatic triggers**:
- "iterator problem" → problem_specification + algorithmic_analysis
- "design options" → systems_design_patterns + comparative_complexity
- "can we do better?" / "optimize" / "find optimal" → iterative_optimizer
- "implement in Java/C++/Rust" → multi_language_codegen + safety_invariants
- "benchmark" → benchmark_design + performance_interpretation
- "write up results" → technical_exposition + reporting_visualization
- "full research pipeline" → research_to_code_pipeline (orchestrator)
- "aggressive optimization" → research_to_code_pipeline with iterative_optimizer loop enabled

## Usage

### Single Skill
"Analyze the algorithmic complexity of merge-based iterator composition"
→ Activates: algorithmic_analysis

### Multi-Skill Composition
"Design, implement, and benchmark a k-way merge iterator in all three languages"
→ Activates: systems_design_patterns, comparative_complexity, multi_language_codegen, benchmark_design, performance_interpretation

### Full Pipeline
"Research optimal iterator composition strategies, implement in Java/C++/Rust, benchmark, and write CS500-level report"
→ Activates: research_to_code_pipeline (coordinates all relevant skills)

## Integration with Iterator Project

These skills support the iterator project's core mission:
- Formal problem specification
- Multi-language idiomatic implementation
- Performance-aware design decisions
- Reproducible benchmarking
- Academic-grade documentation

## Tool Permissions

**Research/Theory skills**: Read, Grep, Glob, WebFetch, WebSearch
**Implementation skills**: Read, Write, Edit, Bash
**Benchmarking skills**: Read, Write, Bash
**Report skills**: Read, Write, Edit
**Orchestration skills**: All tools (coordinates others)

## Era-Accurate Voice

1999-2002 systems engineering register:
- "Hot path" not "critical path"
- "Don't trust JITs" not "runtime optimization varies"
- "Measure before you micro-optimize"
- Pragmatic skepticism about abstractions
- Focus on actual CPU behavior, not theoretical elegance

## References

- Knuth, TAOCP Vol 1-3
- CLRS, Introduction to Algorithms
- Sedgewick, Algorithms in C++/Java
- Hennessy & Patterson, Computer Architecture
- Era: P4 Northwood, early Xeons, nascent M1 concepts

## Contributing

Add new skills to CS500/:
```bash
mkdir -p .claude/skills/CS500/new_skill
# Create SKILL.md with frontmatter
```

---
*Iterator Project CS500 Skills | Graduate systems research capability | 19 specialized modules*
