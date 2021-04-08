import exceptions.SyntaxException
import kotlin.test.*

class ParserExceptionsTest {
    @Test
    fun checkFails() {
        listOf(
            "fdskfjdlakj21 2j13l k+ 321 * 21 j",
            "10 +* 10",
            "10 ++ 10",
            "(5 + 5) * (5 + 5",
            "(1 + (2 + 2 + (2 + 2))",
            "(1 +3)) * (2 + 2 + (2 + 2))",
            "el + 10",
            "(((((5 + 2))))))",
            "7 / 3",
            "3 + x",
            "3 ---6"
        ).forEach {
            assertFailsWith<SyntaxException>("No exception thrown for $it") {
                ExpressionBuilder.build(it)
            }
        }
    }
}