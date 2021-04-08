package operators

enum class UnaryOperator : IUnaryOperator {
    MINUS {
        override fun apply(t: Any) = -(t as Int)
        override fun toString() = "-"
    }
}