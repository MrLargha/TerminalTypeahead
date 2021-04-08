import exceptions.SyntaxException
import exceptions.WrongExpressionTypeException
import kotlin.contracts.contract

fun main(args: Array<String>) {
    while (true) {
        print("Enter expression: ")
        val expressionString = readLine()
        expressionString?.let {
            if(it.trim() == ""){
                println("Enter expression!")
                return@let
            }
            try {
                val callChain = CallChainBuilder(expressionString).build()
                println("Interpreted call-chain: $callChain")
                val data = List(30) { it - 10 }
                println("Initial array: ${data.joinToString(separator = " ")}")
                println("Call-chain result: ${callChain.apply(data).joinToString(separator = ", ")}")
                val filterMap = callChain.toFilterMap()
                println("Filter-map: $filterMap")
                println("Filter-map result: ${filterMap.apply(data).joinToString(separator = ", ")}")
            } catch (e: WrongExpressionTypeException) {
                println("TYPE ERROR")
            } catch (e: SyntaxException) {
                println("SYNTAX ERROR")
            }
        }
    }
}