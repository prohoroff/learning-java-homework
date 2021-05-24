package main.groovy.by.prohor.task1.calculate.expression

class Addition implements Expression {

    Expression left
    Expression right

    @Override
    double eval(){
        left.eval() + right.eval()
    }
}
