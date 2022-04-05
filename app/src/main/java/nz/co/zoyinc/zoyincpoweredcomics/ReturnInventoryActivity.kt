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
import kotlinx.android.synthetic.main.activity_issue_inventory.*
import kotlinx.android.synthetic.main.activity_return_inventory.*

class ReturnInventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_inventory)
        val firestore_val = Firebase.firestore

        val firestoreInfo1 = firestore_val.collection("comicbook_products").document("ZPC_CB01 ")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {
                    val change_textview_item1 =
                        findViewById(R.id.returninventory_activity_item_1) as TextView
                    change_textview_item1.text ="${documentRetrieve1.id}: ${documentRetrieve1.getString("product_name")} \nAvailable Stock: ${documentRetrieve1.get("available_stock")}"

                    returninventory_activity_item_1_issue_btn.setOnClickListener {
                        val i = Intent(this@ReturnInventoryActivity,
                            InventoryStatusChangeRqActivity::class.java)
                        i.putExtra("issue_status", "Return")
                        i.putExtra("item_part_num", "${documentRetrieve1.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this,
                    "Sorry, there is a problem. Please try again. ",
                    Toast.LENGTH_SHORT).show()
            }

        val firestoreInfo2 = firestore_val.collection("comicbook_products").document("ZPC_CB02")
        firestoreInfo2.get()
            .addOnSuccessListener { documentRetrieve2 ->
                if (documentRetrieve2 != null) {
                    val change_textview_item2 =
                        findViewById(R.id.returninventory_activity_item_2) as TextView
                    change_textview_item2.text =
                        " ${documentRetrieve2.id}: ${documentRetrieve2.getString("product_name")} \nAvailable Stock: ${documentRetrieve2.get("available_stock")}"

                    returninventory_activity_item_2_issue_btn.setOnClickListener {
                        val i = Intent(this@ReturnInventoryActivity,
                            InventoryStatusChangeRqActivity::class.java)
                        i.putExtra("issue_status", "Return")
                        i.putExtra("item_part_num", "${documentRetrieve2.id}")
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
                    val change_textview_item3 =
                        findViewById(R.id.returninventory_activity_item_3) as TextView
                    change_textview_item3.text =
                        " ${documentRetrieve3.id}: ${documentRetrieve3.getString("product_name")} \nAvailable Stock: ${documentRetrieve3.get("available_stock")}"

                    returninventory_activity_item_3_issue_btn.setOnClickListener {
                        val i = Intent(this@ReturnInventoryActivity,
                            InventoryStatusChangeRqActivity::class.java)
                        i.putExtra("issue_status", "Return")
                        i.putExtra("item_part_num", "${documentRetrieve3.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

        returninventory_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }


        returninventory_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }

        returninventory_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        returninventory_activity_return_books_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        returninventory_activity_view_account_page_btnv.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
