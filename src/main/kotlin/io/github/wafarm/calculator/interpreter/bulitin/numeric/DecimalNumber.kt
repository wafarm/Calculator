package io.github.wafarm.calculator.interpreter.bulitin.numeric

import io.github.wafarm.calculator.interpreter.objects.NumericObject
import kotlin.math.pow

class DecimalNumber(val value: Double) : NumericObject() {
    override fun plus(other: NumericObject): NumericObject {
        if (other is DecimalNumber) {
            return DecimalNumber(value + other.value)
        } else if (other is IntegerNumber) {
            return DecimalNumber(value + other.value)
        }
        throw IllegalStateException()
    }

    override fun minus(other: NumericObject): NumericObject {
        if (other is DecimalNumber) {
            return DecimalNumber(value - other.value)
        } else if (other is IntegerNumber) {
            return DecimalNumber(value - other.value)
        }
        throw IllegalStateException()
    }

    override fun times(other: NumericObject): NumericObject {
        if (other is DecimalNumber) {
            return DecimalNumber(value * other.value)
        } else if (other is IntegerNumber) {
            return DecimalNumber(value * other.value)
        }
        throw IllegalStateException()
    }

    override fun div(other: NumericObject): NumericObject {
        if (other is DecimalNumber) {
            return DecimalNumber(value / other.value)
        } else if (other is IntegerNumber) {
            return DecimalNumber(value / other.value)
        }
        throw IllegalStateException()
    }

    override fun unaryMinus(): NumericObject {
        return DecimalNumber(-value)
    }

    override fun pow(other: NumericObject): NumericObject {
        if (other is IntegerNumber) {
            return DecimalNumber(value.pow(other.value))
        } else if (other is DecimalNumber) {
            return DecimalNumber(value.pow(other.value))
        }
        throw IllegalStateException()
    }

    override fun stringRepresentation(): String {
        return value.toString()
    }

    companion object {
        fun of(value: Double): DecimalNumber {
            return DecimalNumber(value)
        }
    }
}
