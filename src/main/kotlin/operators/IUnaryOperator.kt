package operators

/**
 * An interface for all unary operators
 */
interface IUnaryOperator {
    /**
     * Apply operator for an operand
     */
    fun apply(t: Any): Any

    override fun toString(): String
}