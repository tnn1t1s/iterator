---
name: research_project_layout
description: Generates filesystem-safe project names from problem descriptions and creates standardized research artifact directory structure. Use when starting a new research pipeline to organize outputs.
allowed-tools: Write, Bash, Read
---

# Research Project Layout Generator

## Purpose

Generate appropriate project names from natural language problem descriptions and create standardized directory structures for research artifacts.

## Instructions

When activated:

1. **Extract Project Name**
   - Parse problem description for key terms
   - Generate kebab-case name (e.g., "collating-iterator", "k-way-merge")
   - Keep length 2-4 words maximum
   - Ensure filesystem-safe (lowercase, hyphens, no spaces)

2. **Create Directory Structure**
   ```
   projects/<project-name>/
     01-specification/       # Formal problem definitions (LaTeX)
     02-analysis/           # Complexity proofs, comparisons (LaTeX)
     03-design/            # Design decisions, justifications (LaTeX)
     04-implementation/    # Source code in multiple languages
       java/               # Gradle project
       cpp/                # CMake project
       rust/               # Cargo project
     05-testing/           # Test strategies, results (Markdown)
     06-benchmarks/        # Benchmark scripts, data, plots
       scripts/
       results/
     07-report/            # Final technical paper (LaTeX)
     08-validation/        # Consistency checks (Markdown)
     README.md            # Project overview
   ```

3. **Generate README.md**
   - Project name and one-line description
   - Directory guide
   - Build instructions placeholders
   - Generated timestamp

4. **Return Project Path**
   - Output: Full path to project root
   - Used by other skills to write artifacts

## Naming Examples

| Input | Generated Name |
|-------|---------------|
| "CollatingIterator for k sorted sequences" | `collating-iterator` |
| "Implement priority queue with decrease-key" | `priority-queue-decrease-key` |
| "Binary search tree with lazy deletion" | `bst-lazy-deletion` |
| "Lock-free queue using CAS operations" | `lock-free-queue` |

## README Template

```markdown
# <Project Name>

**Generated**: <timestamp>
**Problem**: <one-line description>

## Structure

- `01-specification/` - Formal problem specification (LaTeX)
- `02-analysis/` - Algorithmic analysis and complexity proofs (LaTeX)
- `03-design/` - Design decisions and trade-offs (LaTeX)
- `04-implementation/` - Multi-language implementations
- `05-testing/` - Test strategies and results
- `06-benchmarks/` - Performance measurement framework
- `07-report/` - Complete technical report (LaTeX)
- `08-validation/` - Consistency verification

## Build Instructions

### Java
```bash
cd 04-implementation/java
./gradlew build test
```

### C++
```bash
cd 04-implementation/cpp
mkdir build && cd build
cmake ..
make
ctest
```

### Rust
```bash
cd 04-implementation/rust
cargo build
cargo test
```

## LaTeX Compilation

```bash
cd 07-report
pdflatex technical-report.tex
```
```

## Cross-Skill Integration

Used by:
- **research_to_code_pipeline**: First step to set up workspace
- All other skills: Write artifacts to appropriate directories

## Notes

- Creates empty directories immediately
- README.md helps navigate artifacts in git
- Standardized structure enables automated processing
- LaTeX sections compilable independently
