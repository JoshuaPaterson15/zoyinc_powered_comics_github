package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.content.ContentValues
import android.content.ContentValues.TAG
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

//Declearing and extending this class as an AppCompactActivity.//
class MainActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null  //Setting and declearing the private variable mAuth.//
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null  //Setting and declearing the private variable mAuthListener.//
    private var etEmailAddress: EditText? = null  //Setting and declearing the private variable etEmailAddress.//
    private var etPassword: EditText? = null  //Setting and declearing the private variable etPassword.//

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //Setting the content page to activity_main' for this activity.//
        etEmailAddress = findViewById<EditText>(R.id.main_activity_login_email_address_et) //Setting the following variable (etEmailAddress) as the text value from main_activity_login_email_address_et.//
        etPassword = findViewById<EditText>(R.id.main_activity_login_password_et) //Setting the following variable (etPassword) as the text value from main_activity_login_password_et.//
        mAuth = FirebaseAuth.getInstance() //Collecting details saved using the Firebase Authenticator about the current user and setting this as a mAuth.//
        val firestore_database = Firebase.firestore  //Setting the firestore_database variable to the Google Firebase/Firestore network.//


        //The following .updates() are to reset the stock levels of each document/comic as required in the student instructions.//
        firestore_database.collection("comicbook_products").document("ZPC_CB01")
            .update("available_stock", 8,"issued_stock",0)
        firestore_database.collection("comicbook_products").document("ZPC_CB02")
            .update("available_stock", 12,"issued_stock",0)
        firestore_database.collection("comicbook_products").document("ZPC_CB03")
            .update("available_stock", 1,"issued_stock",0)


        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser//Collecting details saved using the Firebase Authenticator about the current user and setting this as a variable called user.//
            if (user != null) { //If the system has detected that the user is already signed into the app, the user would inform the user and take the user to the LoginSuccessfulActivity.//
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.uid)
                Toast.makeText(this, "Successfully signed in with: " + user.email, Toast.LENGTH_SHORT).show()

                val exitIntent = Intent(this@MainActivity, LoginSuccessfulActivity::class.java)
                startActivity(exitIntent)
                finish()

            } else { //If the system has detected that the user has signed out or if the user isn't logged in, the system would record that the user is signed out of the application.//
                Log.d(TAG, "onAuthStateChanged:signed_out")
            }
        }

        //Once the user has pressed the "main_activity_login_btn" button, the system converts the provided email address and passwords to string values attempting to sign the user in to the application.//
        main_activity_login_btn!!.setOnClickListener {
            val val_etEmailAddress = etEmailAddress!!.text.toString() //Converting the text to a string value.//
            val val_etPassword = etPassword!!.text.toString() //Converting the text to a string value.//

            //The following steps are completed if the user has provided a value in the email address and password text fields.//
            if (val_etEmailAddress != "" && val_etPassword != "") {
                mAuth!!.signInWithEmailAndPassword(val_etEmailAddress, val_etPassword) //The user is signed into the system (refer to the other mAuth references in this activity).

                    //The system records and notifies the user of an error and that the system was not able to log the user into their account.//
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "sign-in failed with ", exception)
                        Toast.makeText(this,
                            "We're sorry, you have used incorrect credentials. Please try again or try resetting your password.",
                            Toast.LENGTH_LONG).show()

                    }
            }else { //If the system has detected that the user is missing a field/hasn't put in an email address or password, the user is notified of this error and is asked to sign in again.//
                    Toast.makeText(this, "We're Sorry, you must enter an email address and password before you can sign-in.", Toast.LENGTH_SHORT).show()
            }
        }

        //If the user presses the "main_activity_reset_password_btn" button, the user is taken to the ResetPasswordActivity.//
        main_activity_reset_password_btn.setOnClickListener {
            val exitIntent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
            startActivity(exitIntent)
            finish()
        }

        //If the user presses the "main_activity_register_btn" button, the user is taken to the RegisterAccountActivity.//
        main_activity_register_btn.setOnClickListener {
            val exitIntent = Intent(this@MainActivity, RegisterAccountActivity::class.java)
            startActivity(exitIntent)
            finish()
        }
    }

    //The system begins the following actions/processes when the activity is opened by the end-user.//
    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)

        //The step is to verify and notify the user if they are connected to the internet.//
        if (internetConnectionChecker(this)) {
            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show()
        } else { //If the user is not connected to the internet, the user is immediately signed out and is informed that they aren't connected to the internet.
            Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
            Firebase.auth.signOut()

            //If the user presses the main_activity_login_btn while they are not connected to the internet (after checking for internet again), the user is notified that they can only sign in when they have internet.//
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
                }
            }

            //If the user presses the main_activity_reset_password_btn while they are not connected to the internet (after checking for internet again), the user is notified that they can only reset their password when they have internet.//
            main_activity_reset_password_btn.setOnClickListener {
                if (internetConnectionChecker(this)) {
                    val exitIntent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
                    startActivity(exitIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                }
            }

            //If the user presses the main_activity_register_btn while they are not connected to the internet (after checking for internet again), the user is notified that they can only create an account when they have internet.//
            main_activity_register_btn.setOnClickListener {
                if (internetConnectionChecker(this)) {
                    val exitIntent = Intent(this@MainActivity, RegisterAccountActivity::class.java)
                    startActivity(exitIntent)
                    finish()
                } else {
                    Toast.makeText(this, "Disconnected - No Internet", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    //The system removes the AuthStateListener when the activity is in the background (e.g. when the user is receiving a phone call).//
    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    //Declaration of the variable TAG for this activity.//
    companion object {
        private const val TAG = "MainActivity"
    }

    //The following coding checks if the device and the application have a active internet connection when it is called for by buttons when the user is not connected to the internet.//
    private fun internetConnectionChecker(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false //Checking for any active connection.//
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false //Checking for an active network connection.//

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true //Checking for an active wifi connection.//
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true //Checking for an active CELLULAR connection.//
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
