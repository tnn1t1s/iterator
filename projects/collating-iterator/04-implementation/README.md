# Stage 4: Implementation

## Overview

Java implementation of CollatingIterator using **loser tournament tree** algorithm.

## Design Choice

**Algorithm**: Loser Tournament Tree (from Stage 3 decision)

**Why**:
- Production validated (Grafana 2024)
- O(N log k) time, O(k) space
- log k comparisons per element (vs 2 log k for heap)
- Simpler refill algorithm than winner tree
- Knuth's preference (TAOCP §5.4.1)

## Implementation

**File**: `java/src/main/java/com/research/iterator/CollatingIterator.java`

**Key features**:
- Implements `Iterator<T extends Comparable<? super T>>`
- Loser tree structure: internal nodes store losers, winner tracked separately
- Handles edge cases: empty iterators, single iterator, unequal lengths
- Null sentinels for exhausted iterators (always lose comparisons)

**Complexity**:
- Time: O(N log k) total, O(log k) per next()
- Space: O(k) for tree structure (k-1 internal nodes + k iterators)
- Lazy evaluation: Only pulls elements as needed

## Structure

```
java/
├── build.gradle          # Gradle build configuration
└── src/main/java/com/research/iterator/
    ├── CollatingIterator.java  # Main implementation
    └── Example.java            # Usage examples
```

## Building

```bash
cd java
./gradlew build
```

## Running Example

```bash
./gradlew runExample
```

**Example output**:
```
=== Basic K-Way Merge Example ===
Merged sequence: 1 2 3 4 5 6 7 8 9 10 11 12

=== Edge Cases ===
Single iterator:
alpha beta gamma

Empty iterator in mix:
1 2 3 4 5 6

Unequal lengths:
1 2 3 4 5 6 7 8 10

Large k (10 iterators):
0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 ... 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99
Total elements: 100
```

## Implementation Notes

### Loser Tree Algorithm

**Initialization** (buildTree):
1. Load first element from each iterator
2. Build tree bottom-up via tournament
3. Winners advance, losers stored in tree nodes
4. Final winner stored separately

**Refill** (after extracting winner):
1. Get next element from winner's source
2. Replay tournament: compare against losers on path to root
3. If new element loses, swap and continue with previous winner
4. Final value becomes new overall winner

**Key advantage over winner tree**: Only compare against losers (no sibling access needed)

### Edge Cases Handled

1. **Single iterator**: No tree built, direct passthrough
2. **Empty iterators**: Null sentinels (always lose)
3. **All exhausted**: hasNext() returns false
4. **Unequal lengths**: Works correctly as iterators exhaust

### Correctness Invariants

**Loop invariant**: At start of each next():
- Tree stores current losers from previous comparisons
- Winner is minimum among all remaining elements
- All returned elements ≤ all remaining elements

## API

```java
// Constructor
public CollatingIterator(List<? extends Iterator<T>> iterators)

// Iterator interface
public boolean hasNext()
public T next()  // throws NoSuchElementException if exhausted
```

## Limitations

- `remove()` not supported (throws UnsupportedOperationException)
- Not thread-safe
- Iterators must be pre-sorted (not validated)
- Generic type must be Comparable

## Future Optimizations

From Stage 3 analysis, could consider:
- Small-k optimization (k ≤ 8): Linear scan
- Primitive specializations (int, long) to avoid boxing
- Custom comparator support (not just Comparable)
- Concurrent variant for parallel processing

## Testing

See Stage 5 for comprehensive test suite including:
- Correctness: sorted output, all elements present
- Edge cases: empty, single, duplicates
- Properties: laziness, comparison count
