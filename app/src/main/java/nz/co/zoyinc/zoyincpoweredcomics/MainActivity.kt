package nz.co.zoyinc.zoyincpoweredcomics

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var etEmailAddress: EditText? = null
    private var etPassword: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etEmailAddress = findViewById<EditText>(R.id.main_activity_login_email_address_et)
        etPassword = findViewById<EditText>(R.id.main_activity_login_password_et)

        mAuth = FirebaseAuth.getInstance()

        val firestore_database = Firebase.firestore

        firestore_database.collection("comicbook_products").document("ZPC_CB01")
            .update("available_stock", 8,"issued_stock",0)

        firestore_database.collection("comicbook_products").document("ZPC_CB02")
            .update("available_stock", 12,"issued_stock",0)

        firestore_database.collection("comicbook_products").document("ZPC_CB03")
            .update("available_stock", 1,"issued_stock",0)

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {

                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
                Toast.makeText(this, "Successfully signed in with: " + user.email, Toast.LENGTH_SHORT).show()

                val exitIntent = Intent(this@MainActivity, LoginSuccessfulActivity::class.java)
                startActivity(exitIntent)
                finish()

            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out")
            }
        }

        main_activity_login_btn!!.setOnClickListener {
            val val_etEmailAddress = etEmailAddress!!.text.toString()
            val val_etPassword = etPassword!!.text.toString()
            if (val_etEmailAddress != "" && val_etPassword != "") {
                mAuth!!.signInWithEmailAndPassword(val_etEmailAddress, val_etPassword)
                    .addOnFailureListener{
                        Toast.makeText(this, "Incorrect credentials, please try again.", Toast.LENGTH_SHORT).show()
                    }

            } else {
                    Toast.makeText(this, "Sorry, you can't sign in. Try again when you have internet.", Toast.LENGTH_SHORT).show()
            }
        }

        main_activity_reset_password_btn.setOnClickListener {
            val exitIntent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
            startActivity(exitIntent)
            finish()
        }

        main_activity_register_btn.setOnClickListener {
            val exitIntent = Intent(this@MainActivity, RegisterAccountActivity::class.java)
            startActivity(exitIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)

        if (internetConnectionChecker(this)) {
            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()
            main_activity_login_btn!!.setOnClickListener {
                Toast.makeText(this, "Sorry, you can't sign in. Try again when you have internet.", Toast.LENGTH_SHORT).show()
            }

            main_activity_login_btn!!.setOnClickListener {
                if (internetConnectionChecker(this)) {
                    val email = etEmailAddress!!.text.toString()
                    val pass = etPassword!!.text.toString()
                    if (email != "" && pass != "") {
                        mAuth!!.signInWithEmailAndPassword(email, pass)
                            .addOnFailureListener{
                                Toast.makeText(this, "Incorrect Credentials, please try again.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "You didn't fill in all the fields.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                    val exitIntent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(exitIntent)
                    finish()
                }
            }

            main_activity_reset_password_btn.setOnClickListener {
                if (internetConnectionChecker(this)) {
                    val exitIntent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
                    startActivity(exitIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                    val exitIntent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(exitIntent)
                    finish()
                }
            }

            main_activity_register_btn.setOnClickListener {
                if (internetConnectionChecker(this)) {
                    val exitIntent = Intent(this@MainActivity, RegisterAccountActivity::class.java)
                    startActivity(exitIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                    val exitIntent = Intent(this@MainActivity, MainActivity::class.java)
                    startActivity(exitIntent)
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

    companion object {
        private const val TAG = "MainActivity"
    }

    private fun internetConnectionChecker(context: Context): Boolean {
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
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
