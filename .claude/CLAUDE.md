# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

```bash
mvn test                                    # Run all tests
mvn test -Dtest=GoldMineTest                # Run a single test class
mvn test -Dtest=GoldMineExplorerTest        # Run explorer tests (includes parameterized levels)
mvn test -Dtest=AStarExplorerTest           # Run A* pathfinding tests
mvn test -Dtest="AStarExplorerTest,GoldMineExplorerTest"  # Run multiple test classes
```

## Architecture

A mine exploration game where an explorer navigates a hidden rectangular grid to find the exit.

**Three layers:**

- **Game** (`game/`): `GoldMine` is the engine — owns the secret `char[][]` map, handles movement and looking. `Level`
  is a record holding the level string. The mine dimensions must not be exposed.
- **Model** (`model/`): `Position` (x,y) and `View` (Wall/Exit/Home/Empty) are immutable records.
- **Explorer** (`explorer/`): `GoldMineExplorer` is a facade wrapping `GoldMine` — maintains a dynamically growing
  `View[][]` map of discovered cells, handles boundary detection, and provides look/move operations. `Explorer` is a
  strategy interface implemented by `NaiveExplorer` and `AStarExplorer`.

**Key constraint:** The explorer cannot see the full map. It discovers cells incrementally by looking in cardinal
directions and must navigate to unexplored areas. The `View[][]` map in `GoldMineExplorer` grows as new cells are found.
Null entries represent unexplored cells.

**A\* explorer** (`a/star/AStarExplorer`): Uses A* pathfinding with Manhattan distance heuristic. Two-phase target
selection: first finds walkable neighbors adjacent to unexplored (null) cells, then falls back to unvisited edge cells.

## TDD Workflow

This project follows strict Test-Driven Development. Every code change must go through the red-green-refactor cycle:

1. **Red** — Write a failing test first. Run it and confirm it fails.
2. **Green** — Write the minimum production code to make the test pass. Run it and confirm it passes.
3. **Refactor** — Clean up the code while keeping all tests green. Run tests after each change.

Never write production code without a failing test that demands it. Never skip the step of verifying the test fails
before writing the implementation. The TDD cycle for new code takes priority over keeping all other tests green. It is
acceptable for existing tests to temporarily not compile while you are in the red phase.

**Review gate:** NEVER write or modify test code without explicit user approval. Before writing or changing any test,
present the test to the user with a short motivation explaining *why* the test is needed. Then STOP and wait for the
user to approve before writing any code. This applies to every TDD cycle and to every test file change, including
updates to existing tests.

## Code Style

- Each method should operate at a single level of abstraction. If a method mixes high-level orchestration with low-level
  details, extract the details into private methods.
- Keep methods short. Methods longer than 15 lines should be considered for refactoring into smaller methods with higher
  abstraction levels.
- Always use braces around `if` statement bodies, even for one-liners.
- Prefer `if` with a return over `switch` statements.
- Never use the ternary operator (`condition ? a : b`). Use an `if`/`else` block instead.

## Conventions

- Java records for immutable data types (Position, View, Level)
- Test method names: `should_<action>_<scenario>`
- AssertJ assertions (`assertThat`)
- Parameterized tests validate both NaiveExplorer and AStarExplorer against all levels
