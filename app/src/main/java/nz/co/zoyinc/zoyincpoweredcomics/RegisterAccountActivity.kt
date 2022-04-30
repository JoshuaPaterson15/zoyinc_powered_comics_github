package nz.co.zoyinc.zoyincpoweredcomics

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


class RegisterAccountActivity : AppCompatActivity() {

    private var Register_Email: EditText? = null
    private var Register_Password: EditText? = null
    private var Register_Username: EditText? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        Register_Email = findViewById<EditText>(R.id.userEmail_txt_input)
        Register_Password = findViewById<EditText>(R.id.userPassword_txt_input)
        Register_Username = findViewById<EditText>(R.id.userName_txt_input)
        mAuth = FirebaseAuth.getInstance()


        register_account_activity_cancel_btn.setOnClickListener {
            val i = Intent(this@RegisterAccountActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        register_account_activity_register_account_btn.setOnClickListener {

            val email = Register_Email!!.text.toString()
            val pass = Register_Password!!.text.toString()
            val uname = Register_Username!!.text.toString()

            if (email != "" && pass != "" && uname !="") {
                mAuth!!.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val uid: String? = FirebaseAuth.getInstance().uid

                            Toast.makeText(this, "Success, you have created an account!" + uid,
                                Toast.LENGTH_SHORT).show()
                            val i = Intent(this@RegisterAccountActivity, MainActivity::class.java)
                            startActivity(i)
                            finish()

                        } else {
                            Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

            }
            else{
                Toast.makeText(this,
                    "Were sorry, please fill out all of the fields.",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}