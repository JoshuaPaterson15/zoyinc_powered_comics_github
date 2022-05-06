package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inventory_status_changed.*


//Declearing and extending this class as an AppCompactActivity.//
class InventoryStatusChangedActivity : AppCompatActivity() {

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_status_changed) //Setting the content page to activity_inventory_status_changed for this activity.//

        val firestore_val = Firebase.firestore //Setting the firestore_val variable to the Google Firebase/Firestore network.//

        //Retrieving/reading data which was previously set in the InventoryStatusChangedActivity activity and setting the retrieved data with the relevant/set variable.//
        val inventory_status = intent.getStringExtra("issue_status") //Setting the inventory_status variable as the value from the variable issue_status set from either the issue or return activity.//
        val inventory_item_document_id = intent.getStringExtra("item_document_num") //Setting the inventory_item_document_id variable as the value from the variable item_part_num set from either the issue or return activity.//

        //Retrieve/Read data from product ZPC_CB01 located in the comicbook_products Firestore collection.//
        val firestoreInfo1 = firestore_val.collection("comicbook_products").document("${inventory_item_document_id}")
        firestoreInfo1.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of the data from the comicbook_products Firestore collection.//
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    //Setting the following variables as the text values for the relevant heading/subheading.//
                    val inventory_status_dsp =
                        findViewById(R.id.inventory_status_changed_activity_main_heading) as TextView
                    val item_number_dsp =
                        findViewById(R.id.inventory_status_changed_activity_general_description) as TextView

                    //Setting the inventory_status_dsp text as "Inventory ${inventory_status}ed - ".//
                    inventory_status_dsp.text = "Inventory ${inventory_status}ed - "

                    //Setting the item_number_dsp text to include the product_name, available_stock and issued_stock of the book they are wanting to either issue or return.//
                    item_number_dsp.text =
                        "Item/s ${inventory_status}ed: \n${documentRetrieve1.getString("product_name")}: ${documentRetrieve1.id} \n Stock Available: ${documentRetrieve1.get("available_stock")} \n Stock Issued: ${documentRetrieve1.get("issued_stock")}"

                    //Once the button named "inventory_status_changed_activity_acknowledgement_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
                    inventory_status_changed_activity_acknowledgement_btn.setOnClickListener {
                        val i = Intent(this@InventoryStatusChangedActivity,
                            LoginSuccessfulActivity::class.java)
                        startActivity(i)
                        finish()
                    }

                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo1 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }

    }
}