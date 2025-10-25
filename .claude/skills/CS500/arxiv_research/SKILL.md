---
name: arxiv_research
description: Searches arxiv and modern academic literature for recent work beyond classical textbooks. Takes candidate algorithms from algorithmic_analysis and finds newer alternatives, optimizations, and related work.
allowed-tools: WebSearch, WebFetch, Read, Write
---

# Arxiv Research

## Purpose

Bridge gap between classical textbooks (TAOCP, CLRS) and cutting-edge academic research. Find modern approaches, recent optimizations, and alternative solutions published after canonical references.

## When to Use

- After **algorithmic_analysis** Phase B completes
- Have candidate algorithms from textbooks
- Need to validate: "Is there newer work we're missing?"
- Before Stage 3 (design selection) to ensure comprehensive candidate set

## Methodology

**Input**: List of candidate algorithms from algorithmic_analysis
**Output**: Recent papers, modern variants, performance improvements, alternative approaches

## Instructions

### 1. Extract Search Keywords

From problem specification and candidate algorithms, identify:
- **Problem domain**: Core problem name (e.g., "merge sorted sequences", "dynamic connectivity")
- **Key operations**: What operations are critical? (e.g., "priority queue", "incremental update")
- **Constraints**: What makes this hard? (e.g., "cache-oblivious", "external memory", "parallel")
- **Classical solutions**: Names from textbooks to search variants of

### 2. Systematic Arxiv Search

**CRITICAL**: Use WebSearch with arxiv-specific queries

**Search Strategy** (execute all, don't stop early):

a. **Recent surveys**:
   - `"arxiv [problem domain] survey"`
   - `"arxiv [problem domain] algorithms recent"`

b. **Optimization papers**:
   - `"arxiv [classical algorithm name] optimization"`
   - `"arxiv cache-aware [problem domain]"`
   - `"arxiv cache-oblivious [problem domain]"`

c. **Modern hardware adaptations**:
   - `"arxiv SIMD [problem domain]"`
   - `"arxiv GPU [problem domain]"`
   - `"arxiv multicore [problem domain]"`

d. **Theoretical improvements**:
   - `"arxiv [problem domain] lower bound"`
   - `"arxiv optimal [problem domain]"`
   - `"arxiv [problem] complexity"`

e. **Related problem space**:
   - `"arxiv [related problem] algorithm"`
   - Search for problems that share data structures/techniques

### 3. Extract Information

For each relevant paper found:

```
PAPER: [Title]
AUTHORS: [Names]
YEAR: [Year]
ARXIV: [arxiv.org/abs/XXXX.XXXXX]

RELEVANCE:
- Addresses same problem? Yes/No/Related
- New algorithm? Name: [...]
- Optimization of existing? Which: [...]
- Theoretical result? What: [...]

KEY CONTRIBUTION:
- [1-2 sentence summary]

COMPLEXITY:
- Time: [...]
- Space: [...]
- Compared to classical: [improvement/different trade-off]

SHOULD WE ANALYZE?
- Yes (new candidate) / Maybe (optimization) / No (different problem)
- Reason: [...]
```

### 4. Categorize Findings

**New Candidates**: Full algorithms not in textbooks
- Add to candidate list for analysis

**Optimizations**: Improvements to existing algorithms
- Note for comparative_complexity stage

**Theoretical Results**: Lower bounds, impossibility
- Check against Phase A lower bound analysis

**Related Work**: Different problem, but relevant techniques
- Document for future reference

**Dead Ends**: Not applicable
- Brief note why (avoid re-searching later)

### 5. Output Format

Create summary document (e.g., `02-analysis/arxiv-survey.md`):

```markdown
# Modern Literature Survey

## Search Date
[YYYY-MM-DD]

## Keywords Searched
- [keyword 1]
- [keyword 2]
...

## New Candidates Found

### [Algorithm Name]
- **Paper**: [Authors, Year, arxiv link]
- **Contribution**: [summary]
- **Complexity**: Time O(...), Space O(...)
- **Status**: Should add to candidate-algorithms.tex as CANDIDATE N
- **Notes**: [any caveats, assumptions, trade-offs]

## Optimizations of Existing Candidates

### Optimization: [name]
- **Improves**: [which classical algorithm]
- **Paper**: [citation]
- **Technique**: [what's different]
- **Benefit**: [theoretical or empirical]
- **Status**: Defer to Stage 3 or mention in Stage 7 report

## Theoretical Results

### [Result]
- **Paper**: [citation]
- **Finding**: [new lower bound / impossibility / etc.]
- **Impact**: [does it change our analysis?]

## Related Work (Different Problem)

### [Topic]
- **Papers**: [list]
- **Relevance**: [why it might matter later]

## Search Dead Ends

- Query "[...]" → No relevant results
- Query "[...]" → All older than TAOCP/CLRS
- Query "[...]" → Different problem domain (not applicable)

## Recommendations

**Add to Analysis**:
1. [Algorithm X] - new candidate, should analyze
2. [Algorithm Y] - variant worth comparing

**Defer to Later Stages**:
1. [Optimization Z] - mention in Stage 3 comparative analysis
2. [Paper W] - cite in Stage 7 related work

**Archive for Future**:
- [Topic] - interesting but out of scope
```

## Integration with Pipeline

**Input from**: algorithmic_analysis (Phase B candidate list)

**Output to**:
- **algorithmic_analysis**: Add new candidates if found
- **comparative_complexity**: Note optimizations for comparison
- **technical_exposition**: Cite in related work section

**Timing**: Execute AFTER Phase B initial analysis, BEFORE finalizing candidate list

## Quality Checks

- [ ] Searched at least 5 different query formulations
- [ ] Checked papers from last 10 years minimum
- [ ] Extracted arxiv links for all relevant papers
- [ ] Categorized each finding (candidate/optimization/related/dead-end)
- [ ] Made explicit recommendation: analyze now vs defer vs ignore

## Notes

**Modern Focus**: This skill complements textbook research, doesn't replace it
- TAOCP/CLRS: Classical, proven, foundational
- Arxiv: Recent, experimental, cutting-edge

**Trade-offs**:
- Newer doesn't mean better (maturity matters)
- Arxiv papers vary in quality (not peer-reviewed yet)
- Cite with appropriate caveats

**When to Skip**:
- Problem is purely classical (sorting primitives, basic search)
- Textbook analysis already exhaustive
- Time constraints (can mention limitation in report)

## Cross-Skill Integration

**Requires**:
- **problem_specification**: Need problem definition to generate queries
- **algorithmic_analysis** Phase B: Need baseline candidate list

**Feeds into**:
- **algorithmic_analysis**: May add candidates
- **comparative_complexity**: Modern optimizations to compare
- **technical_exposition**: Related work citations
