package io.github.wafarm.calculator.interpreter.visitor

import io.github.wafarm.calculator.interpreter.ast.BinaryExpressionAST
import io.github.wafarm.calculator.interpreter.ast.IdentifierAST
import io.github.wafarm.calculator.interpreter.ast.NumberAST
import io.github.wafarm.calculator.interpreter.ast.UnaryExpressionAST

interface Visitor<S> {
    fun visitBinaryExpressionAST(ast: BinaryExpressionAST): S
    fun visitUnaryExpressionAST(ast: UnaryExpressionAST): S
    fun visitNumberAST(ast: NumberAST): S
    fun visitIdentifierAST(ast: IdentifierAST): S
}
