package io.github.wafarm.calculator.interpreter.objects

import io.github.wafarm.calculator.interpreter.Interpreter.InterpreterContext

abstract class FunctionObject : BaseObject {
    override val type: ObjectType = ObjectType.FUNCTION
    abstract fun call(arguments: List<BaseObject>, context: InterpreterContext): BaseObject
}
