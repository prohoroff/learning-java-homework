package main.groovy.by.prohor.task1


import java.util.regex.Matcher
import java.util.regex.Pattern


class TestProductCalculate {

    static void main(String[] args) {
        String value = '2 * 2   -10 -10 - 10 / (7  -5+12)'
        String replace = replaceAllEmpty(value)
        String result = resultExpression(replace)
        println("${replace} = ${result} ")
    }

    static String replaceAllEmpty(String value) {
        value.replaceAll("[\\s]", "")
    }

    static String resultExpression(String enterValue) {
        String resultBrackets = resultExpressionInBrackets(enterValue)
        String multiply = resultExpressionWithMultiply(resultBrackets)
        String divide = resultExpressionWithDivide(multiply)
        String add = resultExpressionWithAdd(divide)
        String minus = resultExpressionWithSub(add)
        minus
    }


    static def resultExpressionInBrackets(String enterValue) {
        Pattern pattern = Pattern.compile('\\(.*\\)')
        Matcher matcher = pattern.matcher(enterValue)

        def findValue = ''
        while (matcher.find()) {
            findValue = matcher.group()
        }
        if (findValue != '') {
            def deleteAllBrackets = findValue.replaceAll("[)(]", '')
            return enterValue.replaceAll('\\(.*\\)', calculationFindValue(deleteAllBrackets).toString())
        }
        enterValue
    }

    static Integer calculationFindValue(String findValue) {

//TODO refactor for  negative numbers
        def operands = findValue.split('[*+-/]')

        def result = 0
        switch (findValue.find("[\\*/\\+-]")) {
            case '*': operands[0].toInteger() * operands[1].toInteger()
                break
            case '/': operands[0].toInteger() / operands[1].toInteger()
                break
            case '+': operands[0].toInteger() + operands[1].toInteger()
                break
            case '-': operands[0].toInteger() - operands[1].toInteger()
                break
            default: println('Error! Enter correct operator')
                result
        }
    }

    static def resultExpressionWithMultiply(String enterValue) {
        Pattern pattern = Pattern.compile('\\d*\\*-?\\d*')
        Matcher matcher = pattern.matcher(enterValue)

        def findValue = ''
        while (matcher.find()) {
            findValue = matcher.group()
        }
        if (findValue != '') {
            return enterValue.replaceAll('\\d*\\*-?\\d*', calculationFindValue(findValue).toString())
        }
        enterValue
    }

    static def resultExpressionWithDivide(String enterValue) {
        Pattern pattern = Pattern.compile('\\d*/-?\\d*')
        Matcher matcher = pattern.matcher(enterValue)

        def findValue = ''
        while (matcher.find()) {
            findValue = matcher.group()
        }
        if (findValue != '') {
            return enterValue.replaceAll('\\d*/-?\\d*', calculationFindValue(findValue).toString())
        }
        enterValue
    }

    static String resultExpressionWithAdd(String enterValue) {
        if (enterValue.find('[-*/]') == null) {
            def splitValue = enterValue.split('[+]')
            return addAllValue(splitValue)
        }
        Pattern pattern = Pattern.compile('\\d*\\+-?\\d*')
        Matcher matcher = pattern.matcher(enterValue)

        def findValue = ''
        while (matcher.find()) {
            findValue = matcher.group()
        }
        if (findValue != '') {
            return enterValue.replaceAll('\\d*\\+-?\\d*', calculationFindValue(findValue).toString())
        }
        enterValue
    }


    static String addAllValue(String... splitResult) {
        def result = 0
        for (String value : splitResult) {
            result += value.toInteger()
        }
        result.toString()
    }

    static String resultExpressionWithSub(String enterValue) {
        if (enterValue.find('[+*/]') == null) {
            def splitValue = enterValue.split('[-]')
            return SubAllValue(splitValue)
        }
        Pattern pattern = Pattern.compile('\\d*--?\\d*')
        Matcher matcher = pattern.matcher(enterValue)

        def findValue = ''
        while (matcher.find()) {
            findValue = matcher.group()
        }
        if (findValue != '') {
            return enterValue.replaceAll('\\d*--?\\d*', calculationFindValue(findValue).toString())
        }
        enterValue
    }

    static String SubAllValue(String... splitResult) {
        def result = splitResult[0].toInteger()
        for (int i = 1; i < splitResult.length; i++) {
            result -= splitResult[i].toInteger()
        }
        result.toString()
    }
}




