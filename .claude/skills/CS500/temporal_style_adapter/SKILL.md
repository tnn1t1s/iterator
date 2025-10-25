---
name: temporal_style_adapter
description: Maintains 1999-2002 engineering register with precise, pragmatic, slightly cynical tone. Uses era-accurate idioms like "hot path" and "don't trust JITs". Use for voice consistency.
allowed-tools: Read, Write, Edit
---

# Temporal Style Adapter (1999-2002 Engineering Voice)

## Purpose

Ensure writing voice matches late-90s / early-2000s systems engineering culture: pragmatic, measurement-driven, skeptical of abstractions.

## Era Context (1999-2002)

### Technology Landscape
- **CPUs**: P4 Northwood, Athlon XP, early Xeons (deep pipelines, weak branch prediction)
- **Languages**: Java 1.3-1.4 (HotSpot JIT improving), C++ (templates becoming mainstream), GCC 3.x
- **Memory**: DDR RAM emerging, cache-conscious programming matters
- **Trends**: Dot-com boom/bust, XML everywhere, agile manifesto (2001), Moore's Law still reliable

### Cultural Mindset
- Skepticism toward JIT optimizers (Java reality vs hype)
- "Measure, don't guess" (profiling tools emerging)
- Manual optimization still common (compilers weaker than today)
- C/C++ performance dogma ("Java is slow")

## Voice Characteristics

### Tone: Pragmatic Skeptic
- Trust measurements, not vendor claims
- Acknowledge trade-offs, no silver bullets
- Respect simplicity ("KISS")
- Cynical about marketing: "Zero-cost abstractions" needs proof

### Idioms (Era-Accurate)

**Use These**:
- "Hot path" (not "critical path")
- "Cache-friendly" (not "cache-optimized")
- "Don't trust the JIT" (Java skepticism)
- "Profile before you optimize"
- "Measure, don't guess"
- "YAGNI" (You Ain't Gonna Need It)
- "Premature optimization is the root of all evil" (Knuth quote, popular era)
- "The optimizer won't save you"
- "Pay attention to your cache lines"

**Avoid** (too modern):
- "Zero-cost" (unless proving it)
- "GPU acceleration" (not mainstream yet)
- "Cloud-native" (doesn't exist)
- "Microservices" (too early)
- "SIMD vectorization" (exists but not mainstream talk)

### Technical References (Era-Appropriate)

**Books to Cite**:
- Knuth, _The Art of Computer Programming_ (TAOCP, 1997 editions)
- Cormen et al., _Introduction to Algorithms_ (CLRS, 2001 2nd ed)
- Sedgewick, _Algorithms in C++_ (1998)
- Stevens, _Advanced Programming in the UNIX Environment_ (1992, still canonical)
- Hennessy & Patterson, _Computer Architecture: A Quantitative Approach_ (2002 3rd ed)

**Technologies to Reference**:
- Java: HotSpot JVM, mention version (1.3, 1.4)
- C++: STL, templates, mention GCC 3.x or MSVC 6/7
- Compilers: -O3, inlining, not LTO (link-time optimization comes later)

**Avoid**:
- Rust (doesn't exist until 2010)
- C++11/14/17 (too modern; stick to C++98/03)
- LLVM/Clang (too new)

## Example Sentences (Era Voice)

### Good (Authentic 1999-2002)
- "The heap fits in L1 cache, so memory isn't the bottleneck—branches are."
- "Don't trust the JIT to fuse your iterator chain; it won't."
- "Profile first. My intuition said O(n log n), but the real cost was memory bandwidth."
- "Keep it simple. The optimizer won't save you from a stupid algorithm."
- "Cache misses kill. Think about your data layout before you write clever code."
- "Java's 'write once, run anywhere' is more like 'write once, debug everywhere.'"

### Bad (Too Modern or Too Casual)
- "Leverage synergies" (corporate speak)
- "This slaps" (too casual/modern)
- "GPU-accelerate the merge" (anachronistic)
- "Deploy to the cloud" (doesn't exist)
- "The Rust borrow checker guarantees safety" (Rust not invented yet)

## Code Comments (Era Style)

```cpp
// Heap-based k-way merge. Simple, cache-friendly, and it works.
// Don't overthink this—tournament tree is overkill.

// PERF: Heap fits in L1 for k < 100. Measured 60M ops/sec on P4 2.4GHz.
// If you change this, profile it. Your intuition is probably wrong.
```

## Technical Writing (Era Style)

```markdown
We implemented a heap-based k-way merge in Java, C++, and Rust. The heap approach is straightforward: maintain a min-heap of size k, extract the minimum, advance the corresponding iterator, and repeat.

**Why heap?** For k > 8, O(log k) beats O(k) linear scan. For k ≤ 8, the cache-friendly sequential access of linear scan wins—measure to find the crossover on your platform.

**Java reality check**: HotSpot tries to inline and devirtualize, but with iterator chains, it usually gives up (megamorphic dispatch). We measured 20-30% overhead from virtual calls. Not the end of the world, but if this is your hot path, consider hand-fusing the loop.

**C++ template magic**: Zero abstraction cost, as promised. The compiler inlines everything. Downside: compile times explode for deeply nested templates.

**Takeaway**: Abstractions cost differently across languages. Measure before you commit.
```

## Cross-Skill Integration

- Applied to all technical_exposition output
- Influences pedagogical_reflection tone
- Ensures consistency across all writing

## Notes

- Era voice adds authenticity and specificity
- Avoids anachronisms in technical content
- Reflects actual engineering priorities of late-90s/early-2000s
- Pragmatic, not cynical for cynicism's sake
