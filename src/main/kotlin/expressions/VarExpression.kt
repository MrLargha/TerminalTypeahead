package expressions

class VarExpression : Expression {
    override fun evaluate(element: Int): Int = element
    override fun toString() = "element"
    override fun clone() = VarExpression()
}