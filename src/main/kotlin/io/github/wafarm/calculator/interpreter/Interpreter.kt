package io.github.wafarm.calculator.interpreter

class Interpreter {
    companion object {
        fun interpret(source: String): String {
            val lexer = Lexer(source)
            val tokens = lexer.scanTokens()
            return tokens.joinToString("\n")
        }
    }
}
