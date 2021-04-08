import exceptions.SyntaxException
import java.util.*
import kotlin.random.Random
import kotlin.test.*

class IntExpressionTest {
    companion object {
        val simpleMathExpressions = mapOf(
            "5 * 5 + 5" to 30,
            "5 + 5 * 5" to 30,
            "5   *    5 + 5    *    5" to 50,
            "(5 + 5) * (5 + 5)" to 100,
            "200 * 3 + 21" to 621,
            "-5 * 10 + 2" to -48,
            "-3 + 3 * 10" to 27,
            "(-3 + 3) * 10" to 0
        )

        val expressionsWithVar = mapOf(
            "element + 1" to { x: Int -> x + 1 },
            "element * element + (5 + 5) * (5 + 5) + element" to { x: Int -> x * x + (5 + 5) * (5 + 5) + x },
            "element + element * element" to { x: Int -> x + x * x },
            "-element + 10" to { x: Int -> -x + 10 },
            "(element + 10) * (element + 10) * (element + 10) + element + 20" to { x: Int -> (x + 10) * (x + 10) * (x + 10) + x + 20 }
        )
    }

    @Test
    fun simpleMathTest() {
        for (element in simpleMathExpressions.entries) {
            val ex = ExpressionBuilder.build(element.key)
            assertNotNull(ex)
            assertTrue { ex.evaluate(0) == element.value }
        }
    }

    @Test
    fun testSimpleExpressionsWithVar() {
        val random = Random(Date().time)
        val randomNumberSeries = List(1000) { random.nextInt(-100, 100) }
        for (element in expressionsWithVar.entries) {
            val ex = ExpressionBuilder.build(element.key)
            assertNotNull(ex)
            randomNumberSeries.forEach {
                assertTrue("Expected ${element.value(it)}, but was: ${ex.evaluate(it)}, for ${element.key}")
                { ex.evaluate(it) == element.value(it) }
            }
        }
    }
}
