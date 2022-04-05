package nz.co.zoyinc.zoyincpoweredcomics

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inventory_status_changed.*

class InventoryStatusChangedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_status_changed)

        val firestore_val = Firebase.firestore

        val inventory_status = intent.getStringExtra("issue_status")
        val inventory_item_document_id = intent.getStringExtra("item_document_num")


        val firestoreInfo1 =
            firestore_val.collection("comicbook_products").document("${inventory_item_document_id}")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    val inventory_status_dsp =
                        findViewById(R.id.inventory_status_changed_activity_main_heading) as TextView
                    val item_number_dsp =
                        findViewById(R.id.inventory_status_changed_activity_general_description) as TextView

                    inventory_status_dsp.text = "Inventory ${inventory_status}ed - "
                    item_number_dsp.text =
                        "Item/s ${inventory_status}ed: \n${documentRetrieve1.getString("product_name")}: ${documentRetrieve1.id} \n Stock Available: ${documentRetrieve1.get("stock")} \n Stock Issued: ${documentRetrieve1.get("issued_stock")}"

                    inventory_status_changed_activity_acknowledgement_btn.setOnClickListener {
                        val i = Intent(this@InventoryStatusChangedActivity,
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