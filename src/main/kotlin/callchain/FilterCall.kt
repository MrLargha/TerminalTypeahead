package callchain

import expressions.Expression

class FilterCall(val expression: Expression) : Call {
    override fun apply(elements: List<Int>) = elements.filter { expression.evaluate(it) as Boolean }
    override fun toString(): String = "filter{$expression}"
}