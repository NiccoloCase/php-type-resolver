# PhpStorm SE Intern — Task #3 Solution

### Author: Niccolò Caselli

Solution to Task #3 of the JetBrains PhpStorm Software Engineering Internship application.

## Overview

The task required implementing the `TypeResolver.inferTypeFromDoc` method that infers the type of a PHP variable by parsing its `@var` PHPDoc tag.

## Project Structure

```
src/main/java/org/caselli/
├── DocTag.java
├── PhpDocBlock.java
├── PhpVariable.java
├── PhpType.java
├── UnionType.java
├── TypeFactory.java
├── TypeResolver.java    # <- Core implementation
├── MyDocBlock.java
├── MyDocTag.java
├── MyVariable.java
├── MyPhpType.java
└── Main.java            # <- Demo
```
