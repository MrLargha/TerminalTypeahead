import callchain.Call
import callchain.CallChain
import callchain.FilterCall
import callchain.MapCall
import exceptions.SyntaxException
import java.text.ParseException

class CallChainBuilder(private val expression: String) {
    fun build(): CallChain {
        val regex = """(filter|map)\{([a-z\(\)0-9\*\+\-\&\|\<\>\s\=]*)\}""".toRegex()
        val matches = regex.findAll(expression)
        val result = mutableListOf<Call>()
        for (match in matches) {
            val (type, expressionString) = match.groups.map { it?.value }.slice(1..2)
            if (expressionString == null) {
                throw SyntaxException()
            }
            val expression =
                ExpressionBuilder.build(expressionString)
            result.add(
                when (type) {
                    "map" -> MapCall(expression)
                    "filter" -> FilterCall(expression)
                    else -> throw SyntaxException()
                }
            )
        }
        if(result.isEmpty()) throw SyntaxException()
        return CallChain(result)
    }
}