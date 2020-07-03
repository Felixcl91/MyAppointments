package programacionymas.com.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_appointments.*
import programacionymas.com.R
import programacionymas.com.model.Appointment

class AppointmentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()
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
        )

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter =
            AppointmentAdapter(appointments)
    }
}