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
import kotlinx.android.synthetic.main.activity_issue_inventory.*
import java.lang.Double

//Declearing and extending this class as an AppCompactActivity.//
class IssueInventoryActivity : AppCompatActivity() {

    //The following code is used in the event the activity needs to be recreated (e.g. in the event the orientation of the device changes) while keeping information previously entered (e.g. users email address).//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_inventory) //Setting the content page to activity_issue_inventory for this activity.//
        val db = Firebase.firestore //Setting the db variable to the Google Firebase/Firestore network.//

        //Retrieve/Read data from the comicbook_products Firestore collection.//
        val firestoreInfo1 = db.collection("comicbook_products").document("ZPC_CB01")
        firestoreInfo1.get()

            //The following actions are completed if the system is able to successfully retrieve/read all of data from the comicbook_products Firestore collection which is related to document ZPC_CB01.//
            .addOnSuccessListener { documentRetrieve1 ->
                if (documentRetrieve1 != null) {

                    //Setting the following variable as the text value for the textfield issueinventory_activity_item_1 which includes the product_name and the available_stock for item1.//
                    val change_textview_item1 = findViewById(R.id.issueinventory_activity_item_1) as TextView
                    change_textview_item1.text = "${documentRetrieve1.id}: ${documentRetrieve1.getString("product_name")} \nAvailable Stock: ${documentRetrieve1.get("available_stock")}"

                    //If the user presses the "issueinventory_activity_item_1_issue_btn" button, the user is sent to the InventoryStatusChangeRqActivity saving the current issue_status and item_part_num data.//
                    issueinventory_activity_item_1_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve1.get("available_stock").toString() //Converting the add_stock_val/available_stock integer value to a string value.//
                        val num = Double.parseDouble(add_stock_val) //Converting the add_stock_val string value into an num integer value using parseDouble.//
                        if  (num >= 1) { //An if statement to ensure that books can only be available/in-stock if 1 or more books (with the same title) are currently available/in-stock.//
                            val i = Intent(this@IssueInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Issue")
                            i.putExtra("item_part_num","${documentRetrieve1.id}")
                            startActivity(i)
                            finish()
                        }
                        else { //If the system detects that this book isn't currently available/in-stock, the system would notify the user that they can't issue a book because no books are currently available/in-stock and the system would avoid the book from being issued.//
                            Toast.makeText(this,
                                "Sorry, we have there are no books available/in-stock. Please issue another book.",
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

                    //Setting the following variable as the text value for the textfield issueinventory_activity_item_2 which includes the product_name and the available_stock for item2.//
                    val change_textview_item2 = findViewById(R.id.issueinventory_activity_item_2) as TextView
                    change_textview_item2.text = "${documentRetrieve2.id}: ${documentRetrieve2.getString("product_name")} \nAvailable Stock: ${documentRetrieve2.get("available_stock")}"

                    //If the user presses the "issueinventory_activity_item_2_issue_btn" button, the user is sent to the InventoryStatusChangeRqActivity saving the current issue_status and item_part_num data.//
                    issueinventory_activity_item_2_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve2.get("available_stock").toString() //Converting the add_stock_val/available_stock integer value to a string value.//
                        val num = Double.parseDouble(add_stock_val) //Converting the add_stock_val string value into an num integer value using parseDouble.//
                        if  (num >= 1) { //An if statement to ensure that books can only be issued if 1 or more books (with the same title) are currently available/in-stock.//
                            val i = Intent(this@IssueInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Issue")
                            i.putExtra("item_part_num","${documentRetrieve2.id}")
                            startActivity(i)
                            finish()
                        }
                        else { //If the system detects that this book isn't currently available/in-stock, the system would notify the user that they can't issue a book because no books are currently available/in-stock and the system would avoid the book from being issued.//
                            Toast.makeText(this,
                                "Sorry, we have there are no books available/in-stock. Please issue another book.",
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

                    //Setting the following variable as the text value for the textfield issueinventory_activity_item_3 which includes the product_name and the available_stock for item3.//
                    val change_textview_item3 = findViewById(R.id.issueinventory_activity_item_3) as TextView
                    change_textview_item3.text = "${documentRetrieve3.id}: ${documentRetrieve3.getString("product_name")} \nAvailable Stock: ${documentRetrieve3.get("available_stock")}"

                    //If the user presses the "issueinventory_activity_item_3_issue_btn" button, the user is sent to the InventoryStatusChangeRqActivity saving the current issue_status and item_part_num data.//
                    issueinventory_activity_item_3_issue_btn.setOnClickListener {
                        val add_stock_val = documentRetrieve3.get("available_stock").toString() //Converting the add_stock_val/available_stock integer value to a string value.//
                        val num = Double.parseDouble(add_stock_val) //Converting the add_stock_val string value into an num integer value using parseDouble.//
                        if  (num >= 1) { //An if statement to ensure that books can only be issued if 1 or more books (with the same title) are currently available/in-stock.//
                            val i = Intent(this@IssueInventoryActivity, InventoryStatusChangeRqActivity::class.java)
                            i.putExtra("issue_status","Issue")
                            i.putExtra("item_part_num","${documentRetrieve3.id}")
                            startActivity(i)
                            finish()
                        } else { //If the system detects that this book isn't currently available/in-stock, the system would notify the user that they can't issue a book because no books are currently available/in-stock and the system would avoid the book from being issued.//
                            Toast.makeText(this,
                                "Sorry, we have run out of stock. Please issue another book.",
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                }
            }
            //The system records an error if the system is unable to successfully retrieve/read data from the comicbook_products Firestore collection which is related to document ZPC_CB03.//
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
                Toast.makeText(this, "We're sorry, we can't retrieve firestoreInfo1 now. Please restart the app.", Toast.LENGTH_SHORT).show()
            }

        //Once the button named "issueinventory_activity_restock_inventory_page_btn" is pressed, the user is sent to the RestockInventoryActivity.//
        issueinventory_activity_restock_inventory_page_btn.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, RestockInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "issueinventory_activity_issue_books_page_btn" is pressed, the user is notified that they are already on the IssueInventoryActivity.//
        issueinventory_activity_issue_books_page_btn.setOnClickListener {
            Toast.makeText(this, "Sorry, you are already on this page. ", Toast.LENGTH_SHORT).show()
        }

        //Once the button named "issueinventory_activity_login_successful_page_btn" is pressed, the user is sent to the LoginSuccessfulActivity.//
        issueinventory_activity_login_successful_page_btn.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, LoginSuccessfulActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "issueinventory_activity_return_books_page_btn" is pressed, the user is sent to the ReturnInventoryActivity.//
        issueinventory_activity_return_books_page_btn.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, ReturnInventoryActivity::class.java)
            startActivity(i)
            finish()
        }

        //Once the button named "issueinventory_activity_view_account_page_btnv" is pressed, the user is sent to the AccountDetailsActivity.//
        issueinventory_activity_view_account_page_btnv.setOnClickListener {
            val i = Intent(this@IssueInventoryActivity, AccountDetailsActivity::class.java)
            startActivity(i)
            finish()
        }


    }

}