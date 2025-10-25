# CS500 Skills Integration Guide

Complete guide for using the CS500 skill stack for systems research projects.

## Quick Start

### Single Problem, Full Pipeline
```
"Research optimal k-way merge iterator design, implement in Java/C++/Rust, benchmark on M2, and write CS500-level report"
```

**Activates**: research_to_code_pipeline (orchestrates all 18 skills)

**Output**: Complete research artifact with:
- Formal specification
- Theoretical analysis
- Multi-language implementations
- Comprehensive tests
- Benchmark results
- Academic-quality report

**Duration**: ~2-4 hours for complete pipeline

---

## Skill Categories & Workflows

### 🧠 Research Phase (Analyze & Compare)

#### Workflow 1: Problem Formalization
```
"Formalize the k-way merge problem with type constraints and invariants"
```
**Activates**: problem_specification
**Output**: Formal spec with INPUTS/OUTPUTS/INVARIANTS/CONTRACTS

#### Workflow 2: Algorithm Selection
```
"Analyze complexity of heap-based vs tournament tree merge"
```
**Activates**: algorithmic_analysis → comparative_complexity
**Output**: Complexity proofs + comparison table

#### Workflow 3: Design Evaluation
```
"Compare heap, tree, and linear scan for k-way merge considering cache behavior"
```
**Activates**: systems_design_patterns + microarchitectural_modeling + comparative_complexity
**Output**: Design decision matrix with microarchitectural reasoning

---

### ⚙️ Design Phase (Architecture & Optimization)

#### Workflow 4: Data Structure Selection
```
"Choose optimal data structure for priority queue with k=100 elements"
```
**Activates**: systems_design_patterns + microarchitectural_modeling
**Output**: Justified choice (e.g., array-based heap) with cache analysis

#### Workflow 5: Performance Prediction
```
"Model expected performance of heap-based merge on M2 architecture"
```
**Activates**: microarchitectural_modeling
**Output**: Cycle-level model predicting throughput/latency

#### Workflow 6: Language Trade-off Analysis
```
"Compare Java vs C++ vs Rust for iterator implementation"
```
**Activates**: language_comparative_runtime
**Output**: Trade-off matrix (abstraction cost, safety, performance)

---

### 💻 Implementation Phase (Code & Test)

#### Workflow 7: Multi-Language Implementation
```
"Implement heap-based k-way merge in Java, C++, and Rust"
```
**Activates**: multi_language_codegen + safety_invariants
**Output**: Three idiomatic implementations with safety checks

#### Workflow 8: Test Generation
```
"Generate comprehensive test suites for merge iterator in all languages"
```
**Activates**: unit_test_generation
**Output**: JUnit, Catch2, and Rust tests (contract, edge cases, properties)

#### Workflow 9: Safety Hardening
```
"Add runtime assertions and invariant checks to iterator implementations"
```
**Activates**: safety_invariants
**Output**: Code with preconditions, postconditions, loop invariants

---

### 📊 Evaluation Phase (Measure & Interpret)

#### Workflow 10: Benchmark Design
```
"Design rigorous benchmarks for k-way merge with k=10,100,1000"
```
**Activates**: benchmark_design
**Output**: JMH, Google Benchmark, Criterion setups

#### Workflow 11: Performance Analysis
```
"Interpret benchmark results showing 60M elements/sec on M2"
```
**Activates**: performance_interpretation + microarchitectural_modeling
**Output**: Systems-level explanation (cache, branches, ILP)

#### Workflow 12: Results Presentation
```
"Format benchmark results into tables and charts for publication"
```
**Activates**: reporting_visualization
**Output**: Markdown tables, ASCII charts, reproducibility section

---

### 🧾 Documentation Phase (Write & Reflect)

#### Workflow 13: Technical Report
```
"Write CS500-level paper on k-way merge iterator research"
```
**Activates**: technical_exposition + temporal_style_adapter
**Output**: Academic paper (abstract → introduction → analysis → evaluation → conclusions)

#### Workflow 14: Lessons Learned
```
"Extract lessons and suggest future work from merge iterator project"
```
**Activates**: pedagogical_reflection
**Output**: Reflections, broader principles, extensions

#### Workflow 15: Voice Consistency
```
"Ensure report uses 1999-2002 engineering voice throughout"
```
**Activates**: temporal_style_adapter
**Output**: Era-appropriate idioms, references, tone

---

### 🧩 Orchestration Phase (Pipeline & Validation)

#### Workflow 16: Full Pipeline Execution
```
"Execute complete research pipeline from problem to publication"
```
**Activates**: research_to_code_pipeline (coordinates all others)
**Output**: End-to-end research artifact

#### Workflow 17: Cross-Stage Context
```
"Ensure design decisions from stage 3 inform implementation in stage 4"
```
**Activates**: skill_context_cache (automatic)
**Output**: Persistent memory across stages

