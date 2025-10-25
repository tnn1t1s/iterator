# Research Projects

This directory contains complete research artifacts demonstrating systematic methodology from problem specification through implementation, testing, benchmarking, and critical evaluation.

## What You're Looking At

Each project follows a rigorous pipeline:
- **Specification**: Formal problem definition with research questions (not prescriptive solutions)
- **Analysis**: Lower bounds, candidate algorithms, literature review (TAOCP, CLRS, arxiv)
- **Design**: Comparative analysis with production validation
- **Implementation**: Multi-variant approach (baseline, standard, optimized)
- **Testing**: Comprehensive test suites with shared base patterns
- **Benchmarking**: Systematic test data design + pragmatic execution
- **Validation**: Cross-artifact consistency checks
- **Summary**: Scannable one-page format for senior reviewers (L7/L8)
- **Critique**: Independent evaluation identifying strengths and gaps
- **Scoring**: Skills assessment (1-10 scale)

**Target audience**: Senior engineers (L7/L8) reviewing research artifacts, technical interviewers evaluating systematic thinking, educators demonstrating CS500-level methodology.

**Philosophy**: Question before solution. Empirical validation of theory. Multiple implementations for comparison. Honest acknowledgment of limitations.

## Project Index

### [collating-iterator](collating-iterator/INDEX.html)
**Status**: Complete (10 stages) | **Assessment**: Mean 7.4/10, "Conditional Hire - Technical Deep-Dive Required"

**Problem**: Design efficient k-way merge for sorted iterators

**Solution**: 3 algorithm variants (LinearScan O(Nk), HeapBased O(N log k), LoserTree O(N log k))

**Deliverables**:
- 70 passing JUnit tests (shared base pattern)
- JMH benchmark infrastructure
- Systematic test data catalog (24 scenarios across 5 dimensions)
- Git-committable artifacts with full documentation

**Key findings**:
- Loser tree selected based on Grafana 2024 production validation (50% speedup)
- Literature review (TAOCP, CLRS, arxiv) identified 4 optimal algorithms
- Multi-variant strategy enables empirical comparison

**Critical gaps identified**:
- Loser tree implementation bug (O(k) instead of O(log k) refill)
- Build tooling struggles (reverted to javac hacks)
- Comparison count not instrumented
- Production concerns missing (error handling, thread safety)

**Skills demonstrated**: Exceptional arxiv research (9/10), test data design (9/10). Strong theoretical analysis, systematic methodology.

---

**Framework**: 19 CS500 skills in `.claude/skills/CS500/` - see [SKILL_INDEX.md](../.claude/skills/CS500/SKILL_INDEX.md)
