package nz.co.zoyinc.zoyincpoweredcomics

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_issue_inventory.*
import kotlinx.android.synthetic.main.activity_login_successful.*


class LoginSuccessfulActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successful)

        val mAuth = FirebaseAuth.getInstance()
        var stored_userDetails = FirebaseAuth.getInstance().currentUser
        var stored_emailAddress = stored_userDetails!!.email
        val usersEmail_set = findViewById<TextView>(R.id.loginsuccessful_activity_signed_in_email_dsp)

        usersEmail_set.setText(stored_userDetails!!.getEmail())

        val db = Firebase.firestore




        val firestoreInfo1 = db.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {
                    val change_textview_item1 = findViewById(R.id.loginsuccessful_activity_item_1) as TextView
                    change_textview_item1.text = "${documentRetrieve1.getString("product_name")}: ${documentRetrieve1.id} \n" +
                            "Stock Available: ${documentRetrieve1.get("available_stock")} \n" +
                            "Stock Issued: ${documentRetrieve1.get("issued_stock")}"
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "Sorry, there is a problem. Please try again. ", Toast.LENGTH_SHORT).show()
            }


        val firestoreInfo2 = db.collection("comicbook_products").document("ZPC_CB02")
        firestoreInfo2.get()
            .addOnSuccessListener { documentRetrieve2 ->
                if (documentRetrieve2 != null) {
                    val change_textview_item2 = findViewById(R.id.loginsuccessful_activity_item_2) as TextView
                    change_textview_item2.text = "${documentRetrieve2.getString("product_name")}: ${documentRetrieve2.id} \n" +
                            "Stock Available: ${documentRetrieve2.get("available_stock")} \n" +
                            "Stock Issued: ${documentRetrieve2.get("issued_stock")}"
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "Sorry, you have run into a problem. ", Toast.LENGTH_SHORT).show()
            }

        val firestoreInfo3 = db.collection("comicbook_products").document("ZPC_CB03")
        firestoreInfo3.get()
            .addOnSuccessListener { documentRetrieve3 ->
                if (documentRetrieve3 != null) {
                    val change_textview_item3 = findViewById(R.id.loginsuccessful_activity_item_3) as TextView
                    change_textview_item3.text = "${documentRetrieve3.getString("product_name")}: ${documentRetrieve3.id} \n" +
                            "Stock Available: ${documentRetrieve3.get("available_stock")} \n" +
                            "Stock Issued: ${documentRetrieve3.get("issued_stock")}"
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "Sorry, you have run into a problem. ", Toast.LENGTH_SHORT).show()
            }



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
