# Benchmark Results

## Status

Benchmark frameworks implemented. Results pending execution after build.

## Directory Structure

```
results/
  raw/          # Raw JSON/CSV output from JMH, Google Benchmark, Criterion
  processed/    # Cleaned, normalized data for analysis
  plots/        # Visualizations (PNG, SVG)
  README.md     # This file
```

## Execution Instructions

### Java (JMH)
```bash
cd ../../04-implementation/java
./gradlew jmh
cp build/reports/jmh/results.json ../../06-benchmarks/results/raw/java-jmh.json
```

### C++ (Google Benchmark)
```bash
cd ../../04-implementation/cpp/build
./collating_iterator_benchmark --benchmark_format=json > ../../../06-benchmarks/results/raw/cpp-benchmark.json
```

### Rust (Criterion)
```bash
cd ../../04-implementation/rust
cargo bench
cp target/criterion/*/base/estimates.json ../../06-benchmarks/results/raw/rust-criterion.json
```

## Analysis Scripts

Place analysis scripts (Python, R, Julia) in `scripts/` directory to:
1. Parse raw benchmark outputs
2. Normalize data formats
3. Perform regression analysis
4. Generate plots

## Expected Artifacts

- `raw/java-jmh.json` - JMH benchmark results
- `raw/cpp-benchmark.json` - Google Benchmark results
- `raw/rust-criterion.json` - Criterion benchmark results
- `processed/combined.csv` - Normalized results across all languages
- `plots/complexity-validation.png` - O(N log k) confirmation plot
- `plots/language-comparison.png` - Java vs C++ vs Rust throughput
- `plots/crossover-analysis.png` - Heap vs Linear scan crossover
