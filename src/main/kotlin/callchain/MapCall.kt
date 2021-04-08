package callchain

import expressions.Expression

class MapCall(val expression: Expression) : Call {
    override fun apply(elements: List<Int>) = elements.map { expression.evaluate(it) as Int }
    override fun toString(): String = "map{$expression}"
}