package cat.petrushkacat.exercises.ui.list

import kotlin.math.absoluteValue
import kotlin.random.Random

data class User(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val sex: Sex,
    val squareAvatarUrl: String,
    val description: String
)

enum class Sex {
    Female,
    Male
}

object UsersGenerator {

    fun generateUsers(): List<User> {
        val users = mutableListOf<User>()
        repeat(30) {
            users.add(generateUser())
        }
        return users
    }

     fun generateUser(): User {
        val firstNames = listOf("James", "Amanda", "Cat", "Josh", "Yennefer", "Peter")
        val lastNames = listOf("Smith", "Peterson", "Cat", "Pan", "Vengerberg", "Parker")
        var description = ""
        repeat(Random.nextInt().absoluteValue % 100) {
            description += " " + words[Random.nextInt().absoluteValue % 57]
        }
        description = description.trim()

        return User(
            firstNames[Random.nextInt().absoluteValue % 6],
            lastNames[Random.nextInt().absoluteValue % 6],
            Random.nextInt().absoluteValue % 99,
            if (Random.nextInt().absoluteValue % 2 == 0) Sex.Female else Sex.Male,
            "https://image.cnbcfm.com/api/v1/image/105773423-1551716977818rtx6p9yw.jpg?v=1551717428&w=700&h=700",
            description
            )
    }

    private val words = listOf(
        "did",
        "became",
        "was",
        "has",
        "have",
        "is",
        "start",
        "yard",
        "alive",
        "grave",
        "sow",
        "meaning",
        "prevalence",
        "emergency",
        "contradiction",
        "silver",
        "buffet",
        "creation",
        "use",
        "password",
        "rice",
        "warrant",
        "will",
        "debut",
        "nun",
        "bush",
        "breast",
        "store",
        "fill",
        "pain",
        "flight",
        "threshold",
        "wolf",
        "tribute",
        "overcharge",
        "circumstance",
        "related",
        "asylum",
        "anniversary",
        "creed",
        "overall",
        "decrease",
        "coincidence",
        "spite",
        "inspiration",
        "default",
        "rescue",
        "dump",
        "recommendation",
        "strip",
        "pitch",
        "reporter",
        "petty",
        "deputy",
        "strain",
        "pool",
        "definition"
    )
}