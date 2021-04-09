import exceptions.SyntaxException
import expressions.*
import operators.BinaryOperator
import operators.UnaryOperator
import java.lang.NumberFormatException

/**
 * Builder for Expression objects
 *
 * @see Expression
 */
class ExpressionBuilder(private val expressionString: String) {
    private var currentPosition = 0
    private var openedBrackets = 0

    companion object {
        /**
         * Build expression from given string using ExpressionBuilder
         *
         * @param expression - string with expression to be parsed
         * @return parsed expression
         * @throws SyntaxException if any syntax error in give string
         */
        fun build(expression: String): Expression {
            val builder = ExpressionBuilder(expression.replace(" ", ""))
            return builder.buildImpl()
        }
    }

    private fun buildImpl(): Expression {
        val expr = build(0)

        if (openedBrackets != 0 || currentPosition <= expressionString.lastIndex) {
            throw SyntaxException()
        }
        return expr
    }

    private fun build(state: Int): Expression {
        if (lastState(state)) {
            var expression: Expression
            val isMinus = startWith("-")
            if (isMinus)
                skip("-")
            if (startWith("(")) {
                openedBrackets++
                skip("(")
                expression = build(0)

                if (currentPosition < expressionString.length
                    && expressionString[currentPosition] == ')'
                ) {
                    openedBrackets--
                    skip(")")
                } else {
                    throw SyntaxException()
                }
            } else {
                expression = readSingle()
            }
            if (isMinus)
                expression = UnaryExpression(expression, UnaryOperator.MINUS)
            return expression
        }

        var firstOperand: Expression = build(state + 1)
        var nextOperandString: String?
        while (readStateOperator(state).also { nextOperandString = it } != null) {
            val secondOperand = build(state + 1)
            firstOperand = BinaryExpression(firstOperand, secondOperand, operatorFromString(nextOperandString))
        }
        return firstOperand
    }

    private fun operatorFromString(op: String?) = when (op) {
        "+" -> BinaryOperator.PLUS
        "-" -> BinaryOperator.MINUS
        "*" -> BinaryOperator.MUL
        "|" -> BinaryOperator.OR
        ">" -> BinaryOperator.GREATER
        "<" -> BinaryOperator.LESS
        "&" -> BinaryOperator.AND
        "=" -> BinaryOperator.EQUALS
        else -> throw SyntaxException()
    }

    private val states = arrayOf(
        arrayOf("|"),
        arrayOf("&"),
        arrayOf("=", "<", ">"),
        arrayOf("+", "-"),
        arrayOf("*", "/"),
        null
    )

    private fun lastState(s: Int): Boolean {
        return s + 1 >= states.size
    }

    private fun startWith(s: String): Boolean {
        return expressionString.startsWith(s, currentPosition)
    }

    private fun skip(s: String) {
        if (startWith(s))
            currentPosition += s.length
        while (currentPosition < expressionString.length && expressionString[currentPosition] == ' ')
            currentPosition++
    }


    private fun readStateOperator(state: Int): String? {
        val operators = states[state] ?: return null
        for (operator in operators) {
            if (startWith(operator)) {
                skip(operator)
                return operator
            }
        }
        return null
    }


    private fun readSingle(): Expression {
        val lastPosition = currentPosition

        while (currentPosition < expressionString.length) {
            if (!Character.isLetterOrDigit(expressionString[currentPosition])) break
            currentPosition++
        }

        if (currentPosition > lastPosition) {
            val s = expressionString.substring(lastPosition, currentPosition)
            skip(" ")
            return try {
                NumExpression(s.toInt())
            } catch (e: NumberFormatException) {
                if (s != "element")
                    throw SyntaxException()
                else
                    VarExpression()
            }
        } else {
            throw SyntaxException()
        }
    }
}
  