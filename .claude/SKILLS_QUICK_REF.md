# Skills Quick Reference - Iterator Project

**Location**: `.claude/skills/CS500/`
**Count**: 19 specialized skills
**Full Documentation**: See `SKILLS.md` for comprehensive guide

---

## How to Use Skills

### Explicit Invocation (Recommended)
```
"Use problem_specification skill to formalize this problem"
"Activate iterative_optimizer skill - can we do better?"
"Use comparative_complexity skill to compare heap vs tree"
```

### Keyword Triggers (Automatic)
```
"formalize the problem" → problem_specification
"can we do better?" → iterative_optimizer
"implement in all three languages" → multi_language_codegen
```

### Pipeline Mode (Full Workflow)
```
"Research optimal iterator design with aggressive optimization"
→ Activates research_to_code_pipeline
→ Coordinates all 19 skills automatically
```

---

## 19 CS500 Skills

### 🧠 Core Research (3)
| Skill | Use When | Output |
|-------|----------|--------|
| **problem_specification** | Need formal spec | Types, invariants, contracts |
| **algorithmic_analysis** | Need complexity proof | O() analysis + correctness |
| **comparative_complexity** | Compare alternatives | Comparison table |

### ⚙️ Design & Optimization (3)
| Skill | Use When | Output |
|-------|----------|--------|
| **systems_design_patterns** | Choose data structure | Heap vs tree justification |
| **microarchitectural_modeling** | Predict performance | Cycle-level model |
| **language_comparative_runtime** | Language trade-offs | Java vs C++ vs Rust |

### 🔄 Iterative Improvement (1) - **THE KEY SKILL**
| Skill | Use When | Output |
|-------|----------|--------|
| **iterative_optimizer** | **"Can we do better?"** | **5 challenges, never stops** |

**This is the relentless interviewer. Use it after EVERY solution.**

### 💻 Implementation (3)
| Skill | Use When | Output |
|-------|----------|--------|
| **multi_language_codegen** | Need code | Java, C++, Rust implementations |
| **unit_test_generation** | Need tests | JUnit/Catch2/Rust tests |
| **safety_invariants** | Add safety checks | Assertions, bounds checks |

### 📊 Benchmarking (3)
| Skill | Use When | Output |
|-------|----------|--------|
| **benchmark_design** | Design benchmark | JMH/Google/Criterion setup |
| **performance_interpretation** | Explain results | Systems-level analysis |
| **reporting_visualization** | Format results | Tables, charts |

### 🧾 Documentation (3)
| Skill | Use When | Output |
|-------|----------|--------|
| **technical_exposition** | Write paper | CS500-level document |
| **pedagogical_reflection** | Extract lessons | Future work, takeaways |
| **temporal_style_adapter** | Era voice | 1999-2002 tone |

### 🧩 Orchestration (3)
| Skill | Use When | Output |
|-------|----------|--------|
| **research_to_code_pipeline** | Full workflow | 8-stage pipeline |
| **skill_context_cache** | (automatic) | Cross-stage memory |
| **self_consistency_checker** | Validate | Consistency report |

---

## Common Workflows

### Workflow 1: Formalize Problem
```
User: "Sort array of N integers"
You: "Using problem_specification skill..."
→ Generate formal spec with types, invariants, contracts
```

### Workflow 2: Optimize Iteratively
```
User: "Can we do better than O(n²)?"
You: "Activating iterative_optimizer skill..."
→ Generate 5 challenges
→ User picks one
→ Repeat until user stops
```

### Workflow 3: Full Pipeline
```
User: "Research k-way merge with aggressive optimization"
You: "Activating research_to_code_pipeline with iterative_optimizer loop..."
→ Stage 1: problem_specification
→ Stage 2: algorithmic_analysis
→ Stage 2.5: iterative_optimizer loop (until user stops)
→ Stage 3-8: design, implement, benchmark, report
```

---

## Critical Usage Notes

1. **ALWAYS explicitly invoke skills** - Don't just respond naturally
2. **State which skill is active** - "Using [skill_name] skill to..."
3. **Follow skill output format** - Challenges, tables, formal specs
4. **iterative_optimizer is key** - Use after every solution for "can we do better?"
5. **Era voice** - Maintain 1999-2002 tone (P4 era, pragmatic, skeptical)

---

## Examples

### ❌ Bad (Natural Response)
```
User: "Sort array"
You: "Here's a bubble sort algorithm..."
```

### ✅ Good (Skill Usage)
```
User: "Sort array"
You: "Using problem_specification skill to formalize this problem..."

PROBLEM: Sort array of N integers

INPUTS:
- Type: Array<Integer> of size N
- Constraints: N ≥ 0, arbitrary values
...
```

### ❌ Bad (Missing Optimization)
```
User: "We have O(n²) solution"
You: "That works. Let's implement it."
```

### ✅ Good (Iterative Optimizer)
```
User: "We have O(n²) solution"
You: "Activating iterative_optimizer skill to challenge this solution..."

CURRENT SOLUTION: Bubble sort, O(n²)

CHALLENGE 1 (Better Complexity): Merge sort achieves O(n log n)...
CHALLENGE 2 (Different Approach): Quicksort also O(n log n)...
...
```

---

*See SKILLS.md for full documentation | .claude/skills/CS500/ for skill definitions*
