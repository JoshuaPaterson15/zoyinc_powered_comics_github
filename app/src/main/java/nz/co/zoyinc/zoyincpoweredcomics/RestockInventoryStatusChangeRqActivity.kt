package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
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

//Declearing and extending this class as an AppCompactActivity.//
class RestockInventoryStatusChangeRqActivity : AppCompatActivity() {
    private var user_number_provided: EditText? = null //Setting and declearing the private variable user_number_provided.//

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restock_inventory_status_change_rq) //Setting the content page to activity_restock_inventory_status_change_rq for this activity.//
        user_number_provided = findViewById<EditText>(R.id.restockinventorystatuschangerq_activity_user_input) //Setting the following variable (user_number_provided) as the text value from restockinventorystatuschangerq_activity_user_input.//
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

                    //Once the button named "restockinventorystatuschangerq_activity_confirm_btn" has been pressed, the system will verify that the number provided by the user is an integer.//
                    //Once the system has verified that the user's input is an integer/numeric number, the system will send the user to the next activity (InventoryStatusChangeRqActivity).//
                    restockinventorystatuschangerq_activity_confirm_btn.setOnClickListener {
                        val add_stock_val = user_number_provided!!.text.toString() //Converting the integer value to a string value.//
                        if (add_stock_val != "") { //The following actions below will only occur if the add_stock_val value has no response/no input by the user.//
                            var numeric = true //confirming that the value entered by the user is a true numeric value/integer value.//

                            //The following actions are performed to ensure that the final value is set as an integer value and that when an attempt is made to convert a string value into a numeric value when it is not possible, the numeric variable becomes false.
                            try {
                                val num = parseDouble(add_stock_val)
                            } catch (e: NumberFormatException) {
                                numeric = false
                            }

                            if (numeric) { //The system will on send the user to the InventoryStatusChangeRqActivity if the user's input was a numeric value saving the issue_status, item_part_num and number_requested data.//
                                val i = Intent(this@RestockInventoryStatusChangeRqActivity,
                                    InventoryStatusChangeRqActivity::class.java)
                                i.putExtra("issue_status", inventory_status)
                                i.putExtra("item_part_num", inventory_item_document_id)
                                i.putExtra("number_requested", add_stock_val)
                                startActivity(i)
                                finish()

                            } else { //The user will be notified that they need to enter an integer value, not a string value.//
                                Toast.makeText(this,
                                    "Sorry, please enter a number into the field above.",
                                    Toast.LENGTH_LONG).show()
                            }

                        } else { //The user will be notified that they have entered in nothing into the restockinventorystatuschangerq_activity_user_input field and they cannot proceed onto the next step unless they insert a response.//
                            Toast.makeText(this,"Sorry, please enter a number into the field above.",Toast.LENGTH_LONG).show()
                        }
                    }

                    //Once the button named "restockinventorystatuschangerq_activity_cancel_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
                    restockinventorystatuschangerq_activity_cancel_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryStatusChangeRqActivity,
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