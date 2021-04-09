package expressions

/**
 * Expression, that represents a variable
 */
class VarExpression : Expression {
    override fun evaluate(element: Int): Int = element
    override fun toString() = "element"
    override fun clone() = VarExpression()
}