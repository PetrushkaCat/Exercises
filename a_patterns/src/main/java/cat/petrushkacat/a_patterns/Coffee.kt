package cat.petrushkacat.a_patterns


class CoffeeKotlin(
    val coffeeBase: CoffeeBase = CoffeeBase.Single,
    val hasMilk: Boolean = false,
    val hasCream: Boolean = false,
    val hasSugar: Boolean = false,
    val hasCinnamon: Boolean = false,
    val hasSyrup: Boolean = false,
)

class CoffeeJava private constructor() {

    private constructor(builder: CoffeeBuilder) : this() {
        coffeeBase = builder.coffeeBase
        hasMilk = builder.hasMilk
        hasCream = builder.hasCream
        hasSugar = builder.hasSugar
        hasCinnamon = builder.hasCinnamon
        hasSyrup = builder.hasSyrup
    }

    var coffeeBase: CoffeeBase = CoffeeBase.Single
        private set
    var hasMilk: Boolean = false
        private set
    var hasCream: Boolean = false
        private set
    var hasSugar: Boolean = false
        private set
    var hasCinnamon: Boolean = false
        private set
    var hasSyrup: Boolean = false
        private set


    class CoffeeBuilder(val coffeeBase: CoffeeBase) {

        var hasMilk: Boolean = false
            private set
        var hasCream: Boolean = false
            private set
        var hasSugar: Boolean = false
            private set
        var hasCinnamon: Boolean = false
            private set
        var hasSyrup: Boolean = false
            private set


        fun addMilk(): CoffeeBuilder {
            hasMilk = true
            return this
        }

        fun addCream(): CoffeeBuilder {
            hasCream = true
            return this
        }

        fun addSugar(): CoffeeBuilder {
            hasSugar = true
            return this
        }

        fun addCinnamon(): CoffeeBuilder {
            hasCinnamon = true
            return this
        }

        fun addSyrup(): CoffeeBuilder {
            hasSyrup = true
            return this
        }

        fun build(): CoffeeJava {
            return CoffeeJava(this)
        }
    }

    override fun toString(): String {
        return "Coffee(coffeeBase=$coffeeBase, hasMilk=$hasMilk, hasCream=$hasCream, hasSugar=$hasSugar, hasCinnamon=$hasCinnamon, hasSyrup=$hasSyrup)"
    }
}

enum class CoffeeBase {
    Single,
    Double
}