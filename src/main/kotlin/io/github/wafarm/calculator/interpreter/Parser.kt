package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.ast.*
import io.github.wafarm.calculator.interpreter.bulitin.numeric.DecimalNumber
import io.github.wafarm.calculator.interpreter.bulitin.numeric.IntegerNumber
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
        var expr = exp()
        while (match(TokenType.STAR, TokenType.SLASH)) {
            val operator = advance()
            val right = exp()
            expr = BinaryExpressionAST(operator, expr, right)
        }
        return expr
    }

    // ^
    private fun exp(): BaseAST {
        val left = unary()
        if (match(TokenType.POWER)) {
            val operator = advance()
            val right = exp()
            return BinaryExpressionAST(operator, left, right)
        }
        return left
    }

    private fun unary(): BaseAST {
        if (match(TokenType.MINUS)) {
            return UnaryExpressionAST(advance(), primary())
        }
        return call()
    }

    private fun call(): BaseAST {
        var expr = primary()

        while (match(TokenType.LEFT_BRACKET)) {
            advance()
            val arguments = callArguments()
            consume(TokenType.RIGHT_BRACKET, "Expected ']'")
            expr = FunctionCallAST(expr, arguments)
        }

        return expr
    }

    private fun primary(): BaseAST {
        return when (peek().type) {
            TokenType.LEFT_PAREN -> {
                advance()
                val expr = expression()
                consume(TokenType.RIGHT_PAREN, "Expected ')'")
                expr
            }

            TokenType.INTEGER -> {
                integer()
            }

            TokenType.DECIMAL -> {
                decimal()
            }

            TokenType.HISTORY -> {
                history()
            }

            TokenType.IDENTIFIER -> {
                identifier()
            }

            else -> {
                throw ParseError("Unexpected token ${peek().type}")
            }
        }
    }

    private fun integer(): BaseAST {
        val number = consume(TokenType.INTEGER, "Expected an integer").literal as Int
        return NumberAST(IntegerNumber.of(number))
    }

    private fun decimal(): BaseAST {
        val number = consume(TokenType.DECIMAL, "Expected a decimal").literal as Double
        return NumberAST(DecimalNumber.of(number))
    }

    private fun history(): BaseAST {
        val index = consume(TokenType.HISTORY, "Expected a history").literal as Int
        return FunctionCallAST(IdentifierAST("Out"), listOf(NumberAST(IntegerNumber.of(index))))
    }

    private fun identifier(): BaseAST {
        val identifier = consume(TokenType.IDENTIFIER, "Expected an identifier").literal as String
        return IdentifierAST(identifier)
    }

    private fun callArguments(): List<BaseAST> {
        if (match(TokenType.RIGHT_BRACKET)) {
            return listOf()
        }

        val arguments: MutableList<BaseAST> = mutableListOf()

        while (true) {
            arguments.add(expression())
            if (match(TokenType.RIGHT_BRACKET)) break
            consume(TokenType.COMMA, "Expected ','")
        }

        return arguments
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
}
