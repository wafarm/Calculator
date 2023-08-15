package io.github.wafarm.calculator.interpreter.objects.numeric

import kotlin.math.pow

class IntegerNumber(val value: Int) : NumericObject() {
    override fun plus(other: NumericObject): NumericObject {
        if (other is IntegerNumber) {
            return IntegerNumber(value + other.value)
        } else if (other is DecimalNumber) {
            return DecimalNumber(value + other.value)
        }
        throw IllegalStateException()
    }

    override fun minus(other: NumericObject): NumericObject {
        if (other is IntegerNumber) {
            return IntegerNumber(value - other.value)
        } else if (other is DecimalNumber) {
            return DecimalNumber(value - other.value)
        }
        throw IllegalStateException()
    }

    override fun times(other: NumericObject): NumericObject {
        if (other is IntegerNumber) {
            return IntegerNumber(value * other.value)
        } else if (other is DecimalNumber) {
            return DecimalNumber(value * other.value)
        }
        throw IllegalStateException()
    }

    override fun div(other: NumericObject): NumericObject {
        if (other is IntegerNumber) {
            return IntegerNumber(value / other.value)
        } else if (other is DecimalNumber) {
            return DecimalNumber(value / other.value)
        }
        throw IllegalStateException()
    }

    override fun unaryMinus(): NumericObject {
        return IntegerNumber(-value)
    }

    override fun pow(other: NumericObject): NumericObject {
        if (other is IntegerNumber) {
            return DecimalNumber(value.toDouble().pow(other.value))
        } else if (other is DecimalNumber) {
            return DecimalNumber(value.toDouble().pow(other.value))
        }
        throw IllegalStateException()
    }

    override fun stringRepresentation(): String {
        return value.toString()
    }

    companion object {
        fun of(value: Int): IntegerNumber {
            return IntegerNumber(value)
        }
    }
}
