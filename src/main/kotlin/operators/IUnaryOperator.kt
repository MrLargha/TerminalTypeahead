package operators

interface IUnaryOperator {
    fun apply(t: Any): Any
    override fun toString(): String
}