package programacionymas.com.model

import com.google.gson.annotations.SerializedName

/*
*       "id": 11,
        "description": "Voluptatum laudantium explicabo incidunt ut nisi iste.",
        "scheduled_date": "2020-05-05",
        "type": "Opercaion",
        "created_at": "2020-07-02 10:52:52",
        "status": "Atendida",
        "scheduled_time_12": "11:20 PM",
        "specialty": {
            "id": 2,
            "name": "Pediatría"
        },
        "doctor": {
            "id": 55,
            "name": "Viva Cartwright"
        }*/

data class Appointment (
    val id: Int,
    val description: String,
    val type: String,
    val status: String,

    @SerializedName("scheduled_date")val scheduledDate: String,
    @SerializedName("scheduled_time_12")val scheduledTime: String,
    @SerializedName("created_at") val createdAt: String,

    val specialty: Specialty,
    val doctor: Doctor


)