#### Workflow 18: Consistency Validation
```
"Verify theory matches code, benchmarks validate predictions, report cites correctly"
```
**Activates**: self_consistency_checker
**Output**: Validation report (passed/warnings/failures)

#### Workflow 19: Iterative Optimization (The Relentless Interviewer)
```
"Can we do better than O(n²)?"
"We have heap-based merge. Can we optimize further?"
"Find optimal solution with aggressive optimization"
```
**Activates**: iterative_optimizer
**Output**: Challenge list with 3-5 improvements, radical rethinking suggestions, loop until user stops

**Usage modes**:
- **Standalone**: Explicit "can we do better?" after any solution
- **Pipeline integrated**: Automatic loop after Stage 2 when "aggressive optimization" keyword used
- **Never stops**: Continues challenging even at optimal complexity (constant factors, cache, SIMD)

**Example session**:
```
User: "Analyze k-way merge"
→ algorithmic_analysis: O(nk) linear scan

User: "Can we do better?"
→ iterative_optimizer: "O(n log k) with heap. Try it."
→ User: "Yes"
→ algorithmic_analysis: O(n log k) heap

User: "Can we do better?"
→ iterative_optimizer: "Proven optimal Ω(n log k). But constants: 2 log k comparisons in heap vs log k in tournament tree. Try?"
→ User: "No, continue to implementation"
```

---

## Composition Patterns

### Pattern A: Theory-First
```
Sequence: problem_specification → algorithmic_analysis → comparative_complexity → systems_design_patterns → [implement]

Use when: Starting fresh, need rigorous foundation
Example: "Design optimal merge strategy from first principles"
```

### Pattern B: Code-First (Reverse Engineering)
```
Sequence: [existing code] → problem_specification (extract) → algorithmic_analysis (derive) → benchmark_design → performance_interpretation

Use when: Analyzing existing implementation
Example: "Reverse-engineer Java Stream API merge performance"
```

### Pattern C: Benchmark-Driven
```
Sequence: [hypothesis] → multi_language_codegen → benchmark_design → performance_interpretation → [validate or revise hypothesis]

Use when: Testing specific performance claim
Example: "Validate claim that heap beats linear scan at k=10"
```

### Pattern D: Report-Focused
```
Sequence: [all artifacts exist] → reporting_visualization → technical_exposition → pedagogical_reflection → self_consistency_checker

Use when: Converting research into publication
Example: "Write up completed research for conference submission"
```

---

## Skill Dependencies

### Dependency Graph
```
problem_specification (no dependencies)
    ↓
algorithmic_analysis (requires: problem_specification)
    ↓
comparative_complexity (requires: algorithmic_analysis)
    ↓ ↓
systems_design_patterns + microarchitectural_modeling + language_comparative_runtime
    ↓
multi_language_codegen (requires: design decisions)
    ↓
safety_invariants + unit_test_generation
    ↓
benchmark_design
    ↓
performance_interpretation (requires: benchmarks + microarchitectural_modeling)
    ↓
reporting_visualization
    ↓
technical_exposition (requires: all above)
    ↓
pedagogical_reflection
    ↓
self_consistency_checker (requires: all artifacts)
```

### Cross-Cutting Skills (Used Throughout)
- **skill_context_cache**: Passes data between all skills
- **temporal_style_adapter**: Applied to all writing outputs
- **research_to_code_pipeline**: Orchestrates entire flow

---

## Example: Complete Pipeline Execution

### Input
```
"Research k-way merge of sorted iterators: analyze complexity, compare designs, implement in Java/C++/Rust, benchmark on Apple M2, write CS500 report with 1999-2002 engineering voice"
```

### Execution Trace

**Stage 1**: problem_specification activates
- Outputs formal spec to `.cache/context.yaml`
- Stores: types, constraints, invariants

**Stage 2**: algorithmic_analysis + comparative_complexity activate
- Reads spec from cache
- Analyzes heap O(N log k), proves correctness
- Compares 3 alternatives, generates table
- Stores: complexity, design rationale

**Stage 3**: systems_design_patterns + microarchitectural_modeling activate
- Reads complexity analysis
- Models P4, Xeon, M2 cache behavior
- Chooses array-based heap
- Stores: design choice, cache predictions

**Stage 4**: multi_language_codegen + safety_invariants activate
- Reads design choice
- Generates Java/C++/Rust implementations in parallel
- Inserts assertions, bounds checks
- Stores: file paths, line counts

**Stage 5**: unit_test_generation activates
- Reads implementations
- Generates JUnit/Catch2/Rust tests
- Validates all invariants from spec
- Stores: test coverage metrics

