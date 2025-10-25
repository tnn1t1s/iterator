# Iterator Research Framework

**Purpose**: Systematic research pipeline for algorithm design, implementation, and evaluation using Claude Code's CS500 skill framework.

**Target audience**: L7/L8 senior engineers, technical interviewers, CS educators

**Philosophy**: Question before solution. Empirical validation of theory. Multiple implementations for comparison. Honest acknowledgment of limitations.

---

## What This Is

A research methodology framework demonstrating:
- **Systematic decomposition**: 10-stage pipeline from specification through critique
- **Multi-variant approach**: Baseline, standard, optimized implementations for empirical comparison
- **Production validation**: Literature review (TAOCP, CLRS, arxiv) + real-world deployment evidence
- **Scannable documentation**: Bulleted summaries for senior reviewers reviewing hundreds per week
- **Self-critique**: Independent evaluation identifying strengths and gaps (not self-congratulatory)

**Not**: Interview "answers" to memorize. This is methodology for discovering solutions.

---

## 10-Stage Research Pipeline

### Stages 1-4: Research & Design
1. **Specification**: Formal problem definition with research questions (NOT prescriptive solutions)
2. **Analysis**: Lower bounds, candidate algorithms, literature review (TAOCP, CLRS, arxiv)
3. **Design**: Comparative analysis with production validation cites
4. **Implementation**: Multi-variant strategy (baseline/standard/optimized for empirical comparison)

### Stages 5-7: Validation
5. **Testing**: Comprehensive test suites with shared base patterns
6. **Benchmarking**: Systematic test data design + pragmatic execution (comprehensive design, focused execution, documented future work)
7. **Validation**: Cross-artifact consistency checks (theory ↔ code ↔ tests ↔ benchmarks)

### Stages 8-10: Presentation & Critique
8. **Summary**: Scannable one-page format for L7/L8 reviewers (bulleted, quantitative, no meta-commentary)
9. **Critique**: Independent reviewer evaluation identifying strengths and critical gaps
10. **Scoring**: Skills assessment (1-10 scale) with overall hire recommendation

---

## CS500 Skills Framework

**Location**: `.claude/skills/CS500/` (19 specialized skills)

**Full documentation**: [SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md)

### Core Research (3 skills)
- `problem_specification`: Vague → formal spec (types, invariants, contracts)
- `algorithmic_analysis`: O() analysis + correctness proofs (TAOCP, CLRS)
- `comparative_complexity`: Multi-design comparison tables

### Design Optimization (3 skills)
- `systems_design_patterns`: Heap vs tree, cache-aware design
- `microarchitectural_modeling`: P4→M4 cache/branch/ILP modeling
- `language_comparative_runtime`: Java vs C++ vs Rust trade-offs

### Iterative Improvement (1 skill)
- `iterative_optimizer`: "Can we do better?" → 5 challenge levels (never stops until user says stop)

### Implementation (3 skills)
- `java_codegen`: Multi-variant Java implementations (baseline, standard, optimized)
- `unit_test_generation`: JUnit with edge cases + shared base pattern
- `safety_invariants`: Assertions, invariant checking

### Benchmarking (3 skills)
- `benchmark_design`: JMH controlled benchmarks + systematic test data
- `test_data_design`: Dimension analysis, adversarial cases, realistic workloads
- `performance_interpretation`: Systems-level bottleneck analysis

### Documentation (2 skills)
- `technical_exposition`: Academic papers + scannable summaries for L7/L8 reviewers
- `pedagogical_reflection`: Lessons learned, future work

### Orchestration (4 skills)
- `research_to_code_pipeline`: 8-stage coordinator, invokes all 19 skills
- `skill_context_cache`: Cross-stage persistent memory
- `self_consistency_checker`: Validates theory↔code↔benchmarks
- `arxiv_research`: Modern literature survey for production validation

---

## Key Principles

### 1. No Solution Leak
**Anti-pattern**: "Implement O(N log k) heap-based merge"
**Correct**: "What is minimum achievable complexity? Which algorithms reach it? Which performs best accounting for constants?"

Prescribing solutions before analysis poisons all downstream work (justifying vs discovering).

### 2. Multi-Variant Implementation
**Anti-pattern**: Only implement "the answer"
**Correct**: Baseline (O(Nk) naive) + Standard (O(N log k) heap) + Optimized (O(N log k) loser tree)

Enables empirical comparison. Separates strong candidates from memorizers.

