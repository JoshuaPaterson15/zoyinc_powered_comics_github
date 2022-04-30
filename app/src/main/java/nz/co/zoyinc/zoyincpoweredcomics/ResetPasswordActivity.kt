package nz.co.zoyinc.zoyincpoweredcomics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    private var reset_email_provided: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        reset_email_provided = findViewById<EditText>(R.id.popup_reset_password_email_address)


        reset_password_activity_reset_password_popup_btn.setOnClickListener {
            val reset_password_email = reset_email_provided!!.text.toString()

            Firebase.auth.sendPasswordResetEmail(reset_password_email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,
                            "Password Reset Email sent for $reset_password_email",
                            Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this,
                            "Were sorry, this email either is invalid or does not have an account with Zoyinc Inventory",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }

        reset_password_activity_cancel_btn.setOnClickListener {
            val i = Intent(this@ResetPasswordActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

    }


}