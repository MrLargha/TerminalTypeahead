package callchain

import expressions.Expression

/**
 * Call, that filters elements in list
 *
 * @property expression expression that will be used for filtering elements it must return bool value
 */
class FilterCall(val expression: Expression) : Call {
    override fun apply(elements: List<Int>) = elements.filter { expression.evaluate(it) as Boolean }
    override fun toString(): String = "filter{$expression}"
}