### 3. Production Validation
**Anti-pattern**: "Textbook says loser tree is faster"
**Correct**: "Grafana 2024 production (Loki/Pyroscope/Prometheus): 50% speedup over heaps [cite blog post]"

Modern literature (arxiv) bridges gap between textbooks and production practice.

### 4. Scannable Summaries
**Anti-pattern**: Dense paragraph prose
**Correct**: Bulleted lists, quantitative claims (50% speedup, 70 tests, 7.4/10), 30-second grok for L7/L8

Senior engineers scan hundreds per week. Respect their time.

### 5. Honest Limitations
**Anti-pattern**: "Successfully implemented optimal solution"
**Correct**: "Loser tree has O(k) bug, comparison count not instrumented, build tooling struggles"

Self-awareness separates senior from junior. Acknowledge gaps before reviewer finds them.

### 6. Pragmatic Time Management
**Anti-pattern**: Run 40-minute benchmark suite in interview
**Correct**: Comprehensive design + 10-second validation + documented future work

Demonstrates judgment appropriate for time-constrained environments.

---

## Usage

### Start New Research Project
```bash
# Create project structure
mkdir -p projects/my-problem/{01-specification,02-analysis,03-design,04-implementation,05-testing,06-benchmarking,07-validation,08-summary,09-critique,10-scoring}

# Activate research pipeline (in Claude Code conversation)
# "Use research_to_code_pipeline skill to solve: [problem description]"
```

### Skills Trigger Patterns
- "formalize"|"specify" → `problem_specification`
- "complexity"|"analyze algorithm" → `algorithmic_analysis`
- "can we do better?"|"optimize" → `iterative_optimizer`
- "implement in Java" → `java_codegen`
- "benchmark"|"measure" → `benchmark_design` + `performance_interpretation`
- "write summary" → `technical_exposition`
- "full pipeline"|"aggressive optimization" → `research_to_code_pipeline`

### Key Files
- **Entry point**: `projects/README.md` (project index)
- **Skills catalog**: `.claude/skills/CS500/SKILL_INDEX.md`
- **Artifacts**: Each project's `INDEX.html` (scannable navigation)

---

## Example Output

See: `projects/collating-iterator/INDEX.html`

**Problem**: K-way merge for sorted iterators

**Solution**: 3 variants (LinearScan, HeapBased, LoserTree) based on Grafana 2024 production validation

**Results**: 70 passing tests, JMH infrastructure, systematic test data catalog

**Assessment**: Mean 7.4/10, "Conditional Hire - Technical Deep-Dive Required"

**Critical gaps**: Loser tree O(k) bug, build tooling struggles, comparison count not instrumented

---

## Design Decisions

### Why 10 stages?
Stages 1-7 are standard research pipeline. Stages 8-10 add meta-evaluation:
- **Stage 8**: Candidate presents work (scannable format)
- **Stage 9**: Independent reviewer evaluates (identifies gaps)
- **Stage 10**: Skills assessment (hire recommendation)

This simulates interview process where work is reviewed by senior engineers.

### Why multi-variant implementation?
Enables empirical comparison of theoretical claims. Demonstrates understanding that "optimal" depends on constants (cache, comparisons, k value).

### Why scannable summaries?
L7/L8 engineers review hundreds of artifacts per week. Dense prose is immediate "no hire" signal.

### Why honest limitations?
Self-awareness separates senior from junior. Better to acknowledge gaps than have reviewer discover them.

---

## Anti-Patterns (Immediate "No Hire")

1. **Solution leak**: Prescribe O(N log k) heap before analysis
2. **Dense prose**: Paragraph summaries instead of bullets
3. **Meta-commentary**: "The methodology succeeded in avoiding..." (talking about process, not work)
4. **Missing quantification**: "faster" not "50% faster"
5. **Build tooling hacks**: `javac -cp .` instead of learning Gradle
6. **No self-awareness**: Doesn't acknowledge gaps
7. **Vague claims**: No production validation cites
8. **Only "the answer"**: Single implementation instead of multi-variant

---

## License & Attribution

Framework: David Palaitis
Execution: Claude Code (Anthropic)
Skills: CS500-level systematic methodology

**Philosophy**: Research mindset beats memorization. Question before solution. Empirical validation of theory. Honest acknowledgment of limitations.

---

**Last updated**: October 2025
**Version**: 1.0.0
**Framework location**: https://github.com/tnn1t1s/iterator
