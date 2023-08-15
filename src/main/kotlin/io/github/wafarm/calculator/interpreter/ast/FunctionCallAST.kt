package io.github.wafarm.calculator.interpreter.ast

import io.github.wafarm.calculator.interpreter.visitor.Visitor

class FunctionCallAST(
    val function: BaseAST,
    val arguments: List<BaseAST>
) : BaseAST {
    override fun <S> accept(visitor: Visitor<S>): S {
        return visitor.visitFunctionCallAST(this)
    }
}
