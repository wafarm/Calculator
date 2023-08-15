package io.github.wafarm.calculator.interpreter.objects

abstract class NumericObject : BaseObject {
    override val type: ObjectType = ObjectType.NUMERIC
    abstract operator fun plus(other: NumericObject): NumericObject
    abstract operator fun minus(other: NumericObject): NumericObject
    abstract operator fun times(other: NumericObject): NumericObject
    abstract operator fun div(other: NumericObject): NumericObject
    abstract operator fun unaryMinus(): NumericObject
    abstract fun pow(other: NumericObject): NumericObject
}
