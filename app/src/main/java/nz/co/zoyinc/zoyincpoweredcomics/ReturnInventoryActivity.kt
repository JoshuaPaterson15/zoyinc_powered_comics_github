package nz.co.zoyinc.zoyincpoweredcomics //Declearing the application package with this file.//

//A list of imports which are needed for different functionality purposes (e.g. connecting to the Firebase).//
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_issue_inventory.*
import kotlinx.android.synthetic.main.activity_login_successful.*
import kotlinx.android.synthetic.main.activity_restock_inventory.*
import kotlinx.android.synthetic.main.activity_return_inventory.*
import java.lang.Double

//Declearing and extending this class as an AppCompactActivity.//
class ReturnInventoryActivity : AppCompatActivity() {

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return_inventory) //Setting the content page to activity_return_inventory for this activity.//
        val db = Firebase.firestore //Setting the db variable to the Google Firebase/Firestore network.//

        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo1 = db.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    //Setting the following variable as the text value for the textfield returninventory_activity_item_1 which includes the product_name and the issued_stock for item1.//
                    val change_textview_item1 = findViewById(R.id.returninventory_activity_item_1) as TextView
                    change_textview_item1.text = "${documentRetrieve1.id}: ${documentRetrieve1.getString("product_name")} \nIssued Stock: ${documentRetrieve1.get("issued_stock")}"

                    //If the user presses the "returninventory_activity_item_1_issue_btn" button, the user is sent to the InventoryStatusChangeRqActivity saving the current issue_status and item_part_num data.//
                    returninventory_activity_item_1_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve1.get("issued_stock").toString() //Converting the add_stock_val/issued_stock integer value to a string value.//
                        val num = Double.parseDouble(add_stock_val) //Converting the add_stock_val string value into an num integer value using parseDouble.//
                        if  (num >= 1) { //An if statement to ensure that books can only be returned if 1 or more books (with the same title) are currently issued.//
                            val i = Intent(this@ReturnInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Return")
                            i.putExtra("item_document_num","${documentRetrieve1.id}")
                            startActivity(i)
                            finish()
                        }
                        else { //If the system detects that this book isn't currently issued, the system would notify the user that they can't return a book because no books are currently issued and the system would avoid the book from being returned.//
                            Toast.makeText(this,
                                "Sorry, our system has detected that this book isn't currently issued.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo1 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }


        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo2 = db.collection("comicbook_products").document("ZPC_CB02")
        firestoreInfo2.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB02.//
            .addOnSuccessListener { documentRetrieve2 ->
                if (documentRetrieve2 != null) {

                    //Setting the following variable as the text value for the textfield returninventory_activity_item_2 which includes the product_name and the issued_stock for item2.//
                    val change_textview_item2 = findViewById(R.id.returninventory_activity_item_2) as TextView
                    change_textview_item2.text ="${documentRetrieve2.id}: ${documentRetrieve2.getString("product_name")} \nIssued Stock: ${documentRetrieve2.get("issued_stock")}"

                    //If the user presses the "returninventory_activity_item_2_issue_btn" button, the user is sent to the InventoryStatusChangeRqActivity saving the current issue_status and item_part_num data.//
                    returninventory_activity_item_2_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve2.get("issued_stock").toString() //Converting the add_stock_val/issued_stock integer value to a string value.//
                        val num = Double.parseDouble(add_stock_val) //Converting the add_stock_val string value into an num integer value using parseDouble.//
                        if  (num >= 1) { //An if statement to ensure that books can only be returned if 1 or more books (with the same title) are currently issued.//
                            val i = Intent(this@ReturnInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Return")
                            i.putExtra("item_document_num","${documentRetrieve2.id}")
                            startActivity(i)
                            finish()
                        }
                        else { //If the system detects that this book isn't currently issued, the system would notify the user that they can't return a book because no books are currently issued and the system would avoid the book from being returned.//
                            Toast.makeText(this,
                                "Sorry, our system has detected that this book isn't currently issued.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB02.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo2 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }

        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo3 = db.collection("comicbook_products").document("ZPC_CB03")
        firestoreInfo3.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnSuccessListener { documentRetrieve3 ->
                if (documentRetrieve3 != null) {

                    //Setting the following variable as the text value for the textfield returninventory_activity_item_3 which includes the product_name and the issued_stock for item3.//
                    val change_textview_item3 =
                        findViewById(R.id.returninventory_activity_item_3) as TextView
                    change_textview_item3.text =
                        "${documentRetrieve3.id}: ${documentRetrieve3.getString("product_name")} \nIssued Stock: ${documentRetrieve3.get("issued_stock")}"

                    //If the user presses the "returninventory_activity_item_3_issue_btn" button, the user is sent to the InventoryStatusChangeRqActivity saving the current issue_status and item_part_num data.//
                    returninventory_activity_item_3_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve3.get("issued_stock").toString() //Converting the add_stock_val/issued_stock integer value to a string value.//
                        val num = Double.parseDouble(add_stock_val) //Converting the add_stock_val string value into an num integer value using parseDouble.//
                        if  (num >= 1) { //An if statement to ensure that books can only be returned if 1 or more books (with the same title) are currently issued.//
                            val i = Intent(this@ReturnInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Return")
                            i.putExtra("item_document_num","${documentRetrieve3.id}")
                            startActivity(i)
                            finish()
                        }
                        else { //If the system detects that this book isn't currently issued, the system would notify the user that they can't return a book because no books are currently issued and the system would avoid the book from being returned.//
                            Toast.makeText(this,
                                "Sorry, our system has detected that this book isn't currently issued.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo3 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }

        //Once the button named "returninventory_activity_restock_inventory_page_btn" is pressed, the user is sent to the RestockInventoryActivity.//
        returninventory_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "returninventory_activity_login_successful_page_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
        returninventory_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "returninventory_activity_issue_books_page_btn" is pressed, the user is sent to the IssueInventoryActivity.//
        returninventory_activity_issue_books_page_btn.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, IssueInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "returninventory_activity_return_books_page_btn" is pressed, the user is notified that they are already on the ReturnInventoryActivity.//
        returninventory_activity_return_books_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        //Once the button named "returninventory_activity_view_account_page_btnv" is pressed, the user is sent to the AccountDetailsActivity.//
        returninventory_activity_view_account_page_btnv.setOnClickListener {
            val i = Intent(this@ReturnInventoryActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}