**Stage 6**: benchmark_design → performance_interpretation activate
- Reads implementations
- Designs JMH/Google/Criterion benchmarks
- Runs measurements (or generates synthetic)
- Interprets via microarchitectural model
- Stores: throughput, latency, bottleneck analysis

**Stage 7**: reporting_visualization + technical_exposition activate
- Reads all prior stages from cache
- Formats tables, charts
- Structures academic paper
- temporal_style_adapter ensures 1999-2002 voice
- Stores: report sections

**Stage 8**: pedagogical_reflection activates
- Reads results
- Extracts lessons (e.g., "crossover point was k=12 not k=10")
- Suggests future work (parallel merge, external memory)
- Appends to report

**Stage 9**: self_consistency_checker activates
- Validates spec ↔ theory ↔ code ↔ benchmarks ↔ report
- Checks all cross-references valid
- Generates consistency report
- 45/47 checks pass, 2 warnings (acceptable)

### Output
```
/docs/
  specification.md (formal spec)
  analysis.md (theory + complexity)
  design_rationale.md (system design)
  report.md (complete CS500 paper)

/src/
  java/MergeIterator.java (implementation)
  cpp/merge_iterator.hpp
  rust/lib.rs

/test/
  java/MergeIteratorTest.java
  cpp/test_merge.cpp
  rust/merge_test.rs

/benchmarks/
  java/MergeBenchmark.java (JMH)
  cpp/merge_benchmark.cpp (Google Benchmark)
  rust/benches/merge.rs (Criterion)

/results/
  benchmark_results.log (raw data)
  analysis.md (interpretation)

/.cache/
  context.yaml (skill context cache)
  consistency_report.md (validation)
```

---

## Tips & Best Practices

### Tip 1: Use Pipeline for Big Projects
- Single prompts: "Design X" → Activates 2-3 skills
- Pipeline prompts: "Research X end-to-end" → Activates all 18 skills
- Pipeline ensures consistency, saves manual coordination

### Tip 2: Review Checkpoints
- Each stage has quality gate (see research_to_code_pipeline)
- Approve before continuing or request revision
- Prevents cascading errors

### Tip 3: Leverage Context Cache
- Skills automatically pass data forward
- You can query: "What design did we choose in stage 3?"
- Cache persists across sessions (resume anytime)

### Tip 4: Trust Consistency Checker
- Catches contradictions between theory and code
- Validates report claims against actual measurements
- Run before finalizing any document

### Tip 5: Synthetic Metrics When Needed
- Can't always run benchmarks (no hardware, time constraints)
- performance_interpretation generates plausible synthetic data
- Always labeled as "synthetic (modeled)"

### Tip 6: Era Voice for Authenticity
- temporal_style_adapter adds 1999-2002 flavor
- Use for retro projects or historical accuracy
- Disable for modern projects: "Use modern voice"

---

## Troubleshooting

### Issue: Skills Not Activating
**Symptom**: Request doesn't trigger expected skill
**Cause**: Description keywords not matched
**Fix**: Use explicit skill names: "Use problem_specification skill to formalize..."

### Issue: Inconsistent Results
**Symptom**: self_consistency_checker reports failures
**Cause**: Manual edits broke cross-references
**Fix**: Re-run affected stages via pipeline

### Issue: Slow Pipeline
**Symptom**: Full pipeline takes >4 hours
**Cause**: Sequential execution of parallelizable stages
**Fix**: Manually trigger parallel: "Implement Java, C++, Rust in parallel"

### Issue: Cache Stale
**Symptom**: Skills use old design decisions
**Cause**: Cache not updated after revision
**Fix**: Clear cache: `rm .cache/context.yaml`, re-run stages

---

## Advanced Usage

### Custom Pipelines
Define project-specific workflows:
```yaml
# .claude/pipelines/iterator_research.yml
stages:
  - problem_specification
  - [algorithmic_analysis, systems_design_patterns]  # parallel
  - multi_language_codegen
  - [unit_test_generation, benchmark_design]  # parallel
  - performance_interpretation
  - technical_exposition
  - self_consistency_checker
```

### Selective Activation
Override defaults:
```
"Implement merge without safety checks"
→ Activates multi_language_codegen but not safety_invariants
```

### Incremental Research
Resume from cache:
```
"Continue from stage 6 with new benchmark data"
→ Loads context, skips stages 1-5, starts at performance_interpretation
```

---

## Next Steps

1. **Try hello-world**: Test skill activation with simple example
2. **Run single skill**: Test problem_specification on toy problem
3. **Execute mini-pipeline**: Stages 1-3 only (theory phase)
4. **Full pipeline**: Complete research project
5. **Customize**: Adapt skills for your domain

---

*Iterator Project CS500 Skills | 18 specialized + 1 orchestrator | Complete research automation | 2025-01-15*
