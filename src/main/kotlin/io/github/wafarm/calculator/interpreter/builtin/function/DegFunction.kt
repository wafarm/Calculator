package io.github.wafarm.calculator.interpreter.builtin.function

import io.github.wafarm.calculator.interpreter.Interpreter
import io.github.wafarm.calculator.interpreter.objects.BaseObject
import io.github.wafarm.calculator.interpreter.objects.FunctionObject
import io.github.wafarm.calculator.interpreter.objects.numeric.DecimalNumber
import io.github.wafarm.calculator.interpreter.objects.numeric.IntegerNumber
import kotlin.math.PI

class DegFunction : FunctionObject() {
    override fun call(arguments: List<BaseObject>, context: Interpreter.InterpreterContext): BaseObject {
        if (arguments.size != 1) {
            throw IllegalArgumentException("Expected one argument.")
        }
        return when (val argument = arguments[0]) {
            is IntegerNumber -> {
                DecimalNumber(argument.value.toDouble() / 180 * PI)
            }

            is DecimalNumber -> {
                DecimalNumber(argument.value / 180 * PI)
            }

            else -> {
                throw IllegalArgumentException("Expected a number as the argument")
            }
        }
    }

    override fun stringRepresentation(): String {
        return "[built-in function Deg]"
    }
}
