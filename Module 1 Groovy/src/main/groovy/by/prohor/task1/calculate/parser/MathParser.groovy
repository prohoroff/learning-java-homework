package main.groovy.by.prohor.task1.calculate.parser

import main.groovy.by.prohor.task1.calculate.expression.*
import main.groovy.by.prohor.task1.calculate.expression.atom_value.Atom
import main.groovy.by.prohor.task1.calculate.expression.atom_value.ExpressionAtom
import main.groovy.by.prohor.task1.calculate.expression.atom_value.NumberAtom
import main.groovy.by.prohor.task1.calculate.parser.lexer.Lexeme
import main.groovy.by.prohor.task1.calculate.parser.lexer.Token

class MathParser {
    private int position = 0

    Expression parse(List<Lexeme> lexemes) throws MathParseException {
        Expression result = expression(lexemes)
        consume(lexemes, Token.EOF)
        result
    }

    private Expression expression(List<Lexeme> lexemes) throws MathParseException {
        Expression result = multiExpression(lexemes)

        while (true) {
            if (check(lexemes, Token.OPERATOR, "+")) {
                Expression right = multiExpression(lexemes)
                result = new Addition(left: result, right: right)
                continue
            }

            if (check(lexemes, Token.OPERATOR, "-")) {
                Expression right = multiExpression(lexemes)
                result = new Subtraction(left: result, right: right)
                continue
            }
            break
        }
        result
    }

    private Expression multiExpression(List<Lexeme> lexemes) {
        Expression result = atom(lexemes)

        while (true) {
            if (check(lexemes, Token.OPERATOR, "*")) {
                Expression right = atom(lexemes)
                result = new Multiplication(left: result, right: right)
                continue
            }

            if (check(lexemes, Token.OPERATOR, "/")) {
                Expression right = atom(lexemes)
                result = new Division(left: result, right: right)
                continue
            }
            break
        }
        result
    }

    private Atom atom(List<Lexeme> lexemes) {
        if (check(lexemes, Token.NUMBER)) {
            double value = Double.parseDouble(prev(lexemes).getValue())
            return new NumberAtom(value: value)
        } else if (check(lexemes, Token.BRACKET, "(")) {
            Expression expression = expression(lexemes)
            consume(lexemes, Token.BRACKET, ")")
            return new ExpressionAtom(expression: expression)
        }

        throw new MathParseException("unexpected atom lexeme: " + current(lexemes))
    }


    private boolean check(final List<Lexeme> lexemes, Token type) {
        Lexeme token = get(lexemes, 0)
        if (token.getToken() != type) {
            return false
        }
        position++
        true
    }

    private boolean check(final List<Lexeme> lexemes, Token type, String value) {
        Lexeme token = get(lexemes, 0)
        if (token.getToken() != type) {
            return false
        }
        if (!String.valueOf(token.getValue()).equalsIgnoreCase(String.valueOf(value))) {
            return false
        }
        position++
        true
    }

    private Lexeme current(List<Lexeme> lexemes) {
        get(lexemes, 0)
    }

    private Lexeme prev(List<Lexeme> lexemes) {
        get(lexemes, -1)
    }

    private Lexeme get(final List<Lexeme> lexemes, int offset) {
        if (position + offset < 0) {
            throw new IndexOutOfBoundsException("Is no lexeme at [${position + offset}] position")
        }

        if (position + offset >= lexemes.size()) {
            return new Lexeme(Token.EOF, null)
        }

        lexemes.get(position + offset)
    }

    private void consume(final List<Lexeme> lexemes, Token token) {
        Lexeme lexeme = get(lexemes, 0)
        if (lexeme.getToken() != token) {
            throw new MathParseException("expected ${token}, but found ${lexeme}")
        }
        position++
    }

    private void consume(final List<Lexeme> lexemes, Token token, String value) {
        Lexeme lexeme = get(lexemes, 0)
        if (lexeme.getToken() != token) {
            throw new MathParseException("expected " + token + ", but found " + lexeme)
        }
        if (!lexeme.getValue().equalsIgnoreCase(value)) {
            throw new MathParseException("expected " + token + ", but found " + lexeme)
        }
        position++
    }
}
