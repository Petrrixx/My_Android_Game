package GameObject

interface Enemy : Character {
    fun getBuffed()
    fun onDeath()
    fun attackFriendly(targets: Array<Allies>)
    fun levelUp()
    fun getLevel(): Int
    fun isBoss(): Boolean
    fun getDefense(): Int
}