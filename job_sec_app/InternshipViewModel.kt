import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.job_sec_app.Internship
import retrofit2.Retrofit
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.security.Provider.Service
import kotlin.getValue



class InternshipViewModel : ViewModel() {
    val _internships = MutableLiveData<List<Internship>>()
    val internships: LiveData<List<Internship>> = _internships

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchInternships() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getInternships()
                _internships.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
    fun updateInternship(internship: Internship) {
        viewModelScope.launch {
            try {
                // Ensure the internship has an ID
                internship.title?.let { id ->
                    val response = RetrofitInstance.api.updateInternship(id, internship)
                    // Refresh the list after update
                    fetchInternships()
                } ?: run {
                    _error.postValue("Internship ID is missing")
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
    fun createInternship(internship: Internship) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.createInternship(internship)
                // Refresh list after creation
                fetchInternships()
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:5000/" // For Android emulator

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: InternshipApiService by lazy {
        retrofit.create(InternshipApiService::class.java)
    }
}

interface InternshipApiService {
    @GET("api/internships")
    suspend fun getInternships(): List<Internship>

    @POST("api/internships")
    suspend fun createInternship(@Body internship: Internship): Internship
    @PUT("api/internships/{id}")
    suspend fun updateInternship(
        @Path("id") id: String,
        @Body internship: Internship
    ): Internship
}