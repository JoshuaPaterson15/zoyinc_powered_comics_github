package nz.co.zoyinc.zoyincpoweredcomics  //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_account_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_restock_inventory.*
import kotlinx.android.synthetic.main.popup_activity_account_details_update_profile.*
import kotlinx.android.synthetic.main.popup_activity_account_details_delete_account.*

//Declearing and extending this class as an AppCompactActivity.//
class AccountDetailsActivity : AppCompatActivity() {

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details) //Setting the content page to activity_account_details for this activity.//

        val user_details = FirebaseAuth.getInstance().currentUser //Collecting details saved using the Firebase Authenticator about the current user and setting this as a variable called user_details.//

        //Setting the following variable as the text value which displays the current users email address.//
        val userEmail_dsp = findViewById<TextView>(R.id.accountdetails_activity_signed_in_email_dsp)
        userEmail_dsp.setText("Registered Email Address: "+user_details!!.getEmail())

        //Setting the following variable as the text value which displays the current users username/UID.//
        val userName_dsp = findViewById<TextView>(R.id.accountdetails_activity_username_dsp)
        userName_dsp.setText("Username/UID:\n"+user_details!!.uid)


        //Once the button named "accountdetails_activity_update_profile_btn" is pressed, the user is shown an AlertDialog/Pop-up display.//
        accountdetails_activity_update_profile_btn.setOnClickListener {
            val alert_builder_1 = AlertDialog.Builder(this) //The variable which is building the alertdialog.//
            val alert_viewer_1 =
                layoutInflater.inflate(R.layout.popup_activity_account_details_update_profile,
                    null)
            alert_builder_1.setView(alert_viewer_1) //Setting the content page/pop-up to alert_viewer_1/popup_activity_account_details_update_profile.//

            //Extra coding for the purposes of a future update: performing positive action
            //alert_builder_1.setPositiveButton("Update Profile") { dialogInterface, which ->
                //val uid: String? = FirebaseAuth.getInstance().uid

                //val profileUpdate = userProfileChangeRequest {
                //displayName = uid
                //}

                //user_details!!.updateProfile(profileUpdate)
                    //.addOnCompleteListener { task ->
                        //if (task.isSuccessful) {
                            //val userName_provided =
                                //findViewById<TextView>(R.id.accountdetails_activity_username_dsp)
                            //userName_provided.setText("Username/UID:\n"+user_details!!.displayName)
                            //Toast.makeText(this,
                                //"Successful, your account has been updated. ",
                                //Toast.LENGTH_SHORT).show()
                            //Log.d(TAG, "User profile updated.")
                        //} else {
                            //Toast.makeText(this,
                                //"Failure, your account has not been updated. ",
                                //Toast.LENGTH_SHORT).show()
                        //}
                    //}
            //}

            //Once the NeutralButton is pressed, the user is informed that the account profile update is cancelled.//
            alert_builder_1.setNeutralButton("Ok") { dialogInterface, which ->
                Toast.makeText(this, "Account Update Cancelled", Toast.LENGTH_LONG).show()
            }
            // Creating the alert dialog and setting relevant properties (e.g. .show() to show the dialog).//
            val alertDialog1: AlertDialog = alert_builder_1.create()
            alertDialog1.setCancelable(false)
            alertDialog1.show()
        }

        //Once the button named "accountdetails_activity_sign_out_btn" is pressed, the system signs the user out of their account and sends the user back to the login page.//
        //This includes notifying the user about the successful outcome and this also includes sending the user back to the MainActivity/Login Page.//
        accountdetails_activity_sign_out_btn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this,
                "Successfully signed out. Thanks, " + user_details.getEmail(),
                Toast.LENGTH_SHORT).show()
            val i = Intent(this@AccountDetailsActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "accountdetails_activity_delete_account_btn" is pressed, the user is shown an AlertDialog/Pop-up display.//
        accountdetails_activity_delete_account_btn.setOnClickListener {

            val builder = AlertDialog.Builder(this) //The variable which is building the alertdialog.//
            val view =
                layoutInflater.inflate(R.layout.popup_activity_account_details_delete_account,
                    null)
            builder.setView(view) //Setting the content page/pop-up to view/popup_activity_account_details_delete_account.//

            //If the user confirms that they want to delete their account (by pressing the PositiveButton), the system begins to delete the account and send the user back to the login page.//
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val user = Firebase.auth.currentUser!! //Collecting details saved using the Firebase Authenticator about the current user and setting this as a variable called user.//
                user.delete() //Command asking firebase to delete the current users account.//

                    //The following actions are completed if the system (Google Firebase) is able to successfully delete the users account.//
                    //This includes notifying the user about the successful outcome and this also includes sending the user back to the MainActivity/Login Page.//
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,
                                "Successful, your account has been deleted. ",
                                Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "User account deleted.")
                            val i = Intent(this@AccountDetailsActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }

                        //If the task is not successful, the system will notify the user that it was not able to delete the users account.//
                        else {
                            Toast.makeText(this,
                                "We're sorry, your account couldn't be deleted. Please try again later.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                    //The system records and notifies the user of an error if the system is unable to successfully delete the users account.//
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                        Toast.makeText(this, "We're sorry, your account couldn't be deleted. Please try again later.", Toast.LENGTH_SHORT).show()
                    }
            }

            //Once the NeutralButton is pressed, the user is informed that the account deletion processis cancelled.//
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
                Toast.makeText(this, "Account Deletion Cancelled", Toast.LENGTH_LONG).show()
            }

            // Creating the alert dialog and setting relevant properties (e.g. .show() to show the dialog).//
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }


        //Once the button "accountdetails_activity_reset_password_btn" has been pressed, the system will send instructions to the specified email address so the user can reset their password.//
        accountdetails_activity_reset_password_btn.setOnClickListener {
            val emailAddress_provided = user_details.getEmail().toString() //Converting the current users email address to a string value and storing the string value as a variable called emailAddress_provided.//

            //Code required to send a password reset email to the user. The system is designed to send a password reset email to the email addressed specified by the user (emailAddress_provided).//
            Firebase.auth.sendPasswordResetEmail(emailAddress_provided)
                //If the system is able to successfully reset the users password, the user is notified that a password reset email has been sent to the specified email address.//
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            "Password Reset Email sent for $emailAddress_provided",
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
                        "We're sorry, this email either is invalid or does not have an account with Zoyinc.",
                        Toast.LENGTH_LONG).show()
                }
        }

        //Extra coding for the purposes of a future update: val user = Firebase.auth.currentUser
        //
        //val profileUpdates = userProfileChangeRequest {
        //    displayName = "Jane Q. User"
        //    photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
        //}
        //
        //user!!.updateProfile(profileUpdates)
        //        .addOnCompleteListener { task ->
        //            if (task.isSuccessful) {
        //                Log.d(TAG, "User profile updated.")
        //            }
        //        }//

        //Once the button named "accountdetails_activity_issue_books_page_btn" is pressed, the user is sent to the IssueInventoryActivity.//
        accountdetails_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "accountdetails_activity_restock_inventory_page_btn" is pressed, the user is sent to the RestockInventoryActivity.//
        accountdetails_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "accountdetails_activity_login_successful_page_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
        accountdetails_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "accountdetails_activity_return_books_page_btn" is pressed, the user is sent to the ReturnInventoryActivity.//
        accountdetails_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "accountdetails_activity_view_account_page_btnv" is pressed, the user is notified that they are already on the AccountDetailsActivity.//
        accountdetails_activity_view_account_page_btnv.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }


    }
}