# CS500 Skills Catalog

**For newcomers**: This is a beginner-friendly guide to the 19 specialized skills that power the research pipeline. Each skill handles one specific aspect of systematic algorithm research.

**Full technical reference**: See [.claude/skills/CS500/SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md)

---

## What Are Skills?

Think of skills as specialized experts you can call on:
- **One skill, one job**: Each skill does exactly one thing well
- **Composable**: Skills work together (one calls another)
- **Automated**: Claude decides when to use which skill based on your request
- **Reusable**: Same skill works across different problems

**Example**: Instead of saying "Claude, figure out the complexity", you have an `algorithmic_analysis` skill that knows exactly how to do lower bound proofs, Big-O analysis, and correctness arguments.

---

## The 19 Skills (Grouped by Purpose)

### 📋 Core Research (3 skills)
*Start here: Define what you're solving*

#### 1. `problem_specification`
**What it does**: Turns vague problems into formal specifications

**Input**: "Make a thing that merges sorted lists"
**Output**: Formal definition with types, invariants, preconditions, postconditions, research questions

**Why it matters**: Prevents "solution leak" - poses questions instead of prescribing heap-based approaches

**Beginner tip**: This skill stops you from jumping to solutions before understanding the problem

---

#### 2. `algorithmic_analysis`
**What it does**: Proves lower bounds and analyzes complexity

**Input**: Formal problem specification
**Output**:
- Lower bound proofs (Ω notation with decision tree arguments)
- Candidate algorithms from literature (TAOCP, CLRS)
- Correctness proofs
- Time/space complexity analysis

**Why it matters**: Establishes theoretical limits before writing code

**Beginner tip**: "What's the best we could possibly do?" before "How do I implement it?"

---

#### 3. `comparative_complexity`
**What it does**: Compares multiple algorithm designs in tables

**Input**: List of candidate algorithms
**Output**: Comparison tables showing time, space, best-for scenarios

**Example output**:
| Algorithm | Time | Space | Best For |
|-----------|------|-------|----------|
| Linear Scan | O(Nk) | O(1) | k ≤ 8 (cache locality) |
| Binary Heap | O(N log k) | O(k) | General (k > 8) |
| Loser Tree | O(N log k) | O(k) | Large k (fewer comparisons) |

**Beginner tip**: Side-by-side comparison makes trade-offs obvious

---

### 🎯 Design Optimization (3 skills)
*Choose the right approach for production*

#### 4. `systems_design_patterns`
**What it does**: Evaluates designs for production use

**Input**: Candidate algorithms
**Output**: Selection based on production validation (Grafana blog posts, Apache benchmarks), cache-aware design, trade-offs

**Why it matters**: Textbook "optimal" ≠ production "fast" (constants, cache, hardware matter)

**Beginner tip**: Look for real-world deployments, not just textbook claims

---

#### 5. `microarchitectural_modeling`
**What it does**: Models hardware effects (cache, branch prediction, ILP)

**Input**: Algorithm design
**Output**: Predicted cache misses, branch mispredictions, cycles per operation

**Why it matters**: Cache misses cost 100+ cycles. Algorithm with "fewer operations" can be slower if it thrashes cache.

**Beginner tip**: Advanced skill - skip on first pass, use when performance surprises you

---

#### 6. `language_comparative_runtime`
**What it does**: Compares Java vs C++ vs Rust trade-offs

**Input**: Algorithm + language choice question
**Output**: Language-specific analysis (GC overhead, zero-cost abstractions, memory safety)

**Beginner tip**: Use when choosing implementation language for production

---

### 🔄 Iterative Improvement (1 skill)
*The relentless interviewer*

#### 7. `iterative_optimizer`
**What it does**: Challenges you with "Can we do better?" until you say stop

**Input**: Current solution
**Output**: 5 escalating challenge levels:
1. Complexity improvements
2. Constant factor optimizations
3. Radical algorithm changes
4. Microarchitectural tuning
5. Theoretical limits

**Why it matters**: Prevents settling for first solution. Simulates skilled interviewer pushing for better.

**Beginner tip**: **Never stops until you say stop**. Use trigger: "can we do better?" or "optimize"

---

### 💻 Implementation (3 skills)
*Write the code*

