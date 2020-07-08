package programacionymas.com.io.response

import programacionymas.com.model.User

data class LoginResponse(val success: Boolean, val user: User, val jwt: String)