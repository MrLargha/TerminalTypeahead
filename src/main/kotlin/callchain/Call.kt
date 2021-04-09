package callchain

/**
 * Representation of one call of call-chain
 */
interface Call {
    /**
     * Function that applies call to a list of integers
     *
     * @param elements list of integers for applying a call
     * @return list of integers after applying a call
     */
    fun apply(elements: List<Int>): List<Int>

    override fun toString(): String
}