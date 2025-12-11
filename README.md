# Declarative Scheduling Language (DSL)

This project implements a **declarative scheduling language** using **ANTLR4**, a custom AST, a visitor-based interpreter, and optional PNG visualization.

The repository provides three executable entry points:

- **Tokenize** — prints lexer tokens  
- **PrintAst** — parses the DSL and prints the AST  
- **GeneratePng** — interprets the AST and generates a PNG schedule  

All commands use Maven’s `exec-maven-plugin`.

---

## 📦 Prerequisites

- Java 17+
- Maven 3.9+
- ANTLR4 (managed through Maven plugin)

---

## 📁 Directory Structure

```
src/main/
  antlr4/            → DSL grammar files (DSLLexer.g4, DSLParser.g4)
  java/
    model/           → AST model classes (Schedule, Sequence, TimeRange…)
    processing/      → Builder + Interpreter logic
    Tokenize.java
    PrintAst.java
    GeneratePng.java
```

ANTLR generates parser/lexer code into:

```
src/main/java/generated/
```

---

## ▶️ Running the Tools

Each tool expects a **DSL input file**, e.g.:

📄 `data/test-data.txt`

---

## 1. Tokenize

Prints all lexer tokens from the input file.

```bash
mvn exec:java -Dexec.mainClass=Tokenize \
              -Dexec.args="data/test-data.txt"
```

---

## 2. PrintAst

Parses the DSL and prints the constructed abstract syntax tree.

```bash
mvn exec:java -Dexec.mainClass=PrintAst \
              -Dexec.args="data/test-data.txt"
```

---

## 3. GeneratePng

Interprets the AST and generates a PNG timeline visualization.

```bash
mvn exec:java -Dexec.mainClass=GeneratePng \
              -Dexec.args="data/test-data.txt"
```

The PNG is written to the working directory using the schedule layout.

---


## 4. TestRig GUI

Launches the ANTLR TestRig GUI to visualize and test the DSL grammar.

1. **Build the project and create the fat JAR**:

```bash
mvn package
```

2. **Run the TestRig GUI with your input file**:

```bash
bash grun.sh data/test-data.txt
```

## 🔧 Rebuilding ANTLR Sources

ANTLR grammars are regenerated with:

```bash
mvn generate-sources
```

Or simply:

```bash
mvn clean compile
```

---

## 📖 Recommended Workflow

1. Run **Tokenize** to verify lexical correctness  
2. Run **TestRig GUI** to interactively test and visualize the DSL grammar using ANTLR's GUI  
3. Run **PrintAst** to validate grammar parsing  
4. Run **GeneratePng** to visualize the final interpreted schedule  

---
