---
name: algorithmic_analysis
description: Applies algorithm design theory including asymptotic analysis, data structure selection, and correctness proofs. References Knuth TAOCP, CLRS, Sedgewick. Use for complexity analysis and algorithmic correctness.
allowed-tools: Read, Grep, Glob, WebFetch, WebSearch
---

# Algorithmic Analysis

## Purpose

Provide rigorous theoretical analysis of algorithms including time/space complexity, correctness proofs, and data structure selection.

## Methodology: Discovery, Not Justification

**CRITICAL**: This skill performs DISCOVERY, not post-hoc rationalization.

- **Wrong approach**: "Use O(N log k) heap algorithm. Let me prove it's optimal."
- **Right approach**: "Establish lower bound. Explore candidates. Discover which achieve bound."

Process:
1. Lower bounds BEFORE specific algorithms
2. Multiple candidates WITHOUT assuming winner
3. Systematic comparison reveals optimal solutions
4. Defer constant factors to comparative_complexity skill

## Instructions

When activated, perform in two phases:

### Phase A: Lower Bound Analysis (Discovery)

Establish theoretical limits BEFORE analyzing specific algorithms:

1. **Comparison Lower Bound**
   - Use decision tree argument for comparison-based algorithms
   - Count possible outputs (e.g., multinomial coefficients for k-way merge)
   - Compute log₂(outputs) for comparison lower bound
   - Information-theoretic argument

2. **Space Lower Bound**
   - Minimum state required (e.g., k iterator positions)
   - Memory access patterns

3. **Establish Ω() Bounds**
   - Worst-case lower bound
   - Average-case if applicable
   - Prove bounds are tight (achievable)

### Phase B: Candidate Algorithm Analysis (Discovery)

Systematically explore multiple approaches WITHOUT assuming answer:

1. **Literature Review (REQUIRED FIRST)**

   **CRITICAL**: Do NOT enumerate candidates from memory alone. Research comprehensively.

   **Canonical References** (consult training data):
   - **Knuth TAOCP**: Search relevant volumes for problem domain
   - **CLRS**: Search relevant chapters for related structures/algorithms
   - **Sedgewick**: Domain-specific patterns if applicable
   - **Classic papers**: If problem is well-studied

   **Web Research Strategy**:
   - WebSearch: "[problem description] algorithms survey"
   - WebSearch: "[problem description] optimal complexity"
   - WebSearch: "arxiv [problem keywords]" for recent papers
   - WebSearch: "[data structure] variants" for known structures

   **What to Extract**:
   - All algorithm names mentioned for this problem
   - Variants (e.g., binary vs d-ary, winner vs loser)
   - Classic vs modern approaches
   - Trade-offs mentioned in literature

   **Documentation in Output**:
   - Cite sources for each algorithm found
   - Note which reference discusses which variant
   - Format: "Source: TAOCP Vol X §Y" or "Source: [Author Year]"

