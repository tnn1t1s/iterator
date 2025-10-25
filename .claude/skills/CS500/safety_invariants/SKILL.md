---
name: safety_invariants
description: Inserts runtime assertions, borrow-safety checks, and undefined behavior guards. Ensures semantic equivalence across Java, C++, and Rust implementations. Use for robust, verifiable code.
allowed-tools: Read, Write, Edit, Bash, Grep, Glob
---

# Safety Invariants

## Purpose

Insert explicit safety checks, assertions, and invariant validations to ensure correctness and prevent undefined behavior.

## Invariant Categories

### 1. Preconditions
- Java: `Objects.requireNonNull()`, `if (!condition) throw`
- C++: `assert()` in debug, explicit checks in release
- Rust: `assert!()`, `debug_assert!()`, or `Option`/`Result`

### 2. Postconditions
- Java: Assertions after operations
- C++: `assert()` or return value validation
- Rust: `debug_assert!()` or type system guarantees

### 3. Loop Invariants
- Document expected invariant in comment
- Assert at loop start/end in debug builds
- Example: "Heap property maintained"

### 4. Memory Safety
- Java: Handled by runtime (null checks, bounds)
- C++: Bounds checking (`at()` vs `[]`), smart pointers
- Rust: Compile-time guarantees (borrow checker)

## Language-Specific Patterns

### Java Safety
```java
public T next() {
    if (!hasNext()) {
        throw new NoSuchElementException("Iterator exhausted");
    }
    assert heap.size() > 0 : "Heap should not be empty";
    T result = heap.poll().value;
    assert result != null : "Result should not be null";
    return result;
}
```

### C++ Safety
```cpp
T next() {
    assert(has_next() && "Iterator must have next element");
    assert(!heap_.empty() && "Heap should not be empty");

    auto entry = heap_.top();
    heap_.pop();

    // Bounds checking on iterator advancement
    assert(entry.iter != entry.end && "Iterator not exhausted");

    return entry.value;
}
```

### Rust Safety
```rust
fn next(&mut self) -> Option<T> {
    // Rust's type system enforces Option contract
    let entry = self.heap.pop()?; // Returns None if empty

    debug_assert!(!self.heap.is_empty() || entry.iter.peek().is_none(),
                   "Heap empty implies all iterators exhausted");

    let value = entry.value;

    // Advance iterator
    if let Some(next_val) = entry.iter.next() {
        self.heap.push(Entry { value: next_val, iter: entry.iter });
    }

    Some(value)
}
```

## Cross-Language Equivalence

Ensure implementations have equivalent behavior:

1. **Same inputs → same outputs** (modulo undefined behavior)
2. **Same error conditions** (throw/panic/return None consistently)
3. **Same complexity guarantees** (same Big-O, similar constants)

## Verification Strategy

- Unit tests verify invariants across languages
- Document invariants explicitly in comments
- Use assertions liberally in debug builds
- Profile to ensure assertions don't hurt release builds

## Cross-Skill Integration

Requires: multi_language_codegen
Works with: unit_test_generation
Feeds into: self_consistency_checker
