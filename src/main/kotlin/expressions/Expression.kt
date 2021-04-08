package expressions

interface Expression : Cloneable {
    fun evaluate(element: Int): Any
    override fun toString(): String
    public override fun clone(): Expression
}