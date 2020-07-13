package programacionymas.com.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_appointments.*
import programacionymas.com.PreferenceHelper
import programacionymas.com.R
import programacionymas.com.io.ApiService
import programacionymas.com.model.Appointment
import programacionymas.com.PreferenceHelper.get
import programacionymas.com.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentsActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private val appointmentAdapter = AppointmentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        loadAppointments()

        /*val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(1, "D1", "12/12/2019", "3:00 PM")
        )
        appointments.add(
            Appointment(2, "D2", "12/12/2019", "4:00 PM")
        )
        appointments.add(
            Appointment(3, "D2", "12/12/2019", "5:00 PM")
        )
        appointments.add(
            Appointment(4, "D4", "12/12/2019", "7:00 PM")
        )*/

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = appointmentAdapter
    }

    private fun loadAppointments() {
        val jwt = preferences["jwt", ""]
        val call = apiService.getAppointments("Bearer $jwt")
        call.enqueue(object: Callback<ArrayList<Appointment>> {
            override fun onFailure(call: Call<ArrayList<Appointment>>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ArrayList<Appointment>>,
                response: Response<ArrayList<Appointment>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        appointmentAdapter.appointments = it
                        // notificamos cambios en data
                        appointmentAdapter.notifyDataSetChanged()
                    }

                }
            }

        })
    }
}