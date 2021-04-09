package expressions

/**
 * Representation of any expression
 */
interface Expression : Cloneable {
    /**
     * Evaluates the result of an expression using the given value as the value of a variable
     *
     * @param element value for a variable
     * @return calculation result
     */
    fun evaluate(element: Int): Any

    override fun toString(): String

    public override fun clone(): Expression
}