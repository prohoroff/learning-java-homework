package main.groovy.by.prohor.task1.calculate.expression

class Multiplication implements Expression {
    Expression left
    Expression right

    @Override
    double eval() throws Exception {
        return left.eval() * right.eval()
    }
}
