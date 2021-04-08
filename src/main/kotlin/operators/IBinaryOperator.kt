package operators

interface IBinaryOperator{
    fun apply(t1: Any, t2: Any): Any
    override fun toString(): String
}