package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_issue_inventory.*
import kotlinx.android.synthetic.main.activity_login_successful.*

//Declearing and extending this class as an AppCompactActivity.//
class LoginSuccessfulActivity : AppCompatActivity() {

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_successful) //Setting the content page to activity_login_successful for this activity.//
        val firestore_database = Firebase.firestore  //Setting the firestore_database variable to the Google Firebase/Firestore network.//


        var stored_userDetails = FirebaseAuth.getInstance().currentUser //Collecting details saved using the Firebase Authenticator about the current user and setting this as a variable called stored_userDetails.//

        //Setting the following variable as the text value which displays the current users email address.//
        val usersEmail_set = findViewById<TextView>(R.id.loginsuccessful_activity_signed_in_email_dsp)
        usersEmail_set.setText(stored_userDetails!!.getEmail()) //Retrieving the email address from the users stored information located on the Firebase Authenticator.//

        val db = Firebase.firestore //Setting the db variable to the Google Firebase/Firestore network.//

        //Retrieve/Read data from product ZPC_CB01 located in the comicbook_products Firestore collection.//
        val firestoreInfo1 = db.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    //Setting the following variable as the text value for the textfield loginsuccessful_activity_item_1 which includes the product_name, available_stock and the issued_stock for item1.//
                    val change_textview_item1 = findViewById(R.id.loginsuccessful_activity_item_1) as TextView
                    change_textview_item1.text = "${documentRetrieve1.getString("product_name")}: ${documentRetrieve1.id} \n" +
                            "Stock Available: ${documentRetrieve1.get("available_stock")} \n" +
                            "Stock Issued: ${documentRetrieve1.get("issued_stock")}"
                }
            }

            //The system records and notifies the user of an error if the system is unable to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't connect to the database right now. Please try again later.", Toast.LENGTH_SHORT).show()
            }

        //Retrieve/Read data from product ZPC_CB02 located in the comicbook_products Firestore collection.//
        val firestoreInfo2 = db.collection("comicbook_products").document("ZPC_CB02")
        firestoreInfo2.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB02.//
            .addOnSuccessListener { documentRetrieve2 ->
                if (documentRetrieve2 != null) {

                    //Setting the following variables as the text value for the textfield loginsuccessful_activity_item_2 which includes the product_name, available_stock and the issued_stock for item2.//
                    val change_textview_item2 = findViewById(R.id.loginsuccessful_activity_item_2) as TextView
                    change_textview_item2.text = "${documentRetrieve2.getString("product_name")}: ${documentRetrieve2.id} \n" +
                            "Stock Available: ${documentRetrieve2.get("available_stock")} \n" +
                            "Stock Issued: ${documentRetrieve2.get("issued_stock")}"
                }
            }

            //The system records and notifies the user of an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB02.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't connect to the database right now. Please try again later.", Toast.LENGTH_SHORT).show()
            }

        //Retrieve/Read data from product ZPC_CB03 located in the comicbook_products Firestore collection.//
        val firestoreInfo3 = db.collection("comicbook_products").document("ZPC_CB03")
        firestoreInfo3.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnSuccessListener { documentRetrieve3 ->
                if (documentRetrieve3 != null) {

                    //Setting the following variable as the text value for the textfield loginsuccessful_activity_item_3 which includes the product_name, available_stock and the issued_stock for item3.//
                    val change_textview_item3 = findViewById(R.id.loginsuccessful_activity_item_3) as TextView
                    change_textview_item3.text = "${documentRetrieve3.getString("product_name")}: ${documentRetrieve3.id} \n" +
                            "Stock Available: ${documentRetrieve3.get("available_stock")} \n" +
                            "Stock Issued: ${documentRetrieve3.get("issued_stock")}"
                }
            }

            //The system records and notifies the user of an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't connect to the database right now. Please try again later.", Toast.LENGTH_SHORT).show()
            }



        //Once the button named "inventory_status_change_activity_cancel_change_btn" is pressed, the user is notified that they are already on the LoginSuccessfulActivity.//
        loginsuccessful_activity_login_successful_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        //Once the button named "loginsuccessful_activity_reset_inventory_btn" is pressed, the system reset the inventory stock to the specified stock levels noted in the assessment instructions.//
        loginsuccessful_activity_reset_inventory_btn.setOnClickListener {
            //The following .updates() are to reset the stock levels of each document/comic as noted in the student instructions.//
            firestore_database.collection("comicbook_products").document("ZPC_CB01")
                .update("available_stock", 8,"issued_stock",0)
            firestore_database.collection("comicbook_products").document("ZPC_CB02")
                .update("available_stock", 12,"issued_stock",0)
            firestore_database.collection("comicbook_products").document("ZPC_CB03")
                .update("available_stock", 3,"issued_stock",0)

            //Once the task have been completed successfully, the page will reload to reflect the new updates above.//
            val i = Intent(this@LoginSuccessfulActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()

            //Once the inventory stock has been reset successfully (and once the page has been reloaded), the user is notified that the reset task was successful.//
            Toast.makeText(this, "Inventory reset successful.", Toast.LENGTH_SHORT).show()
            }

        //Once the button named "loginsuccessful_activity_restock_inventory_page_btn" is pressed, the user is sent to the RestockInventoryActivity.//
        loginsuccessful_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "loginsuccessful_activity_issue_books_page_btn" is pressed, the user is sent to the IssueInventoryActivity.//
        loginsuccessful_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "loginsuccessful_activity_return_books_page_btn" is pressed, the user is sent to the ReturnInventoryActivity.//
        loginsuccessful_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "loginsuccessful_activity_view_account_page_btnv" is pressed, the user is sent to the AccountDetailsActivity.//
        loginsuccessful_activity_view_account_page_btnv.setOnClickListener {
            val i = Intent(this@LoginSuccessfulActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
