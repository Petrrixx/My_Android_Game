package GameObject

interface Character {
    fun move(howFast: Int)
    fun takeDamage(howMuch: Int)
    fun getHealth(): Float
    fun getDamage(): Int
}