package nz.co.zoyinc.zoyincpoweredcomics

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var etEmailAddress: EditText? = null
    private var etPassword: EditText? = null
    private var btnSignIn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etEmailAddress = findViewById<EditText>(R.id.main_activity_login_email_address_et)
        etPassword = findViewById<EditText>(R.id.main_activity_login_password_et)
        btnSignIn = findViewById<Button>(R.id.main_activity_login_signin_btn)
        mAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        db.collection("inventory").document("ZPC_CB01")
            .update("available_stock", 8,"issued_stock",0)


        db.collection("inventory").document("ZPC_CB02")
            .update("available_stock", 12,"issued_stock",0)

        db.collection("inventory").document("ZPC_CB03")
            .update("available_stock", 3,"issued_stock",0)


        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {

                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
                toastMessage("Successfully signed in with: " + user.email)

                val i = Intent(this@MainActivity, LoginSuccessfulActivity::class.java)
                startActivity(i)
                finish()

            } else {
                // User is signed out
                Log.d(TAG, "onAuthStateChanged:signed_out")
            }
        }

        main_activity_login_button!!.setOnClickListener {
            val email = mEmail!!.text.toString()
            val pass = mPassword!!.text.toString()
            if (email != "" && pass != "") {
                mAuth!!.signInWithEmailAndPassword(email, pass)
                    .addOnFailureListener{
                        toastMessage("Incorrect Credentials, please try again.")
                    }

            } else {
                toastMessage("You didn't fill in all the fields.")
            }
        }

        main_activity_reset_password_main_btn.setOnClickListener {
            val i = Intent(this@MainActivity, ResetPasswordActivity::class.java)
            startActivity(i)
            finish()
        }

        main_activity_register_button.setOnClickListener {
            val i = Intent(this@MainActivity, RegisterAccountActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)

        if (checkForInternet(this)) {
            Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()
            main_activity_login_button!!.setOnClickListener {
                toastMessage("Sorry, you can't sign in. Try again when you have internet.")
            }

            main_activity_login_button!!.setOnClickListener {

                if (checkForInternet(this)) {
                    val email = etEmailAddress!!.text.toString()
                    val pass = etPassword!!.text.toString()
                    if (email != "" && pass != "") {
                        mAuth!!.signInWithEmailAndPassword(email, pass)
                            .addOnFailureListener{
                                toastMessage("Incorrect Credentials, please try again.")
                            }

                    } else {
                        toastMessage("You didn't fill in all the fields.")
                    }

                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()

                }

            }


            main_activity_reset_password_main_btn.setOnClickListener {
                if (checkForInternet(this)) {
                    val i = Intent(this@MainActivity, ResetPasswordActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()

                }
            }

            main_activity_register_button.setOnClickListener {
                if (checkForInternet(this)) {
                    val i = Intent(this@MainActivity, RegisterAccountActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                    val i = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    private fun toastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
