package cat.petrushkacat.exercises.patterns

class UserManager {
    private val cache: CacheManager = CacheManager()
    private val api: ApiManager = ApiManager

    fun getUser(): String {
        return if(cache.data == null) {
            val data = ApiManager.data
            cache.data = data
            data
        } else {
            cache.data!!
        }
    }
}

class CacheManager {
    var data: String? = null
}

object ApiManager {
    val data = "user1"
}