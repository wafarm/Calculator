package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.visitor.ExpressionVisitor

class Interpreter {

    private val context = InterpreterContext()
    private val visitor = ExpressionVisitor(context)

    init {
        context.setIdentifier("pi", Math.PI)
        context.setIdentifier("e", Math.E)
    }

    class InterpreterContext {
        private val identifiers: MutableMap<String, Double> = mutableMapOf()

        fun getIdentifier(identifier: String): Double? {
            return identifiers.getOrDefault(identifier.lowercase(), null)
        }

        fun setIdentifier(identifier: String, value: Double) {
            identifiers[identifier.lowercase()] = value
        }
    }

    fun interpret(source: String): String {
        val lexer = Lexer(source)
        val tokens = lexer.scanTokens()
        val parser = Parser(tokens)
        val ast = parser.parse()
        return ast.accept(visitor).toString()
    }

    companion object {

        private val interpreter = Interpreter()

        fun interpret(source: String): String {
            return interpreter.interpret(source)
        }
    }
}
