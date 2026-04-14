# 🧪 Narva Online — Test Automation Project

Automated test suite for [narva-online.ee](https://narva-online.ee) —
covering UI, API and End-to-End (Hybrid) scenarios with Allure reporting.

[![CI](https://github.com/SergeiElesin/narvaonline/actions/workflows/build.yaml/badge.svg)](https://github.com/SergeiElesin/narvaonline/actions/workflows/build.yaml)
[![Allure Report](https://img.shields.io/badge/Allure-Report-orange)](https://sergeielesin.github.io/narvaonline/18/index.html)
![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Gradle](https://img.shields.io/badge/Gradle-8-02303A?logo=gradle)
![JUnit5](https://img.shields.io/badge/JUnit-5.10-green?logo=junit5)
![Selenide](https://img.shields.io/badge/Selenide-7.14-yellow)

---

## 📋 Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Running](#installation--running)
- [CI/CD](#cicd)
- [Allure Report](#allure-report)
- [Configuration](#configuration)

---
## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 21 | Programming language |
| Gradle | 8+ | Build tool |
| JUnit 5 | 5.10.2 | Test framework |
| Selenide | 7.14.0 | UI tests (Selenium wrapper) |
| REST Assured | 5.5.6 | API tests |
| Allure | 2.31.0 | Test reporting |
| Lombok | 1.18.42 | Boilerplate code generation |
| AssertJ | 3.27.7 | Fluent assertions |
| Jackson | 2.17.0 | JSON serialization / deserialization |
| Owner | 1.0.12 | Configuration management |

---

## Project Structure

```
narvaonline/
├── src/
│   ├── main/
│   │   └── java/com/elesinsergei/narvaonline/
│   │       ├── api/                        # API clients (REST Assured)
│   │       ├── models/                     # POJO models
│   │       ├── pages/                      # Page Objects (Selenide)
│   │       └── utils/                      # Utility classes
│   └── test/
│       ├── java/com/elesinsergei/narvaonline/
│       │   ├── api/                        # API tests
│       │   ├── config/                     # Test configuration (Owner)
│       │   ├── data/                       # Test data
│       │   ├── e2e/                        # Hybrid (UI + API) tests
│       │   ├── ui/                         # UI tests (Selenide)
│       │   └── utils/                      # Base test class
│       └── resources/
│           ├── img/                        # Test images
│           ├── application.properties      # App configuration
│           ├── auth.properties             # Auth credentials (git-ignored)
│           └── auth.properties.example     # Credentials template
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
└── settings.gradle.kts
```

---

## Prerequisites

- **Java 21** (JDK)
- **Gradle 8+** (or use the included `./gradlew` wrapper)
- A browser for UI tests (Chrome by default)
- Copy `auth.properties.example` → `auth.properties` and fill in credentials

---

## Installation & Running

### Clone the repository

```bash
git clone https://github.com/SergeiElesin/narvaonline.git
cd narvaonline
```

### Set up credentials

```bash
cp src/test/resources/auth.properties.example src/test/resources/auth.properties
# Then fill in your credentials in auth.properties
```

### Run all tests

```bash
./gradlew test
```

### Run UI tests only

```bash
./gradlew test -Dgroups=ui
```

### Run API tests only

```bash
./gradlew test -Dgroups=api
```

### Run E2E (Hybrid) tests only

```bash
./gradlew test -Dgroups=e2e
```

### Run in headless mode (no browser window)

```bash
./gradlew test -Dselenide.headless=true
```

---

## CI/CD

The project uses **GitHub Actions** for continuous integration.
Workflow file: `.github/workflows/build.yaml`

### Pipeline steps

| Step | Description |
|---|---|
| Checkout | Clone the repository |
| Set up JDK 21 | Install Java with Gradle cache |
| Set up Chrome | Install Chrome for Selenide UI tests |
| Run tests | Execute full test suite in headless mode |
| Generate Allure report | Build report with history (last 20 runs) |
| Publish to GitHub Pages | Deploy Allure report to `gh-pages` branch |
| Upload artifacts | Save raw results and HTML report for 7 days |

### Trigger

The workflow is triggered **automatically** when a push or pull request is accepted to the specified branch.
Or **manually** through the GitHub UI (`workflow_dispatch`).
To run: go to **Actions → NarvaOnline Framework CI → Run workflow**.

### Secrets & Variables

Sensitive data and environment settings are stored in GitHub repository settings:

| Name | Type | Description |
|---|---|---|
| `USER_NAME` | Secret | Login credentials |
| `PASSWORD` | Secret | User password |
| `APP_PASSWORD` | Secret | Application password |
| `BASE_URL` | Variable | Frontend base URL |
| `BASE_URL_API` | Variable | API base URL |
| `BROWSER_HEADLESS` | Variable | Headless mode flag |

## Allure Report

### View the latest report online

🔗 [https://sergeielesin.github.io/narvaonline/index.html](https://sergeielesin.github.io/narvaonline/index.html)

### Generate and open locally

```bash
# After running tests
./gradlew allureServe
```

### Generate a static report

```bash
./gradlew allureReport
# Report will be available at build/reports/allure-report/
```

---

## Configuration

The project uses the **Owner** library for configuration management.
Parameters can be passed as system properties (`-D`) at runtime:

| Property | Description | Default |
|---|---|---|
| `selenide.browser` | Browser for UI tests | `chrome` |
| `selenide.headless` | Run without GUI | `false` |
| `selenide.baseUrl` | Base URL of the website | `https://narvaonline.ee` |

> **Note:** Credentials are stored in `src/test/resources/auth.properties` (git-ignored).
> Use `auth.properties.example` as a template.

### Example with custom parameters

```bash
./gradlew test \
  -Dselenide.browser=firefox \
  -Dselenide.headless=true \
  -Dselenide.baseUrl=https://narvaonline.ee
```

---

## 👤 Author

**Sergei Elesin**
GitHub: [@SergeiElesin](https://github.com/SergeiElesin)
