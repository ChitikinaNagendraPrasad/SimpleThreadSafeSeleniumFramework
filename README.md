# Simple Thread‑Safe Selenium Java Framework (Maven + TestNG + POM + Extent + Log4j2)

This is a **small, interview-friendly** sample framework that demonstrates:

- ✅ **Thread-safe parallel execution** using `ThreadLocal<WebDriver>`
- ✅ **TestNG parallel runs** (`testng.xml`)
- ✅ **Page Object Model (POM)**
- ✅ **Extent Reports** with screenshots on failure
- ✅ **Log4j2** logging
- ✅ **RetryAnalyzer** (retries failing tests once)
- ✅ Data‑driven testing using **Excel DataProvider** and **DB DataProvider** (embedded H2)
- ✅ Optional **Selenium Grid** support (RemoteWebDriver)

Target demo site: **https://the-internet.herokuapp.com/login**

> ⚠️ Note: This is a demo framework for learning/interview use. Adjust URLs/locators for your real AUT.

---

## 1) Prerequisites

- Java 11+
- Maven 3.8+
- Chrome/Firefox/Edge installed (for local mode)

---

## 2) Run tests locally

```bash
mvn clean test
```

Default settings are in `src/test/resources/config.properties`.

---

## 3) Run in parallel

Parallel execution is enabled in `testng.xml`.

- Start with `parallel="classes"`, then try `parallel="methods"`.

---

## 4) Run on Selenium Grid (optional)

Update `config.properties`:

```properties
run.mode=grid
grid.url=http://localhost:4444/wd/hub
browser=chrome
```

Then:

```bash
mvn clean test
```

---

## 5) Reports

- Extent HTML report is generated under:

```text
target/extent-report/ExtentReport.html
```

- Screenshots (on failures) under:

```text
target/screenshots/
```

- Logs under:

```text
target/logs/framework.log
```

---

## 6) Project highlights (what makes it thread‑safe)

- WebDriver is stored in `ThreadLocal` (`DriverManager`)
- Page objects use the driver from `DriverManager.getDriver()`
- Extent test nodes are thread‑local (`ExtentManager`)
- Screenshots use unique names with thread id + timestamp

---

## 7) Jenkins

A simple `Jenkinsfile` is included. You can run `mvn clean test` and archive `target/extent-report` and `target/logs`.

---

## 8) Useful commands

```bash
mvn -Dtestng.dtd.http=true clean test
```

---

If you want, share your current framework structure and I can adapt this template exactly to your style.
