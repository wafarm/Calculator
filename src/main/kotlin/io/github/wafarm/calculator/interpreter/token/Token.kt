package io.github.wafarm.calculator.interpreter.token

data class Token(
    val type: TokenType,
    val lexeme: String,
    val literal: Any?
) {
    override fun toString(): String {
        return "$type $lexeme $literal"
    }
}
