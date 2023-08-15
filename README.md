# Yet Another Calculator

A Minecraft mod that adds in-game calculating functions.

## Installation

Put the mod in the mods folder, and make sure you have Fabric API and Fabric Language Kotlin installed.

## Usage

To calculate something, type `/calc <expression>`.

Supported operators: `( ) + - * / ^(power)`

Supported constants: `e pi` (case-insensitive)

You can use `%n` and `Out[n]` to refer to the n-th calculation result. For example, `%1` and `Out[1]` is the first
result. Specially, `%` alone is the previous result.
