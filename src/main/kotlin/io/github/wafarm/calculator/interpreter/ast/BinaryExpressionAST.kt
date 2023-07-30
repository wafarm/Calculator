package io.github.wafarm.calculator.interpreter.ast

import io.github.wafarm.calculator.interpreter.token.Token
import io.github.wafarm.calculator.interpreter.visitor.Visitor

class BinaryExpressionAST(
    val operator: Token,
    val left: BaseAST,
    val right: BaseAST
) : BaseAST {
    override fun <S> accept(visitor: Visitor<S>): S {
        return visitor.visitBinaryExpressionAST(this)
    }
}
