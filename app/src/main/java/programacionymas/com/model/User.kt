package programacionymas.com.model

/*
* "id": 1,
        "name": "Felix Cruz",
        "email": "felix@hotmail.com",
        "dni": null,
        "address": null,
        "phone": null,
        "role": "admin"
* */

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val dni: String,
    val address: String,
    val phone: String,
    val role: String
                )