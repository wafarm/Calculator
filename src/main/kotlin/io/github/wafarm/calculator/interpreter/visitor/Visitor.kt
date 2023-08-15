package io.github.wafarm.calculator.interpreter.visitor

import io.github.wafarm.calculator.interpreter.ast.*

interface Visitor<S> {
    fun visitFunctionCallAST(ast: FunctionCallAST): S
    fun visitBinaryExpressionAST(ast: BinaryExpressionAST): S
    fun visitUnaryExpressionAST(ast: UnaryExpressionAST): S
    fun visitNumberAST(ast: NumberAST): S
    fun visitIdentifierAST(ast: IdentifierAST): S
}
