package io.github.wafarm.calculator.interpreter.ast

import io.github.wafarm.calculator.interpreter.visitor.Visitor

class IdentifierAST(val identifier: String) : BaseAST {
    override fun <S> accept(visitor: Visitor<S>): S {
        return visitor.visitIdentifierAST(this)
    }
}
