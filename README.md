# Iterator

**What is this?** A framework for systematic algorithm research using Claude AI.

**What does it do?** Takes a problem like "merge sorted lists efficiently" and walks through the complete research process: analyzing it, implementing multiple solutions, testing them, and honestly evaluating the results.

**Why "iterator"?** Two reasons:
1. The first project tackles iterator merging (the CS problem)
2. The method keeps asking "can we do better?" until you reach the goal (the research approach)

---

## See It In Action

**Example**: [K-way merge iterator](projects/collating-iterator/INDEX.html)
- Started with: "How do we efficiently merge multiple sorted streams?"
- Ended with: 3 different implementations, 70 tests, honest assessment (7.4/10 with identified bugs)
- Time: Full pipeline with minimal human input

**Not**: Pre-written answers to memorize.
**Is**: A method for discovering and validating solutions.

---

## For Different Audiences

**Just browsing?** → Look at the [example project](projects/collating-iterator/INDEX.html) to see what this produces

**Want to understand the approach?** → Read [CLAUDE.md](CLAUDE.md) for methodology and principles

**Ready to use it?** → Read [SKILLS.md](SKILLS.md) to learn the 19 skills you can use

**Technical deep dive?** → See [.claude/skills/CS500/SKILL_INDEX.md](.claude/skills/CS500/SKILL_INDEX.md)

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

## What Are Anthropic Skills?

**Simple explanation**: Skills are packaged instructions that teach Claude how to do specific tasks well.

**Why it matters**: Instead of re-explaining "how to analyze an algorithm" every time, you package those instructions once. Claude automatically uses them when needed.

**Example**:
- **Without skill**: "Claude, analyze the complexity of this algorithm. Make sure you check lower bounds using decision trees, cite TAOCP, and..."
- **With skill**: "Analyze this algorithm" → Claude automatically loads `algorithmic_analysis` skill which knows the process

**This project has 19 skills** covering everything from writing formal specifications to benchmarking code.

**Learn more**: [Anthropic's announcement](https://www.anthropic.com/news/skills) (October 2025)

---

## The 19 Skills (Quick Overview)

**Research skills** - Define and analyze problems
**Design skills** - Choose the right approach
**Implementation skills** - Write multi-variant code
**Testing skills** - Validate correctness and performance
**Documentation skills** - Explain findings clearly
**Orchestration skills** - Coordinate everything automatically

**One skill does one thing well.** Example: `iterative_optimizer` keeps asking "can we do better?" until you say stop.

**They work together.** The pipeline automatically coordinates all 19 skills from problem → solution → critique.

**See full list**: [SKILLS.md](SKILLS.md) has beginner-friendly descriptions of each skill

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

## Core Ideas

**Ask questions, don't prescribe answers** - Start with "what's the best approach?" not "implement a heap"

**Try multiple solutions** - Build baseline, standard, and optimized versions to compare

**Cite real production use** - "Grafana uses this" beats "textbook says so"

**Be scannable** - Bullets and numbers, not dense paragraphs

**Admit gaps** - "Here are the bugs I found" not "perfect implementation"

**Keep iterating** - "Can we do better?" until you hit the goal

---

## Contributing

**Add a research project**: Create `projects/your-problem/`, follow the 10-stage structure, submit PR

**Improve a skill**: Edit `.claude/skills/CS500/skill-name/SKILL.md`, test it, show before/after

**Create a new skill**: Study existing ones, write `SKILL.md`, add to index, submit PR

**Guidelines**: Make it understandable, composable, and honest about limitations

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
