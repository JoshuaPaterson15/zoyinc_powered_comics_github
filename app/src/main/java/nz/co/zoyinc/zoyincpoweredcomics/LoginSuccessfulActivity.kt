package nz.co.zoyinc.zoyincpoweredcomics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class LoginSuccessfulActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successful)

        val mAuth = FirebaseAuth.getInstance()
        var stored_userDetails = FirebaseAuth.getInstance().currentUser
        var stored_emailAddress = stored_userDetails!!.email
        val usersEmail_set = findViewById<TextView>(R.id.loginsuccessful_activity_user_email_dsp_txt_view)


        usersEmail_set.setText(stored_userDetails!!.getEmail())

        loginsuccessful_activity_login_successful_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        loginsuccessful_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        loginsuccessful_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        loginsuccessful_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        loginsuccessful_activity_view_account_page_btnv.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
