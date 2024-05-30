package GameObject

abstract class Enemies : Enemy {
    protected var health = 100f
    protected var damage = 10
    protected var level = 1
    protected var defense = 0
    protected var isBoss = false
    protected var isBuffed = false

    override fun move(howFast: Int) {
        TODO("Not yet implemented")
    }

    override fun takeDamage(howMuch: Int) {
        val reduced = (howMuch * (1 - defense / 100.0)).toFloat()
        health -= reduced
    }

    override fun getHealth(): Float {
        return health
    }

    override fun getDamage(): Int {
        return if (isBoss) {
            if (isBuffed) damage + 20 else damage + 10
        } else {
            damage
        }
    }

    override fun getBuffed() {
        isBuffed = !isBuffed
    }

    override fun onDeath() {
        TODO("Not yet implemented")
    }

    override fun attackFriendly(targets: Array<Allies>) {
        TODO("Not yet implemented")
    }

    override fun levelUp() {
        level++
    }

    override fun getLevel(): Int {
        return level
    }

    override fun isBoss(): Boolean {
        return isBoss
    }

    override fun getDefense(): Int {
        if (isBoss()) {
            defense += (5 * getLevel())
            if (isBuffed) {
                defense += 10
            }
        }
        return defense
    }
}