import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job_sec_app.ApplicationRequest
import com.example.job_sec_app.Internship
import retrofit2.Retrofit
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
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
                    // Refresh the list after update0
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
    fun searchInternships(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getInternship(query)
                _internships.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
    val selectedInternship = MutableLiveData<Internship>()

    fun getInternshipById(id: String) {
        viewModelScope.launch {
            try {
                val internship = RetrofitInstance.api.getInternshipById(id)
                selectedInternship.postValue(internship)
            } catch (e: Exception) {
                _error.postValue("Failed to load internship")
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
    @POST("api/applications")
    suspend fun submitApplication(
        @Body application: ApplicationRequest,
        name: RequestBody,
        email: RequestBody,
        phone: RequestBody,
        location: RequestBody,
        skills: RequestBody,
        interest: RequestBody
    ): Response<Unit>

    @GET("api/internships/{id}")
    suspend fun getInternshipById(@Path("id") id: String): Internship

    @GET("api/internships")
    suspend fun getInternships(): List<Internship>

    @GET("api/internships")
    suspend fun getInternship(@Query("search") search: String? = null): List<Internship>

    @POST("api/internships")
    suspend fun createInternship(@Body internship: Internship): Internship

    @PUT("api/internships/{id}")
    suspend fun updateInternship(
        @Path("id") id: String,
        @Body internship: Internship
    ): Internship

    fun submitApplication(
        name: okhttp3.RequestBody,
        email: okhttp3.RequestBody,
        phone: okhttp3.RequestBody,
        location: okhttp3.RequestBody,
        skills: okhttp3.RequestBody,
        interest: okhttp3.RequestBody,
        resume: okhttp3.MultipartBody.Part
    )
}

