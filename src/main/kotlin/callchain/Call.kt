package callchain

import expressions.Expression

interface Call {
    fun apply(elements: List<Int>) : List<Int>
    override fun toString(): String
}