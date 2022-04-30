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
import java.lang.Double

class IssueInventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_inventory)
        val db = Firebase.firestore


        val firestoreInfo1 = db.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {
                    val change_textview_item1 = findViewById(R.id.issueinventory_activity_item_1) as TextView
                    change_textview_item1.text = "${documentRetrieve1.id}: ${documentRetrieve1.getString("product_name")} \nAvailable Stock: ${documentRetrieve1.get("available_stock")}"

                    issueinventory_activity_item_1_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve1.get("available_stock").toString()
                        val num = Double.parseDouble(add_stock_val)
                        if  (num >= 1) {
                            val i = Intent(this@IssueInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Issue")
                            i.putExtra("item_part_num","${documentRetrieve1.id}")
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this,
                                "Sorry, we have run out of stock. Please issue another book.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
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
                    val change_textview_item2 = findViewById(R.id.issueinventory_activity_item_2) as TextView
                    change_textview_item2.text = "${documentRetrieve2.id}: ${documentRetrieve2.getString("product_name")} \nAvailable Stock: ${documentRetrieve2.get("available_stock")}"

                    issueinventory_activity_item_2_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve2.get("available_stock").toString()
                        val num = Double.parseDouble(add_stock_val)
                        if  (num >= 1) {
                            val i = Intent(this@IssueInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Issue")
                            i.putExtra("item_part_num","${documentRetrieve2.id}")
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this,
                                "Sorry, we have run out of stock. Please issue another book.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
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
                    val change_textview_item3 = findViewById(R.id.issueinventory_activity_item_3) as TextView
                    change_textview_item3.text = "${documentRetrieve3.id}: ${documentRetrieve3.getString("product_name")} \nAvailable Stock: ${documentRetrieve3.get("available_stock")}"

                    issueinventory_activity_item_3_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve3.get("available_stock").toString()
                        val num = Double.parseDouble(add_stock_val)
                        if  (num >= 1) {
                            val i = Intent(this@IssueInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Issue")
                            i.putExtra("item_part_num","${documentRetrieve3.id}")
                            startActivity(i)
                            finish()
                        } else {
                            Toast.makeText(this,
                                "Sorry, we have run out of stock. Please issue another book.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "Sorry, you have run into a problem. ", Toast.LENGTH_SHORT).show()
            }

        issueinventory_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        issueinventory_activity_issue_books_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }


        issueinventory_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }
        issueinventory_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        issueinventory_activity_view_account_page_btnv.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }


    }

}