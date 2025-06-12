import com.example.job_sec_app.Internship
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

// ApiService.kt
interface ApiService {
    @GET("internships")
    suspend fun getInternships(): List<Internship>

    @POST("internships")
    suspend fun addInternship(@Body internship: Internship): Internship

    @PUT("api/internships/{id}")
    suspend fun updateInternship(
        @Path("id") id: String,
        @Body internship: Internship
    ): Internship
}

// ApiClient.kt
object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:5000/api/" // For emulator

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}