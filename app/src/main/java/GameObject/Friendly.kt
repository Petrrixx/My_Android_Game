package GameObject

interface Friendly : Character {
    fun attackEnemy(target: Array<Enemies>)
    fun heal(howMuch: Int)
    fun upgrade(howMuch: Int)
    fun getUpgradeLevel(): Int
    fun isHealer(): Boolean
    fun isDps(): Boolean
    fun isSupport(): Boolean
    fun isPlayable(): Boolean
}