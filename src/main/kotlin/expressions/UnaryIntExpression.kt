package expressions

import operators.UnaryOperator

class UnaryIntExpression(
    private var ex: Expression,
    private val op: UnaryOperator
) :
    VarReplaceableExpression {

    override fun evaluate(element: Int): Any {
        return op.apply(ex.evaluate(element))
    }

    override fun replaceVar(expression: Expression) {
        if (ex is VarExpression)
            ex = expression
        else if (ex is VarReplaceableExpression)
            (ex as? VarReplaceableExpression)?.replaceVar(expression)
    }

    override fun toString() = "$op$ex"

    override fun clone() = UnaryIntExpression(ex.clone(), op)
}