package Miscellaneous

enum class GameConfig(val mapName: String, val difficulty: String, val numEnemies: Int, val timerDurationSeconds: Int) {
    MAP_1("Map 1", "Easy", 10, 180), // 3 minute timer
    MAP_2("Map 2", "Medium", 20, 300), // 5 minutes timer
    MAP_3("Map 3", "Hard", 30, 600); // 10 minutes timer

    override fun toString(): String {
        return "$mapName - $difficulty - $numEnemies enemies - $timerDurationSeconds seconds"
    }
}