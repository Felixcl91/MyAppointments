package programacionymas.com.io


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import programacionymas.com.io.response.LoginResponse
import programacionymas.com.model.Appointment
import programacionymas.com.model.Doctor
import programacionymas.com.model.Schedule
import programacionymas.com.model.Specialty
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("specialties")
    fun getSpecialties() : Call<ArrayList<Specialty>>

    @GET("specialties/{specialty}/doctors")
    fun getDoctors(@Path("specialty") specialtyId: Int) : Call<ArrayList<Doctor>>

    @GET("schedule/hours")
    fun getHours(@Query("doctor_id") doctorId: Int, @Query("date") date: String)
            : Call<Schedule>

    @POST("login")
    fun postLogin(@Query("email") email: String, @Query("password") password: String)
            : Call<LoginResponse>

    @POST("logout")
    fun postLogout(@Header("Authorization") authHeader: String) : Call<Void>

    @GET("appointments")
    fun getAppointments(@Header("Authorization") authHeader: String)
            : Call<ArrayList<Appointment>>

    companion object Factory { // object declaration
        private const val BASE_URL = "http://167.172.23.37/api/"

        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        private val okHttpClient = OkHttpClient.Builder().addInterceptor(logger)

        fun create() : ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}