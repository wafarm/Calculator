package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.builtin.function.OutFunction
import io.github.wafarm.calculator.interpreter.objects.BaseObject
import io.github.wafarm.calculator.interpreter.objects.numeric.DecimalNumber
import io.github.wafarm.calculator.interpreter.visitor.ExpressionVisitor

class Interpreter {

    private val context = InterpreterContext()
    private val visitor = ExpressionVisitor(context)

    init {
        context.setIdentifier("pi", DecimalNumber.of(Math.PI))
        context.setIdentifier("e", DecimalNumber.of(Math.E))
        context.setIdentifier("Out", OutFunction())
    }

    class InterpreterContext {
        private val identifiers: MutableMap<String, BaseObject> = mutableMapOf()
        private val historyResults: MutableList<BaseObject> = mutableListOf()

        fun getIdentifier(identifier: String): BaseObject? {
            return identifiers.getOrDefault(identifier.lowercase(), null)
        }

        fun setIdentifier(identifier: String, value: BaseObject) {
            identifiers[identifier.lowercase()] = value
        }

        fun addHistory(result: BaseObject) {
            historyResults.add(result)
        }

        fun getHistory(index: Int): BaseObject? {
            return historyResults.getOrNull(index - 1)
        }

        fun getLatestHistory(): BaseObject {
            return getHistory(historyResults.size)!!
        }

        fun getPrettyPrintLatestHistory(): String {
            return "Out[${historyResults.size}]: ${getLatestHistory().stringRepresentation()}"
        }
    }

    fun interpret(source: String): String {
        val lexer = Lexer(source)
        val tokens = lexer.scanTokens()
        val parser = Parser(tokens)
        val ast = parser.parse()
        val result = ast.accept(visitor)
        context.addHistory(result)
        return context.getPrettyPrintLatestHistory()
    }

    companion object {

        private val interpreter = Interpreter()

        fun interpret(source: String): String {
            return interpreter.interpret(source)
        }
    }
}
