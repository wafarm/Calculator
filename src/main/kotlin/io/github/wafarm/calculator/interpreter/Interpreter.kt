package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.visitor.ExpressionVisitor

class Interpreter {
    companion object {
        fun interpret(source: String): String {
            val lexer = Lexer(source)
            val tokens = lexer.scanTokens()
            val parser = Parser(tokens)
            val ast = parser.parse()
            val visitor = ExpressionVisitor()
            return ast.accept(visitor).toString()
        }
    }
}
