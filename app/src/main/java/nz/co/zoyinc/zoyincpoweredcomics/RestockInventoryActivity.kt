package nz.co.zoyinc.zoyincpoweredcomics

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_restock_inventory.*


class RestockInventoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restock_inventory)
        val firestore_val = Firebase.firestore


        val firestoreInfo1 = firestore_val.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {
                    val product_name =
                        findViewById(R.id.restockinventory_activity_item_1) as TextView
                    product_name.text ="${documentRetrieve1.id}: ${documentRetrieve1.getString("product_name")} \nAvailable Stock: ${documentRetrieve1.get("stock")}"

                    restockinventory_activity_item_1_issue_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryActivity,
                            RestockInventoryChangeRq0::class.java)
                        i.putExtra("issue_status", "Restock")
                        i.putExtra("item_document_num", "${documentRetrieve1.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        val firestoreInfo2 = firestore_val.collection("comicbook_products").document("ZPC_CB02")
        firestoreInfo2.get()
            .addOnSuccessListener { documentRetrieve2 ->
                if (documentRetrieve2 != null) {
                    val product_name = findViewById<TextView>(R.id.restockinventory_activity_item_2)
                    product_name.text ="${documentRetrieve2.id}: ${documentRetrieve2.getString("product_name")} \nAvailable Stock: ${documentRetrieve2.get("stock")}"

                    restockinventory_activity_item_2_issue_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryActivity,
                            RestockInventoryChangeRq0::class.java)
                        i.putExtra("issue_status", "Restock")
                        i.putExtra("item_document_num", "${documentRetrieve2.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        val firestoreInfo3 = firestore_val.collection("comicbook_products").document("ZPC_CB03")
        firestoreInfo3.get()
            .addOnSuccessListener { documentRetrieve3 ->
                if (documentRetrieve3 != null) {
                    val product_name = findViewById<TextView>(R.id.restockinventory_activity_item_3)
                    product_name.text ="${documentRetrieve3.id}: ${documentRetrieve3.getString("product_name")} \nAvailable Stock: ${documentRetrieve3.get("stock")}"

                    restockinventory_activity_item_3_issue_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryActivity,
                            RestockInventoryChangeRq0::class.java)
                        i.putExtra("issue_status", "Restock")
                        i.putExtra("item_document_num", "${documentRetrieve3.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


        restockinventory_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }

        restockinventory_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        restockinventory_activity_restock_inventory_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        restockinventory_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        restockinventory_activity_view_account_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }


    }


}