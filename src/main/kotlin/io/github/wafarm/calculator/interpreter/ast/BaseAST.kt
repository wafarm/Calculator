package io.github.wafarm.calculator.interpreter.ast

import io.github.wafarm.calculator.interpreter.visitor.Visitor

interface BaseAST {
    fun <S> accept(visitor: Visitor<S>): S
}
