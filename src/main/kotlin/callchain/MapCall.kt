package callchain

import expressions.Expression

/**
 * Call, that applies expression for each element in list, and replaces it by result of call
 *
 * @property expression expression that will be used for transforming elements it must return int value
 */
class MapCall(val expression: Expression) : Call {
    override fun apply(elements: List<Int>) = elements.map { expression.evaluate(it) as Int }
    override fun toString(): String = "map{$expression}"
}