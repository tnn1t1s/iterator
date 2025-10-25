# Iterator Project Skills

Skills for specialized agent capabilities in the iterator project.

## Available Skills

### hello-world
**Status**: Demo
**Purpose**: Demonstrates basic skill structure and activation
**Tools**: Read, Write

Simple example showing:
- YAML frontmatter format
- Tool restrictions
- Progressive disclosure
- Reference files

## Creating New Skills

```bash
mkdir -p .claude/skills/skill-name
```

Create `SKILL.md`:
```yaml
---
name: skill-name
description: What it does and when to activate
allowed-tools: Tool1, Tool2
---

# Skill Name

## Instructions
Steps for Claude to follow

## Examples
Usage patterns
```

## Skill Locations

- `.claude/skills/` → Project skills (this directory)
- `~/.claude/skills/` → Personal skills (all projects)

## Testing

Trigger naturally: "Can you test the hello-world skill?"

Claude scans descriptions and activates automatically.

## References

- See SKILLS.md in project root for comprehensive guide
- anthropics/skills repo for official examples
- docs.claude.com/claude-code/skills for documentation
