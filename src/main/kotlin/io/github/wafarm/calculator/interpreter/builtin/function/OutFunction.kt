package io.github.wafarm.calculator.interpreter.builtin.function

import io.github.wafarm.calculator.interpreter.Interpreter.InterpreterContext
import io.github.wafarm.calculator.interpreter.objects.BaseObject
import io.github.wafarm.calculator.interpreter.objects.FunctionObject
import io.github.wafarm.calculator.interpreter.objects.numeric.IntegerNumber

class OutFunction : FunctionObject() {
    override fun call(arguments: List<BaseObject>, context: InterpreterContext): BaseObject {
        if (arguments.size != 1) {
            throw IllegalArgumentException("Expected one argument.")
        }
        val argument = arguments[0]
        if (argument !is IntegerNumber) {
            throw IllegalArgumentException("Expected a integer as the argument")
        }
        val index = argument.value
        val result = if (index == -1) {
            context.getLatestHistory()
        } else {
            context.getHistory(index)
        }
        return result ?: throw IllegalArgumentException("Index out of range")
    }

    override fun stringRepresentation(): String {
        return "[built-in function Out]"
    }
}
