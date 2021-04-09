package callchain

import exceptions.WrongExpressionTypeException
import expressions.*
import operators.BinaryOperator

class CallChain(private val chain: List<Call>) {
    fun apply(list: List<Int>): List<Int> {
        var newList: List<Int> = list
        try {
            for (part in chain) {
                newList = part.apply(newList)
            }
        } catch (e: ClassCastException) {
            throw WrongExpressionTypeException()
        }
        return newList
    }

    fun toFilterMap(): CallChain {
        var filter: FilterCall? = null
        var map: MapCall? = null
        var currentVariableExpression: Expression? = null
        var currentFilterExpression: Expression? = null
        for (call in chain) {
            if (call is MapCall) {
                currentVariableExpression =
                    if (currentVariableExpression == null) {
                        call.expression.clone()
                    } else {
                        (call.expression.clone() as? VarReplaceableExpression)?.also {
                            it.replaceVar(
                                currentVariableExpression as Expression
                            )
                        }
                            ?: currentVariableExpression
                    }
                map = MapCall(currentVariableExpression.clone())
            } else if (call is FilterCall) {
                val newFilterExpression = call.expression.clone()
                if (newFilterExpression is VarReplaceableExpression && currentVariableExpression != null) {
                    newFilterExpression.replaceVar(currentVariableExpression)
                }
                currentFilterExpression =
                    (if (currentFilterExpression == null)
                        newFilterExpression
                    else BinaryExpression(
                        currentFilterExpression,
                        call.expression.clone()
                            .also {
                                if (it is VarReplaceableExpression) currentVariableExpression?.let { currentVar ->
                                    it.replaceVar(
                                        currentVar
                                    )
                                }
                            },
                        BinaryOperator.AND
                    ))
                filter = FilterCall(currentFilterExpression.clone())
            }
        }

        return CallChain(
            listOf(
                filter ?: FilterCall(BinaryExpression(NumExpression(1), NumExpression(1), BinaryOperator.EQUALS)),
                map ?: MapCall(VarExpression())
            )
        )
    }

    override fun toString(): String = chain.joinToString(separator = "%>%")
}