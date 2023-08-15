package io.github.wafarm.calculator.interpreter.token

enum class TokenType {
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACKET, RIGHT_BRACKET,
    COMMA, HISTORY,
    PLUS, MINUS, STAR, SLASH, POWER,

    INTEGER, DECIMAL, IDENTIFIER,

    EOF
}
