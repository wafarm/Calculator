package io.github.wafarm.calculator.interpreter

import io.github.wafarm.calculator.interpreter.token.Token
import io.github.wafarm.calculator.interpreter.token.TokenType
import java.lang.Character.MIN_VALUE as nullChar

class Lexer(private val source: String) {
    private val tokens: MutableList<Token> = mutableListOf()
    private val length = source.length
    private var start = 0
    private var current = 0

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            start = current
            scanToken()
        }

        addToken(TokenType.EOF)
        return tokens.toList()
    }

    private fun scanToken() {
        when (val c = advance()) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '[' -> addToken(TokenType.LEFT_BRACKET)
            ']' -> addToken(TokenType.RIGHT_BRACKET)
            ',' -> addToken(TokenType.COMMA)
            '+' -> addToken(TokenType.PLUS)
            '-' -> addToken(TokenType.MINUS)
            '*' -> addToken(TokenType.STAR)
            '/' -> addToken(TokenType.SLASH)
            '^' -> addToken(TokenType.POWER)
            '%' -> history()
            in '0'..'9' -> number()
            in CharRange('a', 'z') + CharRange('A', 'Z') -> identifier()
            ' ' -> Unit // Skip whitespaces (tabs and newlines should not exist in source)
            else -> {
                throw LexError("Unexpected character '$c'")
            }
        }
    }

    private fun history() {
        var index = -1
        if (peek() in '0'..'9') {
            advance()
            while (Character.isDigit(peek())) advance()

            if (peek() == '.') {
                throw LexError("Unexpected character '.'")
            }

            index = source.substring(start + 1, current).toInt()
        }

        addToken(TokenType.HISTORY, index)
    }

    private fun number() {
        while (Character.isDigit(peek())) advance()

        var isDecimal = false
        if (peek() == '.') {
            isDecimal = true
            advance()
            while (Character.isDigit(peek())) advance()
        }

        if (isDecimal) {
            addToken(TokenType.DECIMAL, source.substring(start, current).toDouble())
        } else {
            addToken(TokenType.INTEGER, source.substring(start, current).toInt())
        }
    }

    private fun identifier() {
        while (Character.isAlphabetic(peek().code)) advance()

        addToken(TokenType.IDENTIFIER, source.substring(start, current))
    }

    private fun addToken(type: TokenType) {
        addToken(type, null)
    }

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal))
    }

    private fun isAtEnd(): Boolean {
        return current >= length
    }

    private fun advance(): Char {
        return source[current++]
    }

    private fun peek(): Char {
        if (isAtEnd()) return nullChar
        return source[current]
    }
}
