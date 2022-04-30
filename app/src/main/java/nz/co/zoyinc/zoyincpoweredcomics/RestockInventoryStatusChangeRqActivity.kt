package nz.co.zoyinc.zoyincpoweredcomics

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_restock_inventory_status_change_rq.*
import java.lang.Double
import java.lang.Double.parseDouble


class RestockInventoryStatusChangeRqActivity : AppCompatActivity() {
    private var user_number_provided: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restock_inventory_status_change_rq)
        user_number_provided = findViewById<EditText>(R.id.restockinventorystatuschangerq_activity_user_input)
        val firestore_val = Firebase.firestore


        val inventory_status = intent.getStringExtra("issue_status")
        val inventory_item_document_id = intent.getStringExtra("item_document_num")


        val firestoreInfo1 = firestore_val.collection("comicbook_products").document("${inventory_item_document_id}")
        firestoreInfo1.get()
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    restockinventorystatuschangerq_activity_confirm_btn.setOnClickListener {
                        val add_stock_val = user_number_provided!!.text.toString()
                        if (add_stock_val != "") {
                            var numeric = true

                            try {
                                val num = parseDouble(add_stock_val)
                            } catch (e: NumberFormatException) {
                                numeric = false
                            }

                            if (numeric) {

                                val i = Intent(this@RestockInventoryStatusChangeRqActivity,
                                    InventoryStatusChangeRqActivity::class.java)
                                i.putExtra("issue_status", inventory_status)
                                i.putExtra("item_part_num", inventory_item_document_id)
                                i.putExtra("number_requested", add_stock_val)
                                startActivity(i)
                                finish()
                            } else
                                Toast.makeText(this,"Sorry, please enter a number into the field above.",Toast.LENGTH_LONG).show()

                        } else {
                            Toast.makeText(this,"Sorry, please enter a number into the field above.",Toast.LENGTH_LONG).show()
                        }
                    }

                    restockinventorystatuschangerq_activity_cancel_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryStatusChangeRqActivity,
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