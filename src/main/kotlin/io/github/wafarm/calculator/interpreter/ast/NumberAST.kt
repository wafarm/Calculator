package io.github.wafarm.calculator.interpreter.ast

import io.github.wafarm.calculator.interpreter.objects.numeric.NumericObject
import io.github.wafarm.calculator.interpreter.visitor.Visitor

class NumberAST(val number: NumericObject) : BaseAST {
    override fun <S> accept(visitor: Visitor<S>): S {
        return visitor.visitNumberAST(this)
    }
}
