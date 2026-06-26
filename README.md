# CodeAlpha_JavaGradleApp

> **CodeAlpha DevOps Internship — Task 3: Java Application using Gradle**

[![CI](https://github.com/Alifk22/CodeAlpha_JavaGradleApp/actions/workflows/ci.yml/badge.svg)](https://github.com/Alifk22/CodeAlpha_JavaGradleApp/actions/workflows/ci.yml)

A small but real **Java command-line application** built and managed entirely with
**Gradle** — demonstrating automated builds, dependency management, unit testing, a
CI/CD pipeline, and a packaged, deployable artifact.

The app itself is a **Task Tracker**: add tasks, list them, mark them done, and clear
completed ones. Tasks persist to JSON on disk between runs.

---

## ✨ What this demonstrates
- **Build automation with Gradle** — one command compiles, tests, and packages the app
- **Dependency management** — external libraries resolved from Maven Central
  (`Gson` for JSON, `commons-lang3` for string utilities)
- **Unit testing** — JUnit 5 tests for the service and persistence layers
- **CI/CD pipeline** — GitHub Actions builds and tests on every push/PR and publishes the artifact
- **Deployable output** — the `application` plugin produces a runnable distribution
  (start scripts + libraries) under `build/distributions/`
- **Reproducible builds** — the Gradle Wrapper and a Java toolchain pin the build environment

## 🧱 Project structure

```
CodeAlpha_JavaGradleApp/
├── build.gradle            # build logic, dependencies, plugins
├── settings.gradle
├── gradlew / gradlew.bat   # Gradle Wrapper (no system Gradle needed)
├── gradle/wrapper/         # pinned Gradle version
├── .github/workflows/ci.yml
└── src/
    ├── main/java/com/codealpha/tasktracker/
    │   ├── Main.java           # CLI entry point
    │   ├── Task.java           # data model
    │   ├── TaskService.java    # core logic (pure, unit-tested)
    │   └── TaskRepository.java  # JSON persistence (Gson)
    └── test/java/com/codealpha/tasktracker/
        ├── TaskServiceTest.java
        └── TaskRepositoryTest.java
```

## 🚀 Build, test, run

**Prerequisite:** JDK 21+ (the Wrapper downloads Gradle itself).

```bash
# Compile, run tests, and package
./gradlew build

# Run the app
./gradlew run --args="add Finish the DevOps task"
./gradlew run --args="list"
./gradlew run --args="done 1"

# Build a distributable package (start scripts + libs)
./gradlew installDist
./build/install/CodeAlpha_JavaGradleApp/bin/CodeAlpha_JavaGradleApp list
```

On Windows use `gradlew.bat` instead of `./gradlew`.

## 🧪 Tests
JUnit 5 covers the core logic and persistence:
```bash
./gradlew test
```

## 🔧 Tech stack
`Java 21` · `Gradle` · `JUnit 5` · `Gson` · `Apache Commons Lang3` · `GitHub Actions`

## 👤 Author
**Ali** — CodeAlpha DevOps Internship, 2026
