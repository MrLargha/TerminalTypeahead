package expressions

class NumExpression(private val num: Int) : Expression {
    override fun evaluate(element: Int): Int = num
    override fun toString() = "$num"
    override fun clone() = NumExpression(num)
}