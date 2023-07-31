package io.github.wafarm.calculator.interpreter.visitor

import io.github.wafarm.calculator.interpreter.Interpreter.InterpreterContext
import io.github.wafarm.calculator.interpreter.ast.BinaryExpressionAST
import io.github.wafarm.calculator.interpreter.ast.IdentifierAST
import io.github.wafarm.calculator.interpreter.ast.NumberAST
import io.github.wafarm.calculator.interpreter.ast.UnaryExpressionAST
import io.github.wafarm.calculator.interpreter.token.TokenType
import kotlin.math.pow

class ExpressionVisitor(private val context: InterpreterContext) : Visitor<Double> {
    override fun visitBinaryExpressionAST(ast: BinaryExpressionAST): Double {
        val left = ast.left.accept(this)
        val right = ast.right.accept(this)
        return when (ast.operator.type) {
            TokenType.PLUS -> left + right
            TokenType.MINUS -> left - right
            TokenType.STAR -> left * right
            TokenType.SLASH -> left / right
            TokenType.POWER -> left.pow(right)
            else -> throw ExpressionEvaluateError("Unrecognized operator ${ast.operator.type}")
        }
    }

    override fun visitUnaryExpressionAST(ast: UnaryExpressionAST): Double {
        val right = ast.right.accept(this)
        return when (ast.operator.type) {
            TokenType.MINUS -> -right
            else -> throw ExpressionEvaluateError("Unrecognized operator ${ast.operator.type}")
        }
    }

    override fun visitNumberAST(ast: NumberAST): Double {
        return ast.number
    }

    override fun visitIdentifierAST(ast: IdentifierAST): Double {
        return context.getIdentifier(ast.identifier)
            ?: throw ExpressionEvaluateError("Unrecognized identifier ${ast.identifier}")
    }

    class ExpressionEvaluateError(message: String) : Error(message)
}
