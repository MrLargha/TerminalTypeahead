package expressions

import operators.BinaryOperator

/**
 * Expression, that consists of two expressions and operators between them
 */
class BinaryExpression(
    private var ex1: Expression,
    private var ex2: Expression,
    private val operator: BinaryOperator
) : VarReplaceableExpression, Cloneable {
    override fun evaluate(element: Int): Any = operator.apply(ex1.evaluate(element), ex2.evaluate(element))

    override fun replaceVar(expression: Expression) {
        if (ex1 is VarReplaceableExpression)
            (ex1 as? VarReplaceableExpression)?.replaceVar(expression)
        else if (ex1 is VarExpression)
            ex1 = expression

        if (ex2 is VarReplaceableExpression)
            (ex2 as? VarReplaceableExpression)?.replaceVar(expression)
        else if (ex2 is VarExpression)
            ex2 = expression
    }

    override fun toString(): String = "($ex1$operator$ex2)"

    override fun clone(): BinaryExpression {
        return BinaryExpression(ex1.clone(), ex2.clone(), operator)
    }
}