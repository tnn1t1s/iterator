# SKILLS.md - Agent Skills Digest

## Legend
| Symbol | Meaning | | Abbrev | Meaning |
|--------|---------|---|--------|---------|
| → | leads to | | impl | implementation |
| > | greater than | | exec | execution |
| & | and/with | | docs | documentation |
| : | define | | repo | repository |

## Core Architecture

```yaml
Definition: Modular folders w/ instructions|scripts|resources Claude loads dynamically
Purpose: Package specialized expertise → Make Claude domain specialist
Announced: 2025-10-23 | Status: Pro|Max|Team|Enterprise users

Key_Principles:
  Progressive_Disclosure: Metadata→Full content→Supporting files (as needed)
  Model_Invoked: Claude decides when→use based on description+context
  Composable: Multiple skills stack|coordinate automatically
  Portable: Same format: Claude.ai|Claude Code|API
  Efficient: Load minimal required info only

Invocation_Logic:
  Trigger: Description matches user request+context
  Load: SKILL.md → reference files → scripts (progressive)
  Execute: Within tool restrictions if specified
  Complete: Clean context after task
```

## File Structure

```yaml
Required:
  skill-name/
    SKILL.md: Core instructions w/ YAML frontmatter

Optional:
  reference.md: Supporting documentation
  examples.md: Usage examples
  scripts/: Executable helpers (Python|Bash|JS)
  templates/: File templates
  forms/: Form definitions
```

## SKILL.md Format

```yaml
Frontmatter_Required:
  name: lowercase-hyphenated (max 64 chars)
  description: Brief summary+when to use (max 1024 chars)

Frontmatter_Optional:
  allowed-tools: Read,Grep,Glob (tool restriction)
  version: Semantic versioning
  author: Creator info

Structure:
  ---
  name: skill-name
  description: What it does & when Claude should use it
  allowed-tools: Tool1,Tool2,Tool3
  ---

  # Skill Name

  ## Instructions
  Step-by-step guidance for Claude

  ## Examples
  Concrete usage patterns

  ## References
  Point to reference.md or other files
```

## Storage Locations

```yaml
Personal_Skills: ~/.claude/skills/skill-name/
  Scope: All projects for user
  Use: Personal workflows|preferences|utilities

Project_Skills: .claude/skills/skill-name/
  Scope: Single project|shared via git
  Use: Team collaboration|project-specific tasks

Plugin_Skills: Bundled w/ installed plugins
  Scope: Plugin-specific capabilities
  Use: Marketplace skills|vendor extensions

Priority: Project > Personal > Plugin (conflicts)
```

## Installation & Management

```yaml
Claude_Code:
  Add_Marketplace: /plugin marketplace add anthropics/skills
  Install_Skill: /plugin install skill-name@anthropic-agent-skills
  List_Available: /plugin list
  Remove_Skill: /plugin uninstall skill-name

Manual_Install:
  1: Create skill-name/ in ~/.claude/skills/ or .claude/skills/
  2: Write SKILL.md w/ required frontmatter
  3: Add supporting files as needed
  4: Restart Claude Code or reload project

Team_Sharing:
  1: Create skill in .claude/skills/
  2: git add .claude/skills/skill-name/
  3: git commit & push
  4: Team pulls → Auto-available

Verification: Claude scans on startup|project load
```

## Tool Restrictions

```yaml
Purpose: Limit capabilities when skill active → Enable read-only|restricted modes
Format: allowed-tools: Tool1,Tool2,Tool3

Common_Patterns:
  Read_Only: allowed-tools: Read,Grep,Glob
  Analysis: allowed-tools: Read,Grep,Glob,WebFetch
  Build: allowed-tools: Read,Edit,Write,Bash
  Full_Access: Omit field → All tools available

Benefits:
  - No permission prompts for allowed tools
  - Security: Limit scope of skill operations
  - Clarity: Explicit about skill capabilities
```

## Context Management

```yaml
Progressive_Loading:
  Stage_1_Metadata: name+description pre-loaded (system prompt)
  Stage_2_Core: SKILL.md loaded when Claude determines relevance
  Stage_3_Support: reference.md|examples.md loaded as needed
  Stage_4_Exec: Scripts executed w/o loading to context

Token_Economics:
  Metadata: ~50-100 tokens per skill (always loaded)
  Full_Skill: 500-5K tokens (on activation)
  Support_Files: Variable (progressive)
  Code_Exec: 0 tokens (runs external)

Context_Window_Modification:
  Initial: System prompt + skill metadata + user message
  Activated: + SKILL.md content
  Working: + selected reference files
  Cleanup: Remove after task complete

Scalability: Unbounded skills possible w/ filesystem+code exec
```

## Code Execution

```yaml
Capability: Skills can bundle executable scripts
Purpose: Deterministic ops w/o token cost → PDF parsing|data transform|file ops

Languages: Python|Bash|Node.js|any executable
Invocation: Claude uses Bash tool → Execute script → Parse output
Benefits: Reliable operations|reduced tokens|complex logic

Pattern:
  SKILL.md: Instructions reference scripts/helper.py
  Claude_reads: SKILL.md → Sees script available
  Claude_executes: bash scripts/helper.py args
  Claude_uses: Output in response to user

Examples:
  PDF_forms: Extract field data via script
  Excel_formulas: Generate complex calculations
  Data_transform: Process CSV|JSON w/o loading full data
```

## Official Skills Repository

```yaml
Location: github.com/anthropics/skills
Status: Reference examples (not production-maintained)

Categories:
  Creative: algorithmic-art|canvas-design|slack-gif-creator
  Development: artifacts-builder|mcp-builder|webapp-testing
  Enterprise: brand-guidelines|internal-comms|theme-factory
  Documents: DOCX|PDF|PPTX|XLSX (point-in-time snapshots)
  Meta: skill-creator|template-skill

Install_From_Marketplace:
  /plugin marketplace add anthropics/skills
  /plugin install document-skills@anthropic-agent-skills
  /plugin install example-skills@anthropic-agent-skills

Purpose: Learning|inspiration|starting templates
```

## Best Practices

```yaml
Design:
  Single_Purpose: One skill → One capability
  Clear_Description: Include trigger terms users would say
  Progressive_Detail: Essential first → Deep reference later
  Version_Control: Track changes|compatibility in SKILL.md

Documentation:
  Instructions: Step-by-step for Claude
  Examples: Concrete usage patterns
  References: Organize deep info in separate files
  Trigger_Terms: Use words users would naturally say

Testing:
  Team_Members: Test discoverability w/ real users
  Edge_Cases: Document limitations|failure modes
  Tool_Restrictions: Verify allowed-tools list sufficient
  Context_Size: Keep core SKILL.md under 5K tokens

Organization:
  Personal_Skills: ~/.claude/skills/ → User workflows
  Project_Skills: .claude/skills/ → Team shared
  Naming: lowercase-hyphenated-descriptive
  Structure: Flat directory per skill (no nesting)

Security:
  Trust: Only install skills from trusted sources
  Code_Review: Audit scripts before installing
  Tool_Limits: Use allowed-tools for restrictions
  Sandbox: Skills run w/ Claude Code permissions

Maintenance:
  Version: Track in SKILL.md frontmatter
  Changelog: Document updates|breaking changes
  Dependencies: Note required tools|plugins|env
  Cleanup: Remove unused skills → Reduce metadata load
```

## Integration Patterns

```yaml
With_MCP:
  Skills_handle: Task execution|workflows|instructions
  MCP_handles: External tool integration|APIs|data sources
  Pattern: Skill coordinates → MCP tools provide data
  Example: "SKILL.md guides report generation → MCP fetches data"

With_Slash_Commands:
  Skills: Model-invoked (Claude decides)
  Commands: User-invoked (explicit trigger)
  Complementary: Commands→quick access | Skills→automatic
  Example: /commit (command) vs commit-helper skill (auto)

With_Personas:
  Personas: Behavioral profiles|decision patterns
  Skills: Tactical capabilities|domain knowledge
  Pattern: Persona active → Skills provide tools
  Example: architect persona → Uses DDD skills

With_Hooks:
  Hooks: Event-triggered shell commands
  Skills: Rich instructions|multi-step workflows
  Pattern: Hook triggers → Skill provides context
  Example: pre-commit hook → Code-quality skill active

With_TodoWrite:
  Skills_can: Reference todo workflow in SKILL.md
  Pattern: Skill activates → Break into todos → Execute
  Example: "complex-refactor skill → Generates todo checklist"

Priority_Conflicts:
  1_User_Instructions: Explicit request overrides skill
  2_Active_Skill: Loaded skill takes precedence
  3_Persona: Active persona influences skill selection
  4_Global_Config: CLAUDE.md|RULES.md baseline
```

## Skill Development Workflow

```yaml
Create:
  1_Use_skill-creator: From anthropics/skills repo
  2_Define_metadata: name|description|allowed-tools
  3_Write_instructions: Clear steps for Claude
  4_Add_examples: Concrete usage patterns
  5_Bundle_resources: Scripts|templates|references

Test:
  1_Local_testing: Install in ~/.claude/skills/
  2_Trigger_naturally: Use description keywords in requests
  3_Verify_behavior: Check Claude loads & follows
  4_Edge_cases: Test limitations|error handling
  5_Team_validation: Share w/ colleagues for feedback

Iterate:
  1_Monitor_usage: Note when skill activates correctly
  2_Refine_description: Improve trigger matching
  3_Expand_examples: Add patterns from real usage
  4_Optimize_context: Keep core content minimal
  5_Version_bump: Document changes in frontmatter

Share:
  Personal: Keep in ~/.claude/skills/ (private)
  Team: Move to .claude/skills/ + git commit
  Public: Fork anthropics/skills → PR w/ documentation
  Marketplace: Package as plugin (advanced)

Maintain:
  Update: As Claude capabilities evolve
  Deprecate: Mark obsolete|replaced skills
  Archive: Move unused to skills-archive/
  Document: Keep changelog in SKILL.md
```

