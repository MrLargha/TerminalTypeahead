import java.util.*
import kotlin.random.Random
import kotlin.test.assertFails
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.Test

class BoolExpressionTest {
    companion object {
        val simpleMathExpressions = mapOf(
            "(5 > 1) | (3 < 5)" to true,
            "(5 > 1) & (3 > 5)" to false,
            "(10+10) = (2 * 10)" to true,
            "(10+10) = (3 * 10)" to false
        )

        val expressionsWithVar = mapOf(
            "element > 10" to { x: Int -> x > 10 },
            "element < 10" to { x: Int -> x < 10 },
            "(element < -10) | (element > 10)" to { x: Int -> (x < -10) || (x > 10) },
        )
    }

    @Test
    fun simpleBoolTest() {
        for (element in simpleMathExpressions.entries) {
            val ex = ExpressionBuilder.build(element.key)
            assertNotNull(ex)
            assertTrue("Expected ${element.value}, but was: ${ex.evaluate(0)}, for ${element.key}") {
                ex.evaluate(0) == element.value
            }
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

    @Test
    fun checkFails() {
        assertFails {
            ExpressionBuilder.build("fdskfjdlakj21 2j13l k+ 321 * 21 j")
        }
    }
}