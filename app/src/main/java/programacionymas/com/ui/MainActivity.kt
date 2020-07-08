package programacionymas.com.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import programacionymas.com.PreferenceHelper
import programacionymas.com.PreferenceHelper.get
import programacionymas.com.PreferenceHelper.set
import programacionymas.com.R
import programacionymas.com.io.ApiService
import programacionymas.com.io.response.LoginResponse
import programacionymas.com.util.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService by lazy {
        ApiService.create()
    }

    //con lazy podemos inizializar una variable de manera perezosa y entonces cuando se requiera se inicializa y no al ejecutar
    private val snackBar by lazy {
        Snackbar.make(mainLayout,
            R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // shared preferences
        // SQLite
        // files

        /*val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val session = preferences.getBoolean("session", false)
        val preferences =
            PreferenceHelper.defaultPrefs(this)
        if (preferences["session", ""].contains("."))
            goToMenuActivity()*/

        btnLogin.setOnClickListener {
            // validate
            createSessionPreference()
            goToMenuActivity()
            //performLogin()

        }

        tvGoToRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.please_fill_your_data), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    /*private fun performLogin() {
        val call = apiService.postLogin(etEmail.text.toString(), etPassword.text.toString())
        call.enqueue(object: Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        toast(getString(R.string.error_login_response))
                        return
                    }
                    if (loginResponse.success) {
                        loginResponse.jwt
                        goToMenuActivity()
                    } else {
                        toast(getString(R.string.error_invalid_credentials))
                    }

                } else {
                    toast(getString(R.string.error_login_response))
                }
            }

        })
    }*/

    private fun createSessionPreference() {
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", true)
        editor.apply()
        /*val preferences =
            PreferenceHelper.defaultPrefs(this)
        preferences["session"] = false*/
    }

    private fun goToMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        //if snackbar se muestra
        if (snackBar.isShown)
            super.onBackPressed()
        else
            snackBar.show()

    }
}