package main.groovy.by.prohor.task1.calculate.parser.lexer

class MathLex {

    private TokenizeState state = TokenizeState.DEFAULT
    private String expression
    private String value = ''
    private int position = -1

    MathLex(String expression) {
        this.expression = expression
    }

    List<Lexeme> tokenize() throws TokenException {
        List<Lexeme> lexemes = new ArrayList<>()

        def ch
        while ((ch = get()) != -1) {
            Lexeme lexeme = nextLexeme(ch)
            if (lexeme != null) {
                lexemes.add(lexeme)
            }
        }

        if (state != TokenizeState.DEFAULT) {
            Lexeme lexeme = nextLexeme(ch)
            if (lexeme != null) {
                lexemes.add(lexeme)
            }
        }
        lexemes.add(new Lexeme(Token.EOF, null))
        lexemes
    }

    private Lexeme nextLexeme(def b) throws TokenException {
        def ch = b

        switch (state) {
            case 'DEFAULT':
                if (Character.isDigit(ch as char)) {
                    state = TokenizeState.NUMBER_START
                    position -= 1
                } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    state = TokenizeState.OPERATOR
                    position -= 1
                } else if (ch == '(' || ch == ')') {
                    state = TokenizeState.BRACKET
                    position -= 1
                } else if (Character.isWhitespace(ch as char)) {
                    return null
                } else {
                    throw new TokenException(
                            "unexpected character '" + ch +
                                    "' in state " + state
                    )
                }
                break
            case "NUMBER_START":
                if (Character.isDigit(ch as char)) {
                    value += ch
                } else if (ch == '.') {
                    value += ch
                    state = TokenizeState.NUMBER_END
                } else {
                    return createToken(Token.NUMBER, true)
                }
                break
            case "NUMBER_END":
                if (Character.isDigit(ch as char)) {
                    value += ch
                } else {
                    return createToken(Token.NUMBER, true)
                }
                break
            case "OPERATOR":
                if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                    value += ch
                    return createToken(Token.OPERATOR, false)
                }

                break
            case "BRACKET":
                if (ch == '(' || ch == ')') {
                    value += ch
                    return createToken(Token.BRACKET, false)
                }
                break
        }
        null
    }

    private def get() {
        position += 1
        position < expression.length() ?
                expression.charAt(position) : -1
    }

    private Lexeme createToken(Token token, boolean back) {
        Lexeme lexeme = new Lexeme(token, value)
        value = ""

        state = TokenizeState.DEFAULT
        if (back) {
            position -= 1
        }
        lexeme
    }

    private enum TokenizeState {
        DEFAULT, NUMBER_START, NUMBER_END, OPERATOR, BRACKET
    }
}
