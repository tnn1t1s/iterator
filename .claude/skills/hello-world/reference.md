# Hello World Skill - Reference

## Progressive Loading

This file demonstrates progressive disclosure:
- SKILL.md loaded first (minimal instructions)
- reference.md loaded only if needed (detailed info)
- Reduces token usage when detail unnecessary

## Skill Activation

Claude automatically detects when to use this skill based on:
- Description match: "testing skills" | "demonstrate skill"
- Context: First-time skill usage
- User intent: Learning about skills

## File Structure

```
.claude/skills/hello-world/
├── SKILL.md (required)
└── reference.md (optional)
```

## Tool Restrictions

allowed-tools: Read, Write

This skill can:
- ✓ Read files
- ✓ Write files
- ✗ Execute bash commands
- ✗ Search web
- ✗ Edit files

## Extension Ideas

Add to hello-world:
- scripts/hello.py → Executable demo
- templates/greeting.txt → Template demo
- examples.md → More usage patterns
