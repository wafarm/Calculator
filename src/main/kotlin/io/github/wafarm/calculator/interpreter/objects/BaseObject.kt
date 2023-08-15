package io.github.wafarm.calculator.interpreter.objects

interface BaseObject {
    val type: ObjectType
    fun stringRepresentation(): String
}
