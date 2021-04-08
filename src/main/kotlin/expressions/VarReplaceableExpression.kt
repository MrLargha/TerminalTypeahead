package expressions

/**
 * Expression, that can replace VarExpression inside it by another expression,
 * used for converting CallChain to filter-map
 *
 * @see VarExpression
 * @see Expression
 */
interface VarReplaceableExpression: Expression {
    fun replaceVar(expression: Expression)
}