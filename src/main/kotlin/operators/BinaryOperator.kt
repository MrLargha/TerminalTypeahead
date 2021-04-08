package operators

enum class BinaryOperator : IBinaryOperator {
    LESS {
        override fun apply(t1: Any, t2: Any) = (t1 as Int) < (t2 as Int)
        override fun toString() = "<"
    },

    GREATER {
        override fun apply(t1: Any, t2: Any) = (t1 as Int) > (t2 as Int)
        override fun toString() = ">"
    },

    EQUALS {
        override fun apply(t1: Any, t2: Any) = t1 == t2
        override fun toString() = "="
    },

    OR {
        override fun apply(t1: Any, t2: Any) = (t1 as Boolean) || (t2 as Boolean)
        override fun toString() = "|"
    },

    AND {
        override fun apply(t1: Any, t2: Any) = (t1 as Boolean) && (t2 as Boolean)
        override fun toString() = "&"
    },

    PLUS {
        override fun apply(t1: Any, t2: Any): Int = t1 as Int + t2 as Int
        override fun toString() = "+"
    },

    MUL {
        override fun apply(t1: Any, t2: Any): Int = t1 as Int * t2 as Int
        override fun toString() = "*"
    },

    MINUS {
        override fun apply(t1: Any, t2: Any): Int = t1 as Int - t2 as Int
        override fun toString() = "-"
    };
}