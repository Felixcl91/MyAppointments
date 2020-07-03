package programacionymas.com.io

import programacionymas.com.model.Specialty
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {

    @GET("specialties")
    abstract fun getSpecialties() : Call<ArrayList<Specialty>>

    /*
    * Objeto llamado Factory static para llamar a create
    * */
    companion object Factory { // object declaration
        private const val BASE_URL = "http://167.172.23.37/api/"
        /*
        * metodo que devuelve un apiService
        * construimos una instancia retrofit
        * le pasamos la url
        * lo convertimos gson, json -> objeto
        * por ultimo le pedimos a retrofit que nos permita consultar la apiService "retrofit.create(interface)"
        * */
        fun create() : ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}