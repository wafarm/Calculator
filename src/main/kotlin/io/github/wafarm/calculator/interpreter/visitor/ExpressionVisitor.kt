package io.github.wafarm.calculator.interpreter.visitor

import io.github.wafarm.calculator.interpreter.ExpressionEvaluateError
import io.github.wafarm.calculator.interpreter.Interpreter.InterpreterContext
import io.github.wafarm.calculator.interpreter.ast.*
import io.github.wafarm.calculator.interpreter.objects.BaseObject
import io.github.wafarm.calculator.interpreter.objects.FunctionObject
import io.github.wafarm.calculator.interpreter.objects.NumericObject
import io.github.wafarm.calculator.interpreter.token.TokenType

class ExpressionVisitor(private val context: InterpreterContext) : Visitor<BaseObject> {
    override fun visitFunctionCallAST(ast: FunctionCallAST): BaseObject {
        val arguments = ast.arguments.map {
            it.accept(this)
        }
        val function = ast.function.accept(this)
        if (function !is FunctionObject) {
            throw ExpressionEvaluateError("$function is not callable.")
        }
        return function.call(arguments, context)
    }

    override fun visitBinaryExpressionAST(ast: BinaryExpressionAST): BaseObject {
        val left = ast.left.accept(this)
        val right = ast.right.accept(this)
        if (left is NumericObject && right is NumericObject) {
            return when (ast.operator.type) {
                TokenType.PLUS -> left + right
                TokenType.MINUS -> left - right
                TokenType.STAR -> left * right
                TokenType.SLASH -> left / right
                TokenType.POWER -> left.pow(right)
                else -> throw ExpressionEvaluateError("Unrecognized operator ${ast.operator.type}")
            }
        }
        throw ExpressionEvaluateError("Cannot perform ${ast.operator.type} between ${left.type} and ${right.type}.")
    }

    override fun visitUnaryExpressionAST(ast: UnaryExpressionAST): BaseObject {
        val right = ast.right.accept(this)
        if (right is NumericObject) {
            return when (ast.operator.type) {
                TokenType.MINUS -> -right
                else -> throw ExpressionEvaluateError("Unrecognized operator ${ast.operator.type}")
            }
        }
        throw ExpressionEvaluateError("Cannot perform ${ast.operator.type} on ${right.type}.")
    }

    override fun visitNumberAST(ast: NumberAST): BaseObject {
        return ast.number
    }

    override fun visitIdentifierAST(ast: IdentifierAST): BaseObject {
        return context.getIdentifier(ast.identifier)
            ?: throw ExpressionEvaluateError("Unrecognized identifier ${ast.identifier}")
    }
}