#### 8. `java_codegen`
**What it does**: Generates multi-variant Java implementations

**Input**: Algorithm designs (baseline, standard, optimized)
**Output**: 3+ implementations with:
- One class per file, descriptive names
- Separate example files
- Proper Gradle structure

**Why it matters**: Multi-variant approach enables empirical comparison (not just "the answer")

**Beginner tip**: Generate baseline O(n²) + standard O(n log n) + optimized variant

---

#### 9. `unit_test_generation`
**What it does**: Creates comprehensive JUnit test suites

**Input**: Implementations to test
**Output**:
- Shared test base class (ensures all variants pass identical tests)
- Contract tests (hasNext consistency, exhaustion)
- Edge cases (empty, single, duplicates)
- Property tests (invariants)

**Why it matters**: Shared base pattern prevents "optimized variant passes different tests"

**Beginner tip**: ~70 tests across 3 variants = ~23 tests each, all sharing same base

---

#### 10. `safety_invariants`
**What it does**: Adds assertions and invariant checking

**Input**: Implementation code
**Output**: Assert statements, precondition checks, postcondition verification

**Beginner tip**: Catches bugs early (before they become production issues)

---

### 📊 Benchmarking (3 skills)
*Measure performance*

#### 11. `benchmark_design`
**What it does**: Creates controlled JMH/Criterion benchmarks

**Input**: Implementations to measure
**Output**: JMH benchmark with warmup, measurement iterations, parameters

**Why it matters**: `System.nanoTime()` is too noisy. JMH handles JIT warmup, GC, outliers.

**Beginner tip**: Pragmatic approach = design comprehensive suite, run quick validation, document future work

---

#### 12. `test_data_design`
**What it does**: Systematically designs test data across dimensions

**Input**: Problem characteristics
**Output**: Test data catalog covering:
- Dimensions: k (inputs), N (size), distribution (uniform/skewed), pattern (random/sequential)
- Adversarial cases (worst-case inputs)
- Realistic workloads (log merging, time series)

**Why it matters**: Random testing misses corner cases. Systematic exploration finds where algorithms break.

**Beginner tip**: Don't just test "happy path" - stress each dimension independently

---

#### 13. `performance_interpretation`
**What it does**: Analyzes benchmark results for bottlenecks

**Input**: Benchmark results
**Output**: Systems-level analysis (cache misses, branch prediction, allocation overhead)

**Beginner tip**: When results surprise you, this skill explains why

---

### 📝 Documentation (2 skills)
*Communicate findings*

#### 14. `technical_exposition`
**What it does**: Writes academic papers + scannable summaries

**Input**: All research stages
**Output**:
- **One-page summary**: Bulleted, quantitative, L7/L8-scannable (30-second grok)
- **Full paper**: Academic format (abstract, intro, analysis, implementation, evaluation)

**Why it matters**: L7/L8 engineers scan hundreds of artifacts per week. Dense prose = immediate "no hire"

**Beginner tip**: Use bullets, quantify everything ("50% speedup" not "faster"), no meta-commentary

---

#### 15. `pedagogical_reflection`
**What it does**: Writes lessons learned and future work

**Input**: Completed research
**Output**: What worked, what didn't, areas for improvement, next steps

**Beginner tip**: Honest acknowledgment of gaps separates senior from junior

---

### 🎭 Orchestration (4 skills)
*Coordinate the pipeline*

#### 16. `research_to_code_pipeline`
**What it does**: Coordinates all 19 skills across 8 stages

**Input**: Problem description
**Output**: Complete artifact (specification → validation)

**Stages**:
1. Specification (skill 1)
2. Analysis (skills 2-3)
3. Design (skills 4-6)
4. Implementation (skills 8-10)
5. Testing (skill 9)
6. Benchmarking (skills 11-13)
7. Validation (skill 17)
8. Summary (skills 14-15)

**Why it matters**: Automates systematic methodology with minimal user input

**Beginner tip**: Use trigger: "Use research_to_code_pipeline skill" or "full pipeline"

---

#### 17. `self_consistency_checker`
**What it does**: Validates theory ↔ code ↔ tests ↔ benchmarks

**Input**: All artifacts from stages 1-7
**Output**: Consistency report checking:
- Theory predictions match implementation complexity
- Tests cover specification requirements
- Benchmarks validate theoretical claims
- Documentation is accurate