2. **Enumerate Candidates** (from research + first principles)
   - Minimum 5-8 candidates including:
     * Naive approach (baseline - what's simplest?)
     * Standard textbook solutions (from TAOCP/CLRS)
     * Variants mentioned in literature
     * First-principles alternatives
     * Related problem solutions adapted to this problem

3. **Analyze Each Candidate**
   - Time complexity (best, average, worst)
   - Space complexity (auxiliary + total)
   - Does it satisfy problem constraints? (lazy evaluation, etc.)
   - Does it achieve lower bound?
   - Cite source if from literature (e.g., "TAOCP §5.4.1")

4. **Comparison Table**
   - Create table comparing all candidates
   - Columns: Algorithm, Time, Space, Constraints Met, Optimal?, Source
   - Identify which (if any) achieve lower bound

5. **Correctness Proof** (for optimal candidates only)
   - Loop invariants for iterative algorithms
   - Induction for recursive algorithms
   - Termination argument
   - Proof that output satisfies postconditions

6. **Constant Factors** (for optimal candidates)
   - Note hidden constants in Big-O
   - Memory overhead per element/node
   - Number of comparisons/swaps in practice

7. **Output Structure**

Phase A (lower-bound.tex):
```
RESEARCH QUESTION: What is theoretically impossible to beat?

LOWER BOUND ANALYSIS:
- Problem: ...
- Possible outputs: ... (count using combinatorics)
- Decision tree depth: log₂(outputs) = ...
- Theorem: Ω(...) comparisons required

PROOF:
- Decision tree model
- Leaf counting
- Information-theoretic argument

SPACE BOUND: Ω(...) required because ...

CONCLUSION: Bounds are tight (will be achieved in Phase B)
```

Phase B (candidate-algorithms.tex):
```
RESEARCH QUESTION: Which algorithms (if any) achieve the lower bound?

CANDIDATE 1: [Naive approach]
- Time: ...
- Space: ...
- Achieves bound? No
- Why: ...

CANDIDATE 2: [Standard approach]
- Algorithm: ...
- Time: ...
- Space: ...
- Correctness: Invariant + Proof
- Achieves bound? Yes/No

[Continue for 4-6 candidates]

COMPARISON TABLE:
| Algorithm | Time | Space | Constraints | Optimal? |
|-----------|------|-------|-------------|----------|
| ...       | ...  | ...   | ...         | ...      |

DISCOVERY: N algorithms achieve lower bound: [...list...]

CONCLUSION:
- Optimal algorithms identified
- Defer constant factor comparison to Stage 3
```

## Output Template

### Phase A: Lower Bound (lower-bound.tex)

```latex
\section{Research Question}
From Stage 1: ``What is the minimum [resource] required for [problem]?''

\section{Problem Setup}
- Input characteristics: ...
- Output requirements: ...
- Constraints: ...

\section{Lower Bound via [Method]}
[Use decision tree, adversary argument, information theory, etc.]

\subsection{Counting Possible Outputs}
[Combinatorial argument for number of valid outputs]

\begin{lemma}[Output Count]
Number of distinct outputs is [formula with justification]
\end{lemma}

\subsection{Lower Bound Theorem}
\begin{theorem}[Comparison/Time Lower Bound]
Any algorithm for this problem requires at least Ω(...) [comparisons/time]
\end{theorem}

\begin{proof}
[Decision tree depth / information-theoretic argument]
\end{proof}

\section{Space Lower Bound}
\begin{theorem}[Space Lower Bound]
At least Ω(...) auxiliary space required because [minimal state needed]
\end{theorem}

\section{Summary}
Lower bounds established:
- Time: Ω(...)
- Space: Ω(...)
- These bounds are tight (will be achieved in Phase B)
```

### Phase B: Candidate Algorithms (candidate-algorithms.tex)

```latex
\section{Research Question}
From Stage 2A: ``Which algorithms (if any) achieve the Ω(...) lower bound?''

\section{Literature Review}
Sources consulted:
- [Reference 1]: [what it covers]
- [Reference 2]: [what it covers]
- WebSearch results: [keywords used, findings]

Algorithms discovered in literature: [list]
Variants mentioned: [list]

\section{Candidate Approaches}

\subsection{Candidate 1: [Naive/Baseline Name]}
\textbf{Algorithm}: [description]
\textbf{Time}: [analysis]
\textbf{Space}: [analysis]
\textbf{Source}: First principles / [citation]
\textbf{Achieves bound?} Yes/No [explanation]

\subsection{Candidate 2: [Standard Approach]}
\textbf{Algorithm}: [description]
\textbf{Time}: [analysis]
\textbf{Space}: [analysis]
\textbf{Source}: [TAOCP / CLRS / paper citation]
\textbf{Correctness}:
  Invariant: [formal statement]
  Proof: [sketch]
\textbf{Achieves bound?} Yes/No

[Continue for 5-8 candidates total]

\section{Summary Table}
\begin{table}
| Algorithm | Time | Space | Constraints | Optimal? | Source |
|-----------|------|-------|-------------|----------|--------|
| ...       | ...  | ...   | ...         | ...      | ...    |
\end{table}

\section{Conclusion}
\textbf{Discovery}: [N] algorithms achieve Ω(...) lower bound:
1. [Algorithm 1] - [key characteristic]
2. [Algorithm 2] - [key characteristic]
...

\textbf{Next Steps}:
- Multiple optimal solutions found
- Constant factor/practical comparison deferred to Stage 3
- Open question: Which is best in practice?
```

### Example 2: Lazy FlatMap

```
ALGORITHM: Nested iterator with on-demand expansion

TIME COMPLEXITY:
- Best: O(1) if first element immediately available
- Average: O(m) where m = average inner iterator size (amortized O(1) per element)
- Worst: O(M) where M = max inner iterator size for single next() call
- Total: O(N) for N total elements across all inner iterators

SPACE COMPLEXITY:
- Auxiliary: O(1) (only current inner iterator reference)
- Total: O(d) where d = recursion depth if nested flatMaps

CORRECTNESS:
Invariant: currentInner = null ∨ currentInner.hasNext() ∨ outer.exhausted

Proof:
1. Initially: currentInner = null, outer points to first element
2. On next():
   a. If currentInner != null && hasNext(): return currentInner.next()
   b. Else: advance outer, apply function, set currentInner, recurse
3. Invariant: Either we have an element ready, or we're fetching the next inner
4. Termination: outer exhausted && currentInner exhausted
5. Lazy evaluation: function applied only when outer element accessed

Amortized Analysis:
- Each element returned: O(1)
- Inner iterator transition: O(1) amortized (bounded by number of outer elements)
- Total: O(N) for N elements

DATA STRUCTURES:
- Choice: Two iterator references (outer, currentInner)
- Justification:
  * No buffering required
  * Minimal memory footprint
  * True lazy evaluation

CONSTANT FACTORS:
- Per next(): 1-2 null checks, 1 function application (amortized)
- Memory: 2 references = 16 bytes (64-bit)
- No allocation unless function allocates

THEORETICAL NOTES:
- This is a catamorphism (fold) in category theory
- Maintains monad laws: flatMap(f).flatMap(g) = flatMap(x → f(x).flatMap(g))
- Deforestation: Chained flatMaps can fuse in optimizing compilers
```

## References

- **Knuth TAOCP**: Vol 1 (data structures), Vol 3 (sorting/searching)
- **CLRS**: Heap operations (Ch 6), Amortized analysis (Ch 17)
- **Sedgewick**: Iterator patterns, lazy evaluation

## Cross-Skill Integration

Requires:
- **problem_specification**: Formal spec before analysis

Feeds into:
- **comparative_complexity**: Provides base analysis for comparison
- **microarchitectural_modeling**: Theoretical → practical mapping
- **technical_exposition**: Analysis forms core of writeup

## Notes

- Distinguish asymptotic from constant factors
- Amortized analysis for sequences of operations
- Cache complexity (I/O model) for large datasets
- Probabilistic analysis for randomized algorithms

### Two-Document Pattern

For complex problems, split into separate LaTeX documents:

1. **lower-bound.tex**: Establishes Ω() bounds via decision trees, information theory
2. **candidate-algorithms.tex**: Analyzes 4-6 approaches, comparison table, discovery

Benefits:
- Separation of concerns (theory vs algorithms)
- Avoids assuming solution while proving lower bound
- Clear discovery narrative: limits → candidates → optimal solutions
- Matches research methodology: establish bounds THEN find algorithms achieving them
