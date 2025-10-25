# Iterator: Systematic Research via Anthropic Skills

**Double meaning**:
1. **Topic**: K-way merge of sorted iterators (first research challenge)
2. **Method**: Iterative optimization toward objective function (research methodology)

**Philosophy**: Question before solution. Iterate toward optimal. Empirical validation of theory.

---

## What Is This?

A systematic research framework built on [Anthropic's Claude Skills](https://www.anthropic.com/news/skills), demonstrating:

- **10-stage research pipeline**: Specification → Analysis → Design → Implementation → Testing → Benchmarking → Validation → Summary → Critique → Scoring
- **Multi-variant approach**: Baseline, standard, optimized implementations for empirical comparison
- **Production validation**: Literature review (TAOCP, CLRS, arxiv) + real-world deployment evidence
- **Iterative optimization**: "Can we do better?" until objective reached
- **Honest assessment**: Independent critique identifying strengths and gaps

**Not**: Interview "answers" to memorize. This is methodology for discovering solutions.

---

## Quick Start

**Newcomers**: Read [SKILLS.md](SKILLS.md) - Beginner-friendly guide to all 19 skills

**View example**: [projects/collating-iterator/INDEX.html](projects/collating-iterator/INDEX.html) - Complete artifact with assessment

**Understand methodology**: [CLAUDE.md](CLAUDE.md) - Design decisions, anti-patterns, principles

---

## Why "Iterator"?

### Meaning 1: The Problem Domain
First challenge implements **k-way merge of sorted iterators**:
- Input: k sorted `Iterator<T>` streams
- Output: Single sorted merged `Iterator<T>`
- Challenge: What's optimal? How do we know?

Iterator protocol chosen because:
- Core to streaming systems (log merging, time series, database scans)
- Forces lazy evaluation thinking
- Production relevance (Grafana Loki/Pyroscope, Apache DataFusion)

### Meaning 2: The Research Method
**Iterative optimization toward objective function**:

```
while (!objective_reached) {
    current_solution = analyze(problem);
    if (can_do_better(current_solution, objective)) {
        iterate();  // Challenge current approach
    } else {
        validate(current_solution);
        break;
    }
}
```

**Objective function**: Maximize hire probability for senior role
- Systematic methodology > memorized answers
- Multi-variant empirical comparison > single "right answer"
- Production validation > textbook claims
- Honest gap acknowledgment > self-congratulation

**Iteration mechanism**: `iterative_optimizer` skill challenges solution 5 levels:
1. Complexity improvements (O notation)
2. Constant factor optimizations
3. Radical algorithm changes
4. Microarchitectural tuning
5. Theoretical limits

Never stops until you say stop. Simulates relentless senior interviewer.

---

## Anthropic Skills: What Are They?

**Announced**: October 2025 | **Available**: Claude Pro, Team, Enterprise

From [Anthropic's announcement](https://www.anthropic.com/news/skills):

> "Skills are folders that include instructions, scripts, and resources that Claude can load when needed. Think of them as specialized expertise you can give Claude for specific tasks."

### How Skills Work

**Progressive disclosure architecture**:
1. **Startup**: Claude loads skill names + descriptions only (lightweight)
2. **Trigger**: User request matches skill description
3. **Load**: Skill instructions → reference files → scripts (as needed)
4. **Execute**: Skill runs within tool restrictions
5. **Clean**: Context cleared after task completes

**Key features**:
- **Composable**: Skills stack automatically. Claude coordinates which skills work together.
- **Portable**: Same format works in Claude.ai, Claude Code, and API
- **Efficient**: Load only what's needed, when it's needed

**Example**: Instead of telling Claude "analyze this algorithm's complexity", you have an `algorithmic_analysis` skill that knows exactly how to:
- Prove lower bounds via decision trees
- Analyze time/space complexity
- Write correctness proofs
- Reference canonical sources (TAOCP, CLRS)

### Skills vs Prompts

| Traditional Prompting | Skills |
|----------------------|---------|
| Re-explain every time | Instructions packaged once |
| Full context loaded | Progressive disclosure |
| Manual coordination | Automatic composition |
| Session-specific | Portable across sessions |

### Skills vs MCP (Model Context Protocol)

**MCP**: Connects Claude to external data/tools (databases, APIs, browsers)
**Skills**: Teaches Claude specialized workflows and methodologies

**Complementary**: Skills often *use* MCP tools. Example: `arxiv_research` skill uses web search MCP to find production validation.

---

## CS500 Skills Framework

**19 specialized skills** organized in 7 categories:

### Core Research (3 skills)
- `problem_specification` - Formal specs with research questions
- `algorithmic_analysis` - Lower bounds, complexity, correctness
- `comparative_complexity` - Multi-design comparison tables

### Design Optimization (3 skills)
- `systems_design_patterns` - Production-validated selection
- `microarchitectural_modeling` - Cache, branch prediction, ILP
- `language_comparative_runtime` - Java vs C++ vs Rust

### Iterative Improvement (1 skill)
- `iterative_optimizer` - "Can we do better?" (5 challenge levels)

### Implementation (3 skills)
- `java_codegen` - Multi-variant Java implementations
- `unit_test_generation` - JUnit with shared test base
- `safety_invariants` - Assertions, invariant checking

### Benchmarking (3 skills)
- `benchmark_design` - JMH controlled benchmarks
- `test_data_design` - Systematic test data across dimensions
- `performance_interpretation` - Bottleneck analysis

### Documentation (2 skills)
- `technical_exposition` - Academic papers + scannable summaries
- `pedagogical_reflection` - Lessons learned, future work

### Orchestration (4 skills)
- `research_to_code_pipeline` - 8-stage coordinator
- `self_consistency_checker` - Theory ↔ code validation
- `skill_context_cache` - Cross-stage memory
- `arxiv_research` - Modern literature survey

**Learn more**: [SKILLS.md](SKILLS.md) - Beginner guide with examples and triggers

**Technical reference**: [.claude/skills/CS500/SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md)

---

## Projects

All projects follow the 10-stage research pipeline.

### [collating-iterator](projects/collating-iterator/INDEX.html)
**Status**: Complete | **Assessment**: Mean 7.4/10, "Conditional Hire"

**Problem**: K-way merge for sorted iterators

**Solution**: 3 variants (LinearScan O(Nk), HeapBased O(N log k), LoserTree O(N log k))

**Results**:
- 70 passing JUnit tests (shared base pattern)
- JMH benchmark infrastructure
- Systematic test data catalog (24 scenarios)
- Loser tree selected based on Grafana 2024 production validation

**Critical gaps identified**:
- Loser tree implementation bug (O(k) instead of O(log k))
- Build tooling struggles (javac hacks)
- Comparison count not instrumented
- Production concerns missing

**Skills demonstrated**: Exceptional arxiv research, test data design. Strong theoretical analysis.

**View**: [projects/collating-iterator/INDEX.html](projects/collating-iterator/INDEX.html)

### More projects coming...

See [projects/README.md](projects/README.md) for full index.

---

## Repository Structure

```
iterator/
├── README.md                      # This file
├── CLAUDE.md                       # Methodology guide
├── SKILLS.md                       # Beginner-friendly skills guide
├── .claude/
│   └── skills/CS500/              # 19 specialized skills
│       ├── SKILL_INDEX.md         # Technical reference
│       ├── problem_specification/
│       ├── algorithmic_analysis/
│       ├── iterative_optimizer/
│       └── ... (16 more)
├── projects/
│   ├── README.md                  # Project index
│   └── collating-iterator/       # First research artifact
│       ├── INDEX.html             # Entry point
│       ├── 01-specification/
│       ├── 02-analysis/
│       ├── ... (through 10-scoring)
│       └── 04-implementation/java/ # Code + tests
└── docs/                          # Reference documentation
```

---

## Usage

### Run Full Pipeline
```
"Use research_to_code_pipeline skill to solve: [problem description]"
```

### Use Individual Skills
```
"Can we do better?"               → iterative_optimizer
"Formalize this problem"          → problem_specification
"Search arxiv for validation"     → arxiv_research
"Implement in Java"               → java_codegen
"Design systematic test data"     → test_data_design
```

### Create New Project
```bash
mkdir -p projects/my-problem/{01-specification,02-analysis,03-design,04-implementation,05-testing,06-benchmarking,07-validation,08-summary,09-critique,10-scoring}
```

---

## Key Principles

### 1. No Solution Leak
**Anti-pattern**: "Implement O(N log k) heap-based merge"
**Correct**: "What is minimum achievable complexity? Which algorithms reach it?"

### 2. Multi-Variant Implementation
**Anti-pattern**: Only implement "the answer"
**Correct**: Baseline + Standard + Optimized for empirical comparison

### 3. Production Validation
**Anti-pattern**: "Textbook says X is optimal"
**Correct**: "Grafana 2024 production: 50% speedup [cite]"

### 4. Scannable Summaries
**Anti-pattern**: Dense paragraph prose
**Correct**: Bullets, quantitative (50% speedup, 70 tests), 30-second grok

### 5. Honest Limitations
**Anti-pattern**: "Successfully implemented optimal solution"
**Correct**: "Loser tree has O(k) bug, build tooling struggles, comparison count not instrumented"

### 6. Iterative Optimization
**Anti-pattern**: Accept first working solution
**Correct**: "Can we do better?" until objective function maximized

---

## Contributing

### Add Your Research Artifact
1. Create project directory: `projects/your-problem/`
2. Follow 10-stage structure (see `projects/collating-iterator/`)
3. Use `research_to_code_pipeline` skill or build stages manually
4. Submit PR with scannable summary in `projects/README.md`

### Improve Existing Skills
1. Identify gap in existing skill (see `10-scoring/SCORECARD.md` for examples)
2. Edit skill in `.claude/skills/CS500/skill-name/SKILL.md`
3. Test with relevant project
4. Submit PR with before/after comparison

### Create New Skill
1. Study existing skills in `.claude/skills/CS500/`
2. Create `new-skill/SKILL.md` with YAML frontmatter
3. Document: purpose, inputs, outputs, usage examples
4. Add to `SKILL_INDEX.md`
5. Submit PR

### Guidelines
- **Beginner-friendly**: Skills should explain "why" not just "how"
- **Composable**: Skills should call other skills (not duplicate)
- **Measurable**: Include success criteria (how do we know it worked?)
- **Honest**: Document limitations and failure modes

---

## License

**Framework**: MIT License

**Philosophy**: Research mindset beats memorization.

**Attribution**: Built with [Claude Code](https://claude.com/claude-code) + [Anthropic Skills](https://www.anthropic.com/news/skills)

---

## Resources

### Documentation
- [CLAUDE.md](CLAUDE.md) - Methodology guide (what & why)
- [SKILLS.md](SKILLS.md) - Beginner guide (how & which)
- [projects/README.md](projects/README.md) - Project index
- [.claude/skills/CS500/SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md) - Technical reference

### External Links
- [Anthropic Skills Announcement](https://www.anthropic.com/news/skills)
- [Claude Skills GitHub](https://github.com/anthropics/skills)
- [Simon Willison: Claude Skills](https://simonwillison.net/2025/Oct/16/claude-skills/)
- [InfoQ: Anthropic Introduces Skills](https://www.infoq.com/news/2025/10/anthropic-claude-skills/)

### Learning Path
1. **New to skills?** → [SKILLS.md](SKILLS.md)
2. **See example** → [projects/collating-iterator/INDEX.html](projects/collating-iterator/INDEX.html)
3. **Understand methodology** → [CLAUDE.md](CLAUDE.md)
4. **Technical deep-dive** → [.claude/skills/CS500/SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md)
5. **Try it yourself** → "Use research_to_code_pipeline skill to solve: [problem]"

---

## Contact

**Issues**: [GitHub Issues](https://github.com/tnn1t1s/iterator/issues)

**Philosophy questions**: See [CLAUDE.md](CLAUDE.md) design decisions section

**Skill questions**: See [SKILLS.md](SKILLS.md) common beginner questions

---

**Last updated**: October 2025
**Repository**: https://github.com/tnn1t1s/iterator
