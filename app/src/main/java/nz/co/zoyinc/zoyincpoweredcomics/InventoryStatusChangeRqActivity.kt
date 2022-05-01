package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
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
import java.lang.Double.parseDouble

//Declearing and extending this class as an AppCompactActivity.//
class InventoryStatusChangeRqActivity : AppCompatActivity() {
    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_status_change_rq) //Setting the content page to activity_inventory_status_change_rq for this activity.//
        val firestore_val = Firebase.firestore //Setting the firestore_val to the Google Firebase/Firestore network.//

        //Retrieving/reading data which was previously set in the InventoryStatusChangedActivity activity and setting the retrieved data with the relevant/set variable.//
        val inventory_status = intent.getStringExtra("issue_status") //Setting the inventory_status variable as the value from the variable issue_status set from either the issue or return activity.//
        val inventory_item_document_id = intent.getStringExtra("item_part_num") //Setting the inventory_item_document_id variable as the value from the variable item_part_num set from either the issue or return activity.//
        val num = 0 //Set the num variable to 0.//


        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo1 =
            firestore_val.collection("comicbook_products").document("${inventory_item_document_id}")
        firestoreInfo1.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of the data from the comicbook_products Firestore collection.//
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    //Setting the following variables as the text values for the relevant heading/subheading.//
                    val inventory_status_dsp =
                        findViewById(R.id.inventory_status_change_activity_main_heading) as TextView
                    val item_number_dsp =
                        findViewById(R.id.inventorystatuschange_activity_subheading) as TextView

                    //Setting the inventory_status_dsp text as "$inventory_status Inventory -".//
                    inventory_status_dsp.text = "$inventory_status Inventory -"
                    val inventory_istatus = inventory_status

                    //The following actions are completed if the inventory_istatus value equals to "Restock" as a user change restock more than 1 book per transaction.//
                    if (inventory_istatus == "Restock") {
                        val inventory_restock_num_rq = intent.getStringExtra("number_requested") //Setting the inventory_restock_num_rq variable as the value from the variable number_requested set from the RestockInventoryStatusChangeRqActivity.//
                        val num = parseDouble(inventory_restock_num_rq) //Converting the inventory_restock_num_rq string value into an num integer value using parseDouble.//

                        //Setting the text value for the variable named "item_number_dsp" confirming how many units the user wants to restock.//
                        item_number_dsp.text = "Are you sure you want to ${inventory_status}: \n${
                            documentRetrieve1.getString("product_name")
                        }: ${documentRetrieve1.id} by ${inventory_restock_num_rq} units?"
                    }

                    // The following actions are completed if the system detects that the inventory_status is equal to Issue or Return.//
                    else {
                        //Setting the text value for the variable named "item_number_dsp" confirming the action the user wants to take (either issuing or returning a book/comic).//
                        item_number_dsp.text = "Are you sure you want to ${inventory_status}: \n${
                            documentRetrieve1.getString("product_name")
                        }: ${documentRetrieve1.id}"
                    }


                    //The following actions are completed if the user clicks the inventory_status_change_activity_confirm_change_btn button.//
                    inventory_status_change_activity_confirm_change_btn.setOnClickListener {

                        //The following actions are completed if the inventory_status value equals to "Issue".//
                        if (inventory_status == "Issue") {
                            //Retrieve the comicbook_products firestore colllection and update the relevant comic book (inventory_item_document_id) and decrease the available_stock by 1.//
                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("available_stock", FieldValue.increment(-1))

                            //Retrieve the comicbook_products firestore colllection and update the relevant comic book (inventory_item_document_id) and increase the issued_stock by 1.//
                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("issued_stock", FieldValue.increment(1))

                            //After the system has completed the above actions, the user is sent to the InventoryStatusChangedActivity saving the inventory_status and inventory_item_document_id data.//
                            val i = Intent(this@InventoryStatusChangeRqActivity,
                                InventoryStatusChangedActivity::class.java)
                            i.putExtra("issue_status", inventory_status)
                            i.putExtra("item_document_num", inventory_item_document_id)
                            startActivity(i)
                            finish()
                        }

                        //The following actions are completed if the inventory_status value equals to "Return".//
                        else if (inventory_status == "Return") {
                            //Retrieve the comicbook_products firestore colllection and update the relevant comic book (inventory_item_document_id) and increase the available_stock by 1.//
                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("available_stock", FieldValue.increment(1))

                            //Retrieve the comicbook_products firestore colllection and update the relevant comic book (inventory_item_document_id) and decrease the issued_stock by 1.//
                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("issued_stock", FieldValue.increment(-1))

                            //After the system has completed the above actions, the user is sent to the InventoryStatusChangedActivity saving the inventory_status and inventory_item_document_id data.//
                            val i = Intent(this@InventoryStatusChangeRqActivity,
                                InventoryStatusChangedActivity::class.java)
                            i.putExtra("issue_status", inventory_status)
                            i.putExtra("item_document_num", inventory_item_document_id)
                            startActivity(i)
                            finish()
                        }

                        //The following actions are completed if the inventory_status value equals to "Restock".//
                        else if (inventory_status == "Restock") {
                            val inventory_restock_num_rq = intent.getStringExtra("number_requested") //Setting the inventory_restock_num_rq variable as the value from the variable number_requested set from the RestockInventoryStatusChangeRqActivity.//
                            val num = parseDouble(inventory_restock_num_rq) //Converting the inventory_restock_num_rq string value into an num integer value using parseDouble.//

                            //Retrieve the comicbook_products firestore colllection and update the relevant comic book (inventory_item_document_id) and increase the available_stock by the amount selected by the user in the num variable.//
                            firestore_val.collection("comicbook_products")
                                .document("${inventory_item_document_id}")
                                .update("available_stock", FieldValue.increment(num))

                            //After the system has completed the above actions, the user is sent to the InventoryStatusChangedActivity saving the inventory_status and inventory_item_document_id data.//
                            val i = Intent(this@InventoryStatusChangeRqActivity,
                                InventoryStatusChangedActivity::class.java)
                            i.putExtra("issue_status", inventory_status) //Setting the issue_status data (from the inventory_status variable) to the intent when moving the user to the InventoryStatusChangedActivity.//
                            i.putExtra("item_document_num", inventory_item_document_id) //Setting the item_document_num data (from the inventory_item_document_id variable) to the intent when moving the user to the InventoryStatusChangedActivity.//
                            startActivity(i)
                            finish()

                        }

                        //If the inventory_status does not have a set value or if the inventory_status variable does not have a correct value (e.g. Issue, Return, Restock), the system informs the user of an error.//
                        else {
                            Toast.makeText(this,
                                "Sorry, there appears to be a problem, please try again. ",
                                Toast.LENGTH_SHORT).show()
                        }
                    }

                    //Once the button named "inventory_status_change_activity_cancel_change_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
                    inventory_status_change_activity_cancel_change_btn.setOnClickListener {
                        val i = Intent(this@InventoryStatusChangeRqActivity,
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




