package main.groovy.by.prohor.task1.calculate.parser.lexer

class Lexeme {

    Token token
    String value

    Lexeme(Token token, String value) {
        this.token = token
        this.value = value
    }
}
