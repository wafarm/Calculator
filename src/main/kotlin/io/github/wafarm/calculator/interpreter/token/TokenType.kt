package io.github.wafarm.calculator.interpreter.token

enum class TokenType {
    LEFT_PAREN, RIGHT_PAREN,
    PLUS, MINUS, STAR, SLASH, POWER,

    NUMBER,

    EOF
}
