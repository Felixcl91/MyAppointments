package programacionymas.com.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_appointment.*
import programacionymas.com.R
import programacionymas.com.io.ApiService
import programacionymas.com.model.Specialty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

import kotlin.collections.ArrayList

class CreateAppointmentActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    private var selectedCalendar = Calendar.getInstance()
    private var selectedTimeRadioButton: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

        btnNext.setOnClickListener {
            if (etDescription.text.toString().length < 3) {
                etDescription.error = getString(R.string.validate_appointment_description)
            } else {
                //continuamos con step2
                cvStep1.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
        }

        btnNext2.setOnClickListener {
            if (etScheduledDate.text.toString().isEmpty()) {
                etScheduledDate.error = getString(R.string.validate_appointment_date)
            } else if (selectedTimeRadioButton == null) {
                Snackbar.make(
                    createAppointmentLinearLayout,
                    R.string.validate_appointment_time, Snackbar.LENGTH_SHORT
                ).show()
            } else {
                //continuamos con step3
                showAppointmentDataToConfirm()
                cvStep2.visibility = View.GONE
                cvStep3.visibility = View.VISIBLE
            }
        }

        btnConfirm.setOnClickListener {
            Toast.makeText(this, "Cita registrada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }

        loadSpecialties()

        val doctorOptions = arrayOf("Doctor A", "Doctor B", "Doctor C")
        spinnerDoctors.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doctorOptions)
    }

    private fun loadSpecialties() {
        val call = apiService.getSpecialties()
        call.enqueue(object : Callback<ArrayList<Specialty>> { // object expression
            override fun onFailure(call: Call<ArrayList<Specialty>>, t: Throwable) {
                Toast.makeText(
                    this@CreateAppointmentActivity,
                    getString(R.string.error_loading_specialty),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }

            override fun onResponse(
                call: Call<ArrayList<Specialty>>,
                response: Response<ArrayList<Specialty>>
            ) {
                if (response.isSuccessful) { //200...300
                    val specialties = response.body() //arraylist de especialidades objetos
                    val specialtyOptions = ArrayList<String>() //arraylist de cadenas

                    specialties?.forEach {   // iteramos sobre cada especialidad para sacar el name y guardarlo en specialtyOptions
                        specialtyOptions.add(it.name)
                    }
                    spinnerSpecialties.adapter = ArrayAdapter<String>(
                        this@CreateAppointmentActivity,
                        android.R.layout.simple_list_item_1,
                        specialtyOptions
                    )
                }
            }

        })
    }

    private fun showAppointmentDataToConfirm() {
        tvConfirmDescription.text = etDescription.text.toString()
        tvConfirmSpecialty.text = spinnerSpecialties.selectedItem.toString()

        val selectedRadioButtonId = radioGroupType.checkedRadioButtonId
        val selectedRadioType = radioGroupType.findViewById<RadioButton>(selectedRadioButtonId)
        tvConfirmType.text = selectedRadioType.text.toString()

        tvConfirmDate.text = spinnerDoctors.selectedItem.toString()
        tvConfirmDoctor.text = etScheduledDate.text.toString()
        tvConfirmTime.text = selectedTimeRadioButton?.text.toString()


    }

    @SuppressLint("StringFormatMatches")
    fun onclickScheduledDate(v: View?) {
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        //escuchador
        val listener = DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            //Toast.makeText(this, "$y-$m-$d", Toast.LENGTH_SHORT).show()
            //mantenemos el dia seleccionado
            selectedCalendar.set(y, m, d)
            etScheduledDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    m.twoDigits(),
                    d.twoDigits()
                )
            )
            //tras seleccionar una fecha llamammos a displayRadioButtons va a mostrar despues de consultar al servidor
            etScheduledDate.error = null
            displayRadioButtons()
        }

        //new dialog
        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)
        val datePicker = datePickerDialog.datePicker

        //establecemos limites
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis // +1
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis // +30

        // show dialog
        datePickerDialog.show()
    }

    private fun displayRadioButtons() {
        //limpiamos la vista
        //      radioGroup.clearCheck()
        //      radioGroup.removeAllViews()
        //      radioGroup.checkedRadioButtonId
        selectedTimeRadioButton = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        //desmarque de radios


        val hours = arrayOf("3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM")
        var goToLeft = true

        //para cada hora
        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it

            radioButton.setOnClickListener { view ->
                selectedTimeRadioButton?.isChecked = false
                selectedTimeRadioButton = view as RadioButton?
                selectedTimeRadioButton?.isChecked = true
            }

            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }

    }

    private fun Int.twoDigits() = if (this >= 10) this.toString() else "0$this"

    override fun onBackPressed() {
        when {
            cvStep3.visibility == View.VISIBLE -> {
                cvStep3.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE
            }
            cvStep2.visibility == View.VISIBLE -> {
                cvStep2.visibility = View.GONE
                cvStep1.visibility = View.VISIBLE
            }
            cvStep1.visibility == View.VISIBLE -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
                builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
                builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_positive_btn)) { _, _ ->
                    finish()
                }
                builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative_btn)) { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}