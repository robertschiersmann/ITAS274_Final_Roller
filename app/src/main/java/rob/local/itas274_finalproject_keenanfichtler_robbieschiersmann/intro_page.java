package rob.local.itas274_finalproject_keenanfichtler_robbieschiersmann;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

public class intro_page extends AppCompatActivity {
    public static String DEBUG_TAG = "itas123";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_page);

        // db stuff
        //dbHelper = new MySQLiteHelper(getApplicationContext());
        // make the call to open a writeable database
        //open();
        db = FirebaseFirestore.getInstance();

    }


    /*
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    */

    /**
     * Method to add a Password and Username to the DB based upon what the user put in the EditText
     */
    public void addUser(View view) {
        EditText editText = (EditText)findViewById(R.id.username);
        String usernameText = editText.getText().toString();
        //createUser(userText);

        EditText editText2 = (EditText)findViewById(R.id.password);
        String passwordText = editText2.getText().toString();
        createUser(usernameText, passwordText);
    }
    private String currentUserId = null;
    /**
     * Method to insert a new row into the DB
     */
    public void createUser(String username, String password) {

        /*
        //
        //MYSQL CODE:
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        long insertId = database.insert(MySQLiteHelper.TABLE_USERS, null,
                values);
        Log.d(DEBUG_TAG, "Inserted new user with id: " + insertId);

        Log.d(DEBUG_TAG,"Adding user to that firebase thing, dave");
        */

        //
        //FIRE-BASE CODE:
        //create a new user with first and last name
        Map<String, Object> doc = new HashMap<>();
        doc.put("username",username);
        doc.put("password", password);

        //add a new document with a generated ID
        db.collection("users")
                .add(doc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        currentUserId = documentReference.getId();
                        Log.d(DEBUG_TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })


                .addOnFailureListener(new OnFailureListener() {
                    public void onFailure (@NonNull Exception e) {
                        Log.d(DEBUG_TAG,"Error adding document",e);
                    }
                });
    }

    /**
     * This method demonstrates the equivalent of select * and uses the Cursor object to
     * iterate through all the results and Log them
     */
    public void logUser(View view) {

        // TODO
        // Get the username and password from the EditText
        EditText editText = findViewById(R.id.username);
        final String username = editText.getText().toString();
        //createUser(userText);

        EditText editText2 = findViewById(R.id.password);
        final String password = editText2.getText().toString();

        Log.d(DEBUG_TAG, "FireBase Users");
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean userValid=false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(DEBUG_TAG, document.getId() + " =>" + document.getData());

                                final String usr = document.get("username").toString();
                                final String pwd = document.get("password").toString();
                                Log.d(DEBUG_TAG, "User String: " + usr);
                                Log.d(DEBUG_TAG, "Password String: " + pwd);

                                // get the password and username from document.getData()
                                //Robbie & Keenan changed '==' to '.equals' in order to be compatible with strings
                                if (pwd.equals(password) && usr.equals(username))
                                {
                                    Log.d(DEBUG_TAG, "Yo!  User: " + document.get("username") + " is valid.  Using that ID");
                                    currentUserId = document.getId();
                                    userValid = true;
                                    break;
                                }
                            }

                            if (userValid == true) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Logging IN", Toast.LENGTH_SHORT);
                                toast.show();
                                //Keenan & Robbie moving over to the main activity (Roll System)
                                //setContentView(R.layout.activity_main);
                                Intent intent = new Intent(intro_page.this, MainActivity.class);
                                intent.putExtra("user", currentUserId);
                                Log.d(DEBUG_TAG, "Your userId is: " + currentUserId);

                                startActivity(intent);
                            }

                        } else {
                            Log.d(DEBUG_TAG, "Error getting documents", task.getException());
                            Toast toast = Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });



        /*
        // keep looping while there are still results
        while (!cursor.isAfterLast()) {

            // get the data from the two columns in the next row
            long id = cursor.getLong(0);
            String checkusername = cursor.getString(1);
            String checkpassword = cursor.getString(2);

            // DAVe
            //Robbie & Keenan changed '==' to '.equals' in order to be compatible with strings
            if (username.equals(checkusername) && password.equals(checkpassword)) {
                userValid = true;
                Log.d(DEBUG_TAG, "Got a match ID[" + id + "]: From: " + username + " users: " + password);

            }

            Log.d(DEBUG_TAG, "ID[" + id + "]: From: " + username + " users: " + password);

            // go to the next row in the results
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        */


        Log.d(DEBUG_TAG, "FireBase Users");
        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(DEBUG_TAG, document.getId() + " =>" + document.getData());
                            }
                        } else {
                            Log.d(DEBUG_TAG, "Error getting documents", task.getException());
                        }
                    }
                });
    }
}





/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String choices[] = {"red","green","blue"};
        builder.setTitle("Choose a Color");
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // the 'Which' argument contains the index position
                //of the selected item
                Log.d("itas123", "You chose: " + choices[which]);

            }
        });

        // Create the AlertDialog and call show() to make it appear
        AlertDialog dialog = builder.create();
        dialog.show();



    }


}
**/

