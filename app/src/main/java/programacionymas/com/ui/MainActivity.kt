package programacionymas.com.ui

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

class MainActivity : AppCompatActivity() {

    //con lazy podemos inizializar una variable de manera perezosa y entonces cuando se requiera se inicializa y no al ejecutar
    private val snackBar by lazy {
        Snackbar.make(mainLayout,
            R.string.press_back_again, Snackbar.LENGTH_SHORT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // shared preferences
        // splite
        // files
        /*
        val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val session = preferences.getBoolean("session", false)*/
        val preferences =
            PreferenceHelper.defaultPrefs(this)
        if (preferences["session", false])
            goToMenuActivity()

        btnLogin.setOnClickListener {
            // validate
            createSessionPreference()
            goToMenuActivity()
        }

        tvGoToRegister.setOnClickListener {
            Toast.makeText(this, getString(R.string.please_fill_your_data), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createSessionPreference() {
        /*val preferences = getSharedPreferences("general", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("session", true)
        editor.apply()*/
        val preferences =
            PreferenceHelper.defaultPrefs(this)
        preferences["session"] = true
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