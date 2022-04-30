package nz.co.zoyinc.zoyincpoweredcomics

import android.app.AlertDialog
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

class AccountDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_details)

        val user_details = FirebaseAuth.getInstance().currentUser
        val userEmail_dsp = findViewById<TextView>(R.id.accountdetails_activity_signed_in_email_dsp)
        userEmail_dsp.setText("Registered Email Address: "+user_details!!.getEmail())
        val userName_dsp = findViewById<TextView>(R.id.accountdetails_activity_username_dsp)
        userName_dsp.setText("Username/UID:\n"+user_details!!.displayName)


        accountdetails_activity_update_profile_btn.setOnClickListener {
            val alert_builder_1 = AlertDialog.Builder(this)
            val alert_viewer_1 =
                layoutInflater.inflate(R.layout.popup_activity_account_details_update_profile,
                    null)
            alert_builder_1.setView(alert_viewer_1)

            //performing positive action
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

            //performing cancel action
            alert_builder_1.setNeutralButton("Ok") { dialogInterface, which ->
                Toast.makeText(this, "Account Update Cancelled", Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog1: AlertDialog = alert_builder_1.create()
            // Set other dialog properties
            alertDialog1.setCancelable(false)
            alertDialog1.show()
        }

        accountdetails_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        accountdetails_activity_sign_out_btn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this,
                "Successfully signed out. Thanks, " + user_details.getEmail(),
                Toast.LENGTH_SHORT).show()
            val i = Intent(this@AccountDetailsActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        accountdetails_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        accountdetails_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }


        accountdetails_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@AccountDetailsActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        accountdetails_activity_delete_account_btn.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            val view =
                layoutInflater.inflate(R.layout.popup_activity_account_details_delete_account,
                    null)
            builder.setView(view)

            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                val user = Firebase.auth.currentUser!!

                user.delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,
                                "Successful, your account has been deleted. ",
                                Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "User account deleted.")
                            val i = Intent(this@AccountDetailsActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this,
                                "Unsuccessful, your account couldn't be deleted. ",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            //performing cancel action
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
                Toast.makeText(this, "Account Deletion Cancelled", Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }


        accountdetails_activity_reset_password_btn.setOnClickListener {

            val emailAddress_provided = user_details.getEmail().toString()

            Firebase.auth.sendPasswordResetEmail(emailAddress_provided)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            "Password Reset Email sent for $emailAddress_provided",
                            Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this,
                            "Were sorry, this email either is invalid or does not have an account with Zoyinc.",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }

        //Extra: val user = Firebase.auth.currentUser
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


        accountdetails_activity_view_account_page_btnv.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }


    }
}