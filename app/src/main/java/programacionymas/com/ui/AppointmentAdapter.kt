package programacionymas.com.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_appointment.view.*
import programacionymas.com.R
import programacionymas.com.model.Appointment

class AppointmentAdapter
    : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    var appointments = ArrayList<Appointment>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(appointment: Appointment) {
            with (itemView) {
                tvAppointmentId.text = context.getString(R.string.item_appointment_id, appointment.id)
                tvDoctorName.text = appointment.doctor.name
                tvScheduledDate.text = context.getString(R.string.item_appointment_date, appointment.scheduledDate)
                tvScheduledTime.text = context.getString(R.string.item_appointment_time, appointment.scheduledTime)
            }

        }
    }

    // inflate xml items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_appointment,
                parent,
                false
            )
        )
    }

    // enlazar BIND data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.bind(appointment)
    }

    // Returns of elements
    override fun getItemCount() = appointments.size
}