## Troubleshooting

```yaml
Skill_Not_Activating:
  - Check description matches user intent terms
  - Verify SKILL.md frontmatter valid YAML
  - Confirm location: ~/.claude/skills/ or .claude/skills/
  - Test: Explicitly mention skill name in request
  - Debug: Check Claude Code logs for load errors

Tool_Restrictions_Blocking:
  - Verify allowed-tools includes needed capabilities
  - Add required tools to list if safe
  - Consider security implications before expanding
  - Test w/o restrictions first → Add gradually

Context_Size_Issues:
  - Keep SKILL.md under 5K tokens
  - Move detail to reference.md (progressive load)
  - Use scripts for data/code (exec not load)
  - Split large skills into focused sub-skills

Conflicts:
  - Name_collision: Rename skill (must be unique)
  - Tool_conflicts: Adjust allowed-tools per skill
  - Priority_issues: Project skills override personal
  - Multiple_matches: Make descriptions more specific

Performance:
  - Too_many_skills: Metadata load overhead → Archive unused
  - Slow_activation: Large SKILL.md → Move content to refs
  - Script_timeout: Optimize executable performance
  - Context_thrash: Reduce skill size|frequency
```

## Advanced Patterns

```yaml
Multi_Stage_Skills:
  Pattern: Stage 1 instructions → Load stage 2 on demand
  Structure: SKILL.md minimal → reference/*.md detailed
  Benefit: Fast initial load → Deep capability available
  Example: "Quick API guide → reference/endpoints.md"

Skill_Families:
  Pattern: Related skills w/ consistent naming
  Structure: api-reader|api-writer|api-validator
  Benefit: Composable|discoverable|maintainable
  Example: "test-unit|test-integration|test-e2e"

Executable_Skills:
  Pattern: Minimal SKILL.md → Heavy script logic
  Structure: Instructions delegate to scripts/
  Benefit: Complex ops w/o context cost
  Example: "PDF processing → scripts/extract_forms.py"

Template_Skills:
  Pattern: SKILL.md + templates/ directory
  Structure: Instructions reference templates for generation
  Benefit: Consistent output|rapid iteration
  Example: "API docs → templates/endpoint.md"

Chained_Skills:
  Pattern: One skill output → Another skill input
  Structure: Independent but complementary
  Benefit: Modular workflows|reusable components
  Example: "data-fetch skill → data-analyze skill"

Conditional_Skills:
  Pattern: Different paths based on context
  Structure: SKILL.md includes decision tree
  Benefit: Flexible behavior|context-aware
  Example: "test-runner: unit if *.test.ts | e2e if *.spec.ts"
```

## Migration from Other Patterns

```yaml
From_Slash_Commands:
  When: Task Claude should auto-detect (not explicit invoke)
  How: Convert .claude/commands/X.md → .claude/skills/X/SKILL.md
  Add: YAML frontmatter w/ description
  Keep: Commands for user-triggered workflows

From_MCP_Servers:
  When: Task needs instructions > tool access
  How: Write SKILL.md coordinating MCP tool usage
  Pattern: Skill guides workflow → MCP provides data
  Example: "report-generator skill → C7 MCP for docs"

From_CLAUDE.md_Sections:
  When: Domain-specific knowledge repeated often
  How: Extract to .claude/skills/domain/SKILL.md
  Benefit: Conditional load vs always-in-context
  Example: "Python venv rules → python-env-manager skill"

From_Personas:
  When: Need tactical capability > behavioral profile
  How: Create skill persona can use
  Pattern: Persona provides mindset → Skill provides method
  Example: "architect persona → ddd-design skill"

From_Documentation:
  When: Reference material Claude should load as-needed
  How: Create skill w/ docs in reference.md
  Pattern: SKILL.md minimal → reference/ extensive
  Example: "api-style-guide skill → reference/standards.md"
```

## Future Considerations

```yaml
Skill_Marketplace:
  Status: Early|evolving
  Direction: Easier discovery|install|share
  Opportunity: Package specialized expertise
  Watch: Anthropic announcements|community repos

API_Integration:
  Status: Available via Skills API
  Capability: Skills in API requests|v1/skills endpoint
  Requirement: Code Execution Tool beta
  Use_Case: Programmatic skill management

Cross_Platform:
  Current: Claude.ai|Claude Code|API support
  Future: Mobile|other surfaces
  Benefit: Write once → Run everywhere
  Consideration: Platform capabilities vary

Versioning:
  Current: Manual in frontmatter
  Future: Dependency management|compatibility
  Need: Breaking change handling|migration paths
  Best_Practice: Semantic versioning now

Community:
  Growing: User-created skills emerging
  Sharing: GitHub|forums|social
  Quality: Varies|review before use
  Opportunity: Contribute|learn|collaborate
```

---
*Iterator Project | Skills foundation for specialized agent capabilities | 2025-01-15*
