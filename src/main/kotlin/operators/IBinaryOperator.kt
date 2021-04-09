package operators

/**
 * An interface for all binary operators
 */
interface IBinaryOperator {
    /**
     * Apply operator for two operands
     */
    fun apply(t1: Any, t2: Any): Any

    override fun toString(): String
}