package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register_account.*

//Declearing and extending this class as an AppCompactActivity.//
class RegisterAccountActivity : AppCompatActivity() {

    private var Register_Email: EditText? = null//Setting and declearing the private variable Register_Email.//
    private var Register_Password: EditText? = null//Setting and declearing the private variable Register_Password.//
    private var Register_Username: EditText? = null//Setting and declearing the private variable Register_Username.//
    private var mAuth: FirebaseAuth? = null//Setting and declearing the private variable mAuth.//

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account) //Setting the content page to activity_register_account for this activity.//

        Register_Email = findViewById<EditText>(R.id.userEmail_txt_input) //Setting the following variable (Register_Email) as the text variable for the response from userEmail_txt_input.//
        Register_Password = findViewById<EditText>(R.id.userPassword_txt_input) //Setting the following variable (Register_Password) as the text variable for the response from userPassword_txt_input.//
        Register_Username = findViewById<EditText>(R.id.userName_txt_input) //Setting the following variable (Register_Username) as the text variable for the response from userName_txt_input.//
        mAuth = FirebaseAuth.getInstance() //Setting the mAuth variable to the Google Firebase/Firestore network.//

        //Once the user presses the button called "register_account_activity_cancel_btn", the user is sent back to the MainActivity.//
        register_account_activity_cancel_btn.setOnClickListener {
            val i = Intent(this@RegisterAccountActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        //If the user presses the button called "register_account_activity_register_account_btn", the system will begin to register this user onto the database (hosted by Google Firebase).
        register_account_activity_register_account_btn.setOnClickListener {

            //Text conversions to a string value (this is more of a safety measure).//
            val email = Register_Email!!.text.toString()
            val pass = Register_Password!!.text.toString()
            val uname = Register_Username!!.text.toString()

            //The system will only proceed to the next step if the user has entered into the email, password and username fields.//
            if (email != "" && pass != "" && uname !="") {

                //Code required to register a user using the provided email address and password.//
                mAuth!!.createUserWithEmailAndPassword(email, pass)
                    //If the system was able to successfully create an account, the user would be notified about the successful outcome as they are taken back to the login page.//
                    //Additioanlly, the user would also be displayed their UID (Unique identifier).//
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val uid: String? = FirebaseAuth.getInstance().uid //Getting the users UID from the Firebase Authentication system.//

                            Toast.makeText(this, "Success, you have created an account!" + uid,
                                Toast.LENGTH_SHORT).show()
                            val i = Intent(this@RegisterAccountActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()

                        } else { //If the system was not successful, the user would be notified that their account couldn't be registered.//
                            Toast.makeText(this,
                                "We're sorry, your account couldn't be registered. Please try again or contact support.",
                                Toast.LENGTH_LONG).show()
                        }
                    }
                    //The system records and notifies the user of an error and that the system was not able to successfully register the user.//
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "error, failed with ", exception)
                        Toast.makeText(this,
                            "We're sorry, your account couldn't be registered. Please try again or contact support.",
                            Toast.LENGTH_LONG).show()
                    }

            }
            else{ // //The user will be notified that they have entered in nothing into the appropriate missing field/s and they cannot proceed onto the next step unless they insert a response into all of the rquired fields.//
                Toast.makeText(this,
                    "We're sorry, you must fill out all of the fields before an account can be created. Please try again.",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}