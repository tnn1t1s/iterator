#!/bin/bash
# Convert LaTeX files to HTML using pandoc

set -e

# Find all .tex files in the repository
find projects -name "*.tex" -type f | while read -r tex_file; do
    # Generate HTML filename (same location, .html extension)
    html_file="${tex_file%.tex}.html"

    echo "Converting: $tex_file → $html_file"

    # Convert using pandoc with math support
    pandoc "$tex_file" \
        -f latex \
        -t html \
        --mathjax \
        --standalone \
        --toc \
        --css="https://cdn.jsdelivr.net/npm/water.css@2/out/water.css" \
        -o "$html_file"

    # Stage the HTML file for commit
    git add "$html_file" 2>/dev/null || true
done

echo "LaTeX → HTML conversion complete!"
