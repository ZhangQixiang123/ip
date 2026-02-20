# AI-Assisted Development Report

## Tool Used
- **Claude Code** (Anthropic Claude Opus CLI agent)

## Usage Summary

### 1. Adding `find` Command (Level-9)
- **What**: Used Claude Code to implement the `find` keyword search feature in `Parser.java`.
- **How**: AI generated the `handleFind` method that iterates through the task list and matches descriptions containing the keyword.
- **Changes**: `src/main/java/duke/Parser.java` — added `handleFind` method and `find` case in the switch statement.

### 2. JUnit Tests (A-JUnit)
- **What**: Used Claude Code to generate JUnit 5 test classes for `Parser` and `TaskList`.
- **How**: AI wrote test cases covering normal operations, edge cases, and error conditions for both classes.
- **Changes**: `src/test/java/duke/ParserTest.java`, `src/test/java/duke/TaskListTest.java`, and JUnit dependency in `build.gradle`.

### 3. User Guide (A-UserGuide)
- **What**: Used Claude Code to write the product User Guide.
- **How**: AI generated documentation covering all commands with format descriptions and example outputs.
- **Changes**: `docs/README.md`.

### 4. Project Compliance Check
- **What**: Used Claude Code to audit the project against CS2103 iP grading requirements.
- **How**: AI fetched the course website, identified missing tags, naming issues, and missing features, then fixed them systematically.
- **Changes**: Renamed product title from "Duke" to "Fzjjs" in `Main.java`, added missing git tags to historical commits.
