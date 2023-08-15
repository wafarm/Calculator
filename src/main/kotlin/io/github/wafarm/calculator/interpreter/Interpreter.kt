package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.objects.BaseObject
import io.github.wafarm.calculator.interpreter.objects.numeric.DecimalNumber
import io.github.wafarm.calculator.interpreter.visitor.ExpressionVisitor

class Interpreter {

    private val context = InterpreterContext()
    private val visitor = ExpressionVisitor(context)

    init {
        context.setIdentifier("pi", DecimalNumber.of(Math.PI))
        context.setIdentifier("e", DecimalNumber.of(Math.E))
    }

    class InterpreterContext {
        private val identifiers: MutableMap<String, BaseObject> = mutableMapOf()

        fun getIdentifier(identifier: String): BaseObject? {
            return identifiers.getOrDefault(identifier.lowercase(), null)
        }

        fun setIdentifier(identifier: String, value: BaseObject) {
            identifiers[identifier.lowercase()] = value
        }
    }

    fun interpret(source: String): String {
        val lexer = Lexer(source)
        val tokens = lexer.scanTokens()
        val parser = Parser(tokens)
        val ast = parser.parse()
        return ast.accept(visitor).stringRepresentation()
    }

    companion object {

        private val interpreter = Interpreter()

        fun interpret(source: String): String {
            return interpreter.interpret(source)
        }
    }
}
