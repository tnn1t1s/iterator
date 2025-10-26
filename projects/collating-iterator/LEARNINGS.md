# Learnings: INDEX.html Design for Research Artifacts

## Context
Iteratively refined INDEX.html landing page for collating-iterator research project to be scannable by L7/L8 senior engineers.

## Key Insights

### 1. Hegelian Dialectic Structure Works
**Discovery**: Splitting "Review Summary" into two sections created clarity
- **Thesis**: Original Prompt (what was asked)
- **Antithesis**: Candidate Submission (what was delivered)
- **Synthesis**: Reviewer Assessment (independent critique + verdict)

**Why it matters**: Separates submission from critique, making bias transparent

### 2. Outline Must Come BEFORE Details
**Mistake**: Initially removed stage outline entirely
**Fix**: Placed "Stages Overview" between assessment and stage details
**Lesson**: Readers need roadmap before diving into content

### 3. Scannability > Completeness
**Pattern**: L7/L8 reviewers scan hundreds of artifacts
**Requirements**:
- 3 bullets max per section
- Specific algorithm names (LinearScan, HeapBased, LoserTree) not generic labels
- Quantitative claims (70 tests, 50% speedup, 7.4/10)
- No dense paragraphs

### 4. Praise Good Choices with Evidence
**Mistake**: Initially wrote "naive, standard, optimized"
**Fix**: "LinearScan (naive baseline), HeapBased (standard approach), LoserTree (selected based on Grafana 2024 production validation showing 50% speedup)"
**Lesson**: Assess candidate decisions, cite production validation

### 5. Clinical Third-Person Tone
**Reference**: "like Freud or Derrida analyzing a case"
**Application**: Detached observer documenting what was submitted vs what gaps exist
**Avoids**: Cheerleading, vague praise, personal voice

### 6. Each Stage Needs Three Elements
**Structure**:
1. Link to detailed HTML
2. One-paragraph inline summary
3. Full verbatim content from original file

**Why**: Quick scan (summary) + deep dive (verbatim) in same page

### 7. Section Names Must Be Concrete
**Bad**: "Review Summary" (vague, what happened?)
**Good**: "Candidate Submission" + "Reviewer Assessment" (clear roles)
**Lesson**: Avoid abstract meta-labels

## Document Structure That Worked

```
1. Original Prompt (blockquote)
2. Candidate Submission (3 bullets)
3. Reviewer Assessment (deficiencies + verdict)
4. Stages Overview (10-item outline with links)
5. Stage 1: Title (link + summary + verbatim)
6. Stage 2: Title (link + summary + verbatim)
   ...
10. Stage 10: Title (link + summary + verbatim)
```

## Skills Updated
- `.claude/skills/CS500/technical_exposition/SKILL.md` section 0a
- Documented format to prevent p-hacking on future projects
- Added anti-pattern: "Combining submission and assessment in one section"

## What Changed Across Iterations

| Iteration | Problem | Fix |
|-----------|---------|-----|
| 1 | Gray text on white unreadable | Custom CSS, high contrast |
| 2 | TL;DR + collapsed prompt "stupid" | Remove TL;DR, show prompt |
| 3 | Abstract jargon not scannable | Concrete facts, quantitative |
| 4 | Dense paragraphs | Bullet lists |
| 5 | Generic algorithm labels | Specific names + evidence |
| 6 | Too many bullets (5) | Constrain to 3 |
| 7 | "Review Summary" vague | Split into two sections |
| 8 | No outline visible | Add "Stages Overview" before details |

## Reusable Patterns

### For Future Research Artifacts
1. Always use dialectic structure (prompt → submission → assessment)
2. Outline before details
3. 3-bullet constraint on summaries
4. Name specific algorithms/choices, not generic labels
5. Cite production validation evidence
6. Clinical third-person tone
7. Each stage: link + summary + verbatim

### For Senior Reviewers (L7/L8)
- Can scan in 30 seconds: prompt → what was built → what's wrong → verdict
- Outline shows scope
- Can drill into any stage
- Evidence-based claims (not handwaving)

## Next Steps
- Apply this format to future research projects
- Template generation from technical_exposition skill
- Consider automating outline generation from stage directories
