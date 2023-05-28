package cat.petrushkacat.foursquare_data.api

import android.util.Log
import cat.petrushkacat.foursquare_data.models.FoursquareDataEntity
import cat.petrushkacat.foursquare_data.models.FoursquareResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class Api @Inject constructor() {
    private val API_KEY = "fsq3CPeqsuBXdjNZu2hfYlJdKAvb7nNx9Uj/D8eF1NMeufE="
    private val service = createRetrofit(createOkHttpClient()).create<FoursquareApi>()

    suspend fun getPlaces(
        sessionToken: String,
        ll: String = "53.9,27.5",
        radius: Int = 4000,
        limit: Int = 30,
    ): List<FoursquareDataEntity> {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            service.getPlaces(sessionToken = sessionToken, ll = ll, radius = radius, limit = limit)
        }
        return deferred.await().results
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://api.foursquare.com/v3/places/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val client = OkHttpClient
            .Builder()
            .addInterceptor {
                val original = it.request()

                val request = original
                    .newBuilder()
                    .method(original.method, original.body)
                    .header("accept", "application/json")
                    .header("Authorization", API_KEY)
                    .build()

                it.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("http", it)
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return client
    }

}

interface FoursquareApi {

    @GET("search")
    suspend fun getPlaces(
        @Query("ll") ll: String,
        @Query("radius") radius: Int,
        @Query("limit") limit: Int,
        @Query("session_token") sessionToken: String
    ): FoursquareResponse
}