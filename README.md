# yast
Several AST tools

_YAST stands for Yast AST. Or Yet Another Syntax Tree._

## Common
Currently, only constants and some utils are here. Maybe, it will contain more (typers, translators, optimizers, etc.).

## Kotlin JavaCC
Kotlin parser written in JavaCC. Produces Yast abstract syntax tree.

_Note, build with maven to generate sources before executing tests._

## Kotlin IntelliJ
AST converter from IntelliJ's AST to Yast. Not even close to the working state because is ceased in favor of JavaCC. It is here for performance testing and to show how one can use IntelliJ Kotlin parser.

_Note, build with maven to generate sources before executing tests._

## Kotlin ANTLR
Kotlin parser built on ANTLR. It is a full copy of grammars at https://github.com/shadrina/kotlin-grammar-antlr4 It is not used to produce Yast, it is for performance testing only.