**Beginner tip**: Catches loser tree "O(log k)" claim vs "O(k)" implementation

---

#### 18. `skill_context_cache`
**What it does**: Maintains shared memory across pipeline stages

**Input**: Results from earlier stages
**Output**: Context available to later stages (avoids re-reading/re-computing)

**Beginner tip**: Automatically handled by pipeline - you don't invoke this directly

---

#### 19. `arxiv_research`
**What it does**: Surveys modern literature for production validation

**Input**: Problem domain
**Output**: Recent papers, production deployments (Grafana, Apache), benchmark results

**Why it matters**: Bridges gap between textbooks (academic) and practice (production)

**Beginner tip**: Use trigger: "search arxiv for production validation" or "modern literature survey"

---

## How Skills Work Together

**Example flow** (k-way merge problem):

1. **User**: "Design efficient k-way merge"
2. **`problem_specification`**: Formal spec with research questions (NOT "implement heap")
3. **`algorithmic_analysis`**: Lower bound Ω(N log k), candidate algorithms from TAOCP
4. **`arxiv_research`**: Finds Grafana 2024 production validation (loser tree 50% faster)
5. **`comparative_complexity`**: Table comparing 4 algorithms
6. **`systems_design_patterns`**: Selects loser tree based on production evidence
7. **`java_codegen`**: Generates 3 variants (LinearScan, HeapBased, LoserTree)
8. **`unit_test_generation`**: Creates 70 tests with shared base
9. **`test_data_design`**: Designs 24 test scenarios across 5 dimensions
10. **`benchmark_design`**: JMH infrastructure + quick validation
11. **`self_consistency_checker`**: Validates theory matches implementation
12. **`technical_exposition`**: Scannable one-page summary
13. **External review** (Stages 9-10): Critique + scoring

**Total time**: Full pipeline runs with minimal user input, ready for review

---

## Skill Trigger Patterns

**How Claude knows which skill to use**:

| You say... | Skill activated |
|------------|----------------|
| "formalize the problem" | `problem_specification` |
| "what's the complexity?" | `algorithmic_analysis` |
| "can we do better?" | `iterative_optimizer` |
| "implement in Java" | `java_codegen` |
| "benchmark this" | `benchmark_design` + `performance_interpretation` |
| "write summary" | `technical_exposition` |
| "full pipeline" | `research_to_code_pipeline` (coordinates all 19) |
| "search arxiv" | `arxiv_research` |

---

## Common Beginner Questions

### Q: Do I need to memorize all 19 skills?
**A**: No. Use `research_to_code_pipeline` which automatically coordinates all 19. Learn individual skills when you need custom control.

### Q: Which skills are most important?
**A**:
- **Essential**: problem_specification, algorithmic_analysis, java_codegen, unit_test_generation, technical_exposition
- **High-value**: iterative_optimizer, test_data_design, arxiv_research
- **Advanced**: microarchitectural_modeling, language_comparative_runtime

### Q: Can I use skills outside this project?
**A**: Yes! Skills are in `.claude/skills/CS500/` and work in any Claude Code conversation.

### Q: What if a skill makes a mistake?
**A**: Skills can have bugs (see collating-iterator: loser tree O(k) bug). That's why Stage 9 (critique) exists - independent review catches errors.

### Q: How do I add my own skill?
**A**: Create `.claude/skills/my-skill/SKILL.md` with YAML frontmatter. See [SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md) for format.

---

## Learning Path

**Beginner** (understand the pipeline):
1. Read `CLAUDE.md` (methodology overview)
2. Read this file (skill summaries)
3. Look at `projects/collating-iterator/INDEX.html` (example output)
4. Try: "Use research_to_code_pipeline skill to solve: [your problem]"

**Intermediate** (use individual skills):
1. Read `.claude/skills/CS500/SKILL_INDEX.md` (detailed skill docs)
2. Practice triggers: "can we do better?", "search arxiv", "formalize problem"
3. Customize: Edit skills to match your preferences

**Advanced** (create your own skills):
1. Study existing skill implementations
2. Create custom skills for your domain
3. Contribute improvements back to framework

---

**Next**: See [CLAUDE.md](CLAUDE.md) for full methodology and anti-patterns
