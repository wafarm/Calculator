package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.ast.BaseAST
import io.github.wafarm.calculator.interpreter.ast.BinaryExpressionAST
import io.github.wafarm.calculator.interpreter.ast.NumberAST
import io.github.wafarm.calculator.interpreter.ast.UnaryExpressionAST
import io.github.wafarm.calculator.interpreter.token.Token
import io.github.wafarm.calculator.interpreter.token.TokenType

class Parser(private val tokens: List<Token>) {
    private var current = 0

    fun parse(): BaseAST {
        val expr = expression()
        consume(TokenType.EOF, "Unexpected token after expression")
        return expr
    }

    private fun expression(): BaseAST {
        return term()
    }

    // + and -
    private fun term(): BaseAST {
        var expr = factor()
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            val operator = advance()
            val right = factor()
            expr = BinaryExpressionAST(operator, expr, right)
        }
        return expr
    }

    // * and /
    private fun factor(): BaseAST {
        var expr = unary()
        while (match(TokenType.STAR, TokenType.SLASH)) {
            val operator = advance()
            val right = unary()
            expr = BinaryExpressionAST(operator, expr, right)
        }
        return expr
    }

    private fun unary(): BaseAST {
        if (match(TokenType.MINUS)) {
            return UnaryExpressionAST(advance(), primary())
        }
        return primary()
    }

    private fun primary(): BaseAST {
        return when (peek().type) {
            TokenType.LEFT_PAREN -> {
                advance()
                val expr = expression()
                consume(TokenType.RIGHT_PAREN, "Expected ')'")
                expr
            }

            TokenType.NUMBER -> {
                number()
            }

            else -> {
                throw ParseError("Unexpected token ${peek().type}")
            }
        }
    }

    private fun number(): BaseAST {
        val number = consume(TokenType.NUMBER, "Expected a number").literal as Double
        return NumberAST(number)
    }

    private fun advance(): Token {
        return tokens[current++]
    }

    private fun peek(): Token {
        return tokens[current]
    }

    private fun match(vararg types: TokenType): Boolean {
        return peek().type in types
    }

    private fun consume(type: TokenType, message: String): Token {
        if (peek().type != type) {
            throw ParseError(message)
        }
        return advance()
    }

    class ParseError(message: String) : Error(message)
}
