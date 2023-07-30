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
        return tokens
    }

    private fun scanToken() {
        val c = advance()
        when (c) {
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            '+' -> addToken(TokenType.PLUS)
            '-' -> addToken(TokenType.MINUS)
            '*' -> addToken(TokenType.STAR)
            '/' -> addToken(TokenType.SLASH)
            in '0'..'9' -> number()
            ' ' -> Unit // Skip whitespaces (tabs and newlines should not exist in source)
            else -> {
                throw LexError("Unexpected character '$c'.")
            }
        }
    }

    private fun number() {
        while (Character.isDigit(peek())) advance()

        if (peek() == '.') {
            advance()
            while (Character.isDigit(peek())) advance()
        }

        addToken(TokenType.NUMBER, source.substring(start, current).toDouble())
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

    class LexError(message: String) : Error(message)
}
