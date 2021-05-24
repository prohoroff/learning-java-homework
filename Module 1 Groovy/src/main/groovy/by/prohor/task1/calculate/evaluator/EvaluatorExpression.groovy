package main.groovy.by.prohor.task1.calculate.evaluator

import main.groovy.by.prohor.task1.calculate.expression.Expression
import main.groovy.by.prohor.task1.calculate.parser.MathParser
import main.groovy.by.prohor.task1.calculate.parser.lexer.Lexeme
import main.groovy.by.prohor.task1.calculate.parser.lexer.MathLex

class EvaluatorExpression {

     static double evalExpression(String line) {
        MathLex lexer = new MathLex(line)
        List<Lexeme> lexemes = lexer.tokenize()

         Expression expression = new MathParser().parse(lexemes)

        expression.eval()
    }
}
