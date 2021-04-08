import exceptions.WrongExpressionTypeException
import kotlin.test.*

class CallChainTest {
    companion object {
        val expressions = mutableMapOf(
            "filter{(element>10)}%>%filter{(element<20)}" to { x: List<Int> ->
                x.filter { it > 10 }.filter { it < 20 }
            },
            "map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}" to { x: List<Int> ->
                x.map { it + 10 }.filter { it > 10 }.map { it * it }
            },
            "filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)" to { x: List<Int> ->
                x.filter { it > 0 }.filter { it < 0 }.map { it * it }
            },
            "map{(element - 100)}%>%map{(element * 2)}%>%filter{((element * element) > 100)}%>%map{(element*10)}" to { x: List<Int> ->
                x.map { it - 100 }.map { it * 2 }.filter { (it * it) > 100 }.map { it * 10 }
            },

            )
        val wrongTypeExpressions = mutableListOf(
            "map{(element>10)}%>%map{(element<20)}",
            "map{(element>0)}%>%map{(element<0)}%>%filter{(element*element)"
        )
        val data = List(1000) { it - 500 }
    }

    @Test
    fun simpleEvaluatingTest() {
        expressions.entries.forEach { entry ->
            val callChainBuilder = CallChainBuilder(entry.key)
            val callChain = callChainBuilder.build()
            val chainResult = callChain.apply(data)
            val lambdaResult = entry.value(data)
            assertTrue { chainResult == lambdaResult }
        }
    }

    @Test
    fun simplifyTest() {
        expressions.entries.forEach { entry ->
            val default = CallChainBuilder(entry.key).build()
            val simple = CallChainBuilder(entry.key).build().toFilterMap()
            println("Default: $default")
            println("Simplified: $simple")
            assertTrue("Default: ${default.apply(data)}\nSimplified: ${simple.apply(data)}") {
                default.apply(data) == simple.apply(data)
            }
        }
    }

    @Test
    fun wrongExpressionTypeTest() {
        wrongTypeExpressions.forEach {
            assertFailsWith<WrongExpressionTypeException> {
                val expression = CallChainBuilder(it).build()
                expression.apply(data)
            }
        }
    }
}