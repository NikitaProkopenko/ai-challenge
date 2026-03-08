# AGENTS.md

## Cursor Cloud specific instructions

This is a minimal Kotlin CLI application that calls the Anthropic Claude API. It has no tests, no database, no Docker, and no web server.

### Services

| Service | How to run | Notes |
|---|---|---|
| Claude API Client | `gradle run` | Requires `ANTHROPIC_API_KEY` env var |

### Prerequisites

- **Java 21** — pre-installed on the VM (`/usr/lib/jvm/java-21-openjdk-amd64`).
- **Gradle 8.12** — installed at `/opt/gradle-8.12`, symlinked to `/usr/local/bin/gradle`.

### Build & Run

- `gradle build` — compiles the project and downloads dependencies.
- `gradle run` — runs the application (sends a prompt to Claude and prints the response).
- The app reads `ANTHROPIC_API_KEY` from the environment. Without it, the app exits with a user-friendly error.
- There are no lint tools or test suites configured in this project.

### Caveats

- No Gradle wrapper (`gradlew`) is checked in; system Gradle is required.
- First `gradle build` after a clean VM may take ~30s to download dependencies and start the Gradle daemon.
