package GameObject

abstract class Allies : Friendly {
    protected var health: Float = 100f
    protected var damage: Int = 10
    protected var upgrade: Int = 0
    protected var level: Int = 1

    override fun move(howFast: Int) {
        TODO("Not yet implemented")
    }

    override fun takeDamage(howMuch: Int) {
        health -= howMuch
    }

    override fun getHealth(): Float {
        return health
    }

    override fun getDamage(): Int {
        return damage
    }

    override fun attackEnemy(target: Array<Enemies>) {
        TODO("Not yet implemented")
    }

    override fun heal(howMuch: Int) {
        health += howMuch
        if (health > 100) {
            health = 100f
        }
    }

    override fun upgrade(howMuch: Int) {
        upgrade += howMuch
        if (upgrade > 3) {
            upgrade = 3
        }
    }

    override fun getUpgradeLevel(): Int {
        return upgrade
    }

    override fun isHealer(): Boolean {
        return false
    }

    override fun isDps(): Boolean {
        return false
    }

    override fun isSupport(): Boolean {
        return false
    }

    override fun isPlayable(): Boolean {
        TODO("Not yet implemented")
    }
}