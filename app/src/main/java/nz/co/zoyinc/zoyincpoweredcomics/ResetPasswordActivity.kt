package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reset_password.*

//Declearing and extending this class as an AppCompactActivity.//
class ResetPasswordActivity : AppCompatActivity() {

    private var reset_email_provided: EditText? = null  //Setting and declearing the private variable reset_email_provided.//

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password) //Setting the content page to activity_reset_password for this activity.//
        reset_email_provided = findViewById<EditText>(R.id.popup_reset_password_email_address) //Setting the following variable (reset_email_provided) as the text value from popup_reset_password_email_address.//

        //Once the button "reset_password_activity_reset_password_popup_btn" has been pressed, the system will send instructions to the specified email address so the user can reset their password.//
        reset_password_activity_reset_password_popup_btn.setOnClickListener {
            val reset_password_email = reset_email_provided!!.text.toString()

            //Code required to send a password reset email to the user. The system is designed to send a password reset email to the email addressed specified by the user (emailAddress_provided).//
            Firebase.auth.sendPasswordResetEmail(reset_password_email)
                //If the system is able to successfully reset the users password, the user is notified that a password reset email has been sent to the specified email address.//
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            "Password Reset Email sent for $reset_password_email",
                            Toast.LENGTH_LONG).show()

                        //If the task is not successful, the system will notify the user that it was not able to send an email address.//
                    } else {
                        Toast.makeText(this,
                            "We're sorry, we wern't able to send a email to reset your password. Please try again.",
                            Toast.LENGTH_LONG).show()
                    }
                }
                //The system records and notifies the user of an error and that the system was not able to send a password reset email.//
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "email failed with ", exception)
                    Toast.makeText(this,
                        "We're sorry, this email either is invalid or there is a problem. Please try again or contact support.",
                        Toast.LENGTH_LONG).show()
                }
        }

        //Once the button named "reset_password_activity_cancel_btn" is pressed, the user is sent to the MainActivity.//
        reset_password_activity_cancel_btn.setOnClickListener {
            val i = Intent(this@ResetPasswordActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

    }


}