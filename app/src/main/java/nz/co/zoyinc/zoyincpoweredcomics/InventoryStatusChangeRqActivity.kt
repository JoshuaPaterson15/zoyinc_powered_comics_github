package nz.co.zoyinc.zoyincpoweredcomics

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inventory_status_change_rq.*

class InventoryStatusChangeRqActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_status_change_rq)
        val firestore_val = Firebase.firestore

        val inventory_status = intent.getStringExtra("issue_status")
        val inventory_item_document_id = intent.getStringExtra("item_document_num")


        val firestoreInfo1 = firestore_val.collection("comicbook_products").document("${inventory_item_document_id}")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {
                    val inventory_status_dsp =
                        findViewById(R.id.inventory_status_change_activity_main_heading) as TextView
                    val item_number_dsp =
                        findViewById(R.id.inventorystatuschange_activity_subheading) as TextView

                    inventory_status_dsp.text = "$inventory_status Inventory -"
                    item_number_dsp.text = "Are you sure you want to ${inventory_status}: \n${documentRetrieve1.getString("product_name")}: ${documentRetrieve1.getString("part_number")}"


                    inventory_status_change_activity_confirm_change_btn.setOnClickListener {
                        if (inventory_status != "Issue") {
                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("stock", FieldValue.increment(1))

                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("issued_stock", FieldValue.increment(1))
                        }


                        if (inventory_status != "Return") {

                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("stock", FieldValue.increment(-1))

                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("issued_stock", FieldValue.increment(-1))

                        } else {
                            Toast.makeText(this,
                                "Sorry, there appears to be a problem, please try again. ",
                                Toast.LENGTH_SHORT).show()
                        }


                        val i = Intent(this@InventoryStatusChangeRqActivity,
                            InventoryStatusChangedActivity::class.java)
                        i.putExtra("issue_status", inventory_status)
                        i.putExtra("item_document_num", inventory_item_document_id)
                        startActivity(i)
                        finish()
                    }

                    inventory_status_change_activity_cancel_change_btn.setOnClickListener {
                        val i = Intent(this@InventoryStatusChangeRqActivity,
                            LoginSuccessfulActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }


    }
}



