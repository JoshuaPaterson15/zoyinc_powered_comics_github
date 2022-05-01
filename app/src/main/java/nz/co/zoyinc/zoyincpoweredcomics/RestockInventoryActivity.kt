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
import kotlinx.android.synthetic.main.activity_restock_inventory.*

//Declearing and extending this class as an AppCompactActivity.//
class RestockInventoryActivity : AppCompatActivity() {

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restock_inventory) //Setting the content page to activity_restock_inventory for this activity.//
        val firestore_val = Firebase.firestore //Setting the firestore_val to the Google Firebase/Firestore network.//

        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo1 = firestore_val.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    //Setting the following variable as the text value for the textfield restockinventory_activity_item_1 which includes the product_name and the available_stock for item1.//
                    val product_name =
                        findViewById(R.id.restockinventory_activity_item_1) as TextView
                    product_name.text ="${documentRetrieve1.id}: ${documentRetrieve1.getString("product_name")} \nAvailable Stock: ${documentRetrieve1.get("available_stock")}"

                    //If the user presses the "restockinventory_activity_item_1_issue_btn" button, the user is sent to the RestockInventoryStatusChangeRqActivity saving the current issue_status and item_document_num data.//
                    restockinventory_activity_item_1_issue_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryActivity,
                            RestockInventoryStatusChangeRqActivity::class.java)
                        i.putExtra("issue_status", "Restock")
                        i.putExtra("item_document_num", "${documentRetrieve1.id}")
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

        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo2 = firestore_val.collection("comicbook_products").document("ZPC_CB02")
        firestoreInfo2.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB02.//
            .addOnSuccessListener { documentRetrieve2 ->
                if (documentRetrieve2 != null) {

                    //Setting the following variable as the text value for the textfield restockinventory_activity_item_2 which includes the product_name and the available_stock for item2//
                    val product_name = findViewById<TextView>(R.id.restockinventory_activity_item_2)
                    product_name.text ="${documentRetrieve2.id}: ${documentRetrieve2.getString("product_name")} \nAvailable Stock: ${documentRetrieve2.get("available_stock")}"

                    //If the user presses the "restockinventory_activity_item_2_issue_btn" button, the user is sent to the RestockInventoryStatusChangeRqActivity saving the current issue_status and item_document_num data.//
                    restockinventory_activity_item_2_issue_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryActivity,
                            RestockInventoryStatusChangeRqActivity::class.java)
                        i.putExtra("issue_status", "Restock")
                        i.putExtra("item_document_num", "${documentRetrieve2.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB02.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo2 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }
        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo3 = firestore_val.collection("comicbook_products").document("ZPC_CB03")
        firestoreInfo3.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnSuccessListener { documentRetrieve3 ->
                if (documentRetrieve3 != null) {

                    //Setting the following variable as the text value for the textfield restockinventory_activity_item3 which includes the product_name and the available_stock for item3.//
                    val product_name = findViewById<TextView>(R.id.restockinventory_activity_item_3)
                    product_name.text ="${documentRetrieve3.id}: ${documentRetrieve3.getString("product_name")} \nAvailable Stock: ${documentRetrieve3.get("available_stock")}"

                    //If the user presses the "restockinventory_activity_item_3_issue_btn" button, the user is sent to the RestockInventoryStatusChangeRqActivity saving the current issue_status and item_document_num data.//
                    restockinventory_activity_item_3_issue_btn.setOnClickListener {
                        val i = Intent(this@RestockInventoryActivity,
                            RestockInventoryStatusChangeRqActivity::class.java)
                        i.putExtra("issue_status", "Restock")
                        i.putExtra("item_document_num", "${documentRetrieve3.id}")
                        startActivity(i)
                        finish()
                    }
                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo3 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }

        //Once the button named "restockinventory_activity_login_successful_page_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
        restockinventory_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "restockinventory_activity_issue_books_page_btn" is pressed, the user is sent to the IssueInventoryActivity.//
        restockinventory_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "restockinventory_activity_restock_inventory_page_btn" is pressed, the user is notified that they are already on the RestockInventoryActivity.//
        restockinventory_activity_restock_inventory_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        //Once the button named "restockinventory_activity_return_books_page_btn" is pressed, the user is sent to the ReturnInventoryActivity.//
        restockinventory_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "restockinventory_activity_view_account_page_btn" is pressed, the user is sent to the AccountDetailsActivity.//
        restockinventory_activity_view_account_page_btn.setOnClickListener {
            val i = Intent(this@RestockInventoryActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }


    }


}