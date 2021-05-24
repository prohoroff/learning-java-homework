package main.groovy.by.prohor.task1.calculate.expression.atom_value

import main.groovy.by.prohor.task1.calculate.expression.atom_value.Atom

class NumberAtom extends Atom{

    def value

    @Override
    double eval(){
        return value
    }
}
