#!/bin/bash
# Convert LaTeX and Markdown files to HTML using pandoc

set -e

# Convert LaTeX files
find projects -name "*.tex" -type f | while read -r tex_file; do
    html_file="${tex_file%.tex}.html"
    echo "Converting: $tex_file → $html_file"

    pandoc "$tex_file" \
        -f latex \
        -t html \
        --mathjax=https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js \
        --standalone \
        --toc \
        --css="https://cdn.jsdelivr.net/npm/water.css@2/out/water.css" \
        -o "$html_file"

    git add "$html_file" 2>/dev/null || true
done

# Convert Markdown files
find projects -name "*.md" -type f | while read -r md_file; do
    html_file="${md_file%.md}.html"
    echo "Converting: $md_file → $html_file"

    pandoc "$md_file" \
        -f markdown \
        -t html \
        --standalone \
        --toc \
        --css="https://cdn.jsdelivr.net/npm/water.css@2/out/water.css" \
        -o "$html_file"

    git add "$html_file" 2>/dev/null || true
done

echo "LaTeX + Markdown → HTML conversion complete!"
