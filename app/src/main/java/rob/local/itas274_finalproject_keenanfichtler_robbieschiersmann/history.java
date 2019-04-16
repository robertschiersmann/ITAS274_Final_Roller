package rob.local.itas274_finalproject_keenanfichtler_robbieschiersmann;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class history extends AppCompatActivity {
    ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;

    public final static String DEBUG_TAG = "HistoryActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String currentUserId;
    int imgX;
    //Below is a switch for the ImageViews to be used when adding images to them.
    //Helps eliminate redundant code by creating a sort of dynamic variable.
    // --Robbie
    private ImageView image(int index) {
        switch (index) {
            case 1:
                return (ImageView) findViewById(R.id.history1);
            case 2:
                return (ImageView) findViewById(R.id.history2);
            case 3:
                return (ImageView) findViewById(R.id.history3);
            case 4:
                return (ImageView) findViewById(R.id.history4);
            case 5:
                return (ImageView) findViewById(R.id.history5);
            case 6:
                return (ImageView) findViewById(R.id.history6);
            case 7:
                return (ImageView) findViewById(R.id.history7);
            case 8:
                return (ImageView) findViewById(R.id.history8);
            case 9:
                return (ImageView) findViewById(R.id.history9);
            default:
                return (ImageView) findViewById(R.id.history1);
        }
    }


    public void onCreate (Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.history);

        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("user");
        //imgH = intent.getIntExtra("user_history",0);
        Log.d(DEBUG_TAG, "Message from History: Logged in user is: " + currentUserId);
        //Log.d(DEBUG_TAG, "imgH is set to number: " + imgH);

        //Give names to each ImageView
        image1 = findViewById(R.id.history1);
        image2 = findViewById(R.id.history2);
        image3 = findViewById(R.id.history3);
        image4 = findViewById(R.id.history4);
        image5 = findViewById(R.id.history5);
        image6 = findViewById(R.id.history6);
        image7 = findViewById(R.id.history7);
        image8 = findViewById(R.id.history8);
        image9 = findViewById(R.id.history9);

        //Dave!
        //I Made This!
        // --Robbie
        db.collection("users").document(currentUserId)
                .collection("history").limit(9)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(DEBUG_TAG, "Starting to fill the scrollview");

                            //For each imageview in the history scrollview, look at the Firebase sub-collection "history",
                            // and determine what items the user has obtained.
                            int i = 1;
                            while (i < 9) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(DEBUG_TAG, document.getId() + " => " + document.getData());

                                    //Grab the case number associated with an image
                                    final int imgH = Integer.valueOf(document.get("imageCaseNum").toString());
                                    Log.d(DEBUG_TAG, "imgH is set to number: " + imgH);
                                    Log.d(DEBUG_TAG, "integer 'i' is set to number: " + i);

                                    //Determine which image to apply with the grabbed case number 'imgH'.
                                    if (imgH != 0 && i < 9) {
                                        imgX = imgH;
                                        switch (imgX) {
                                            case 1:
                                                image(i).setBackgroundResource(R.drawable.bar);
                                                break;
                                            case 2:
                                                image(i).setBackgroundResource(R.drawable.cherry);
                                                break;
                                            case 3:
                                                image(i).setBackgroundResource(R.drawable.lemon);
                                                break;
                                            case 4:
                                                image(i).setBackgroundResource(R.drawable.seven);
                                                break;
                                            default:
                                                image(i).setBackgroundResource(R.drawable.image_not_available);
                                                break;
                                        }
                                        Log.d(DEBUG_TAG, "Setting scrollview to case #" + imgX);
                                        i++;
                                    }else if(i <= 9) {
                                        break;
                                    }
                                        else {
                                            //If no image has been found, declare image not found and use placeholder.
                                            //Original code block below by Keenan,
                                            //Added into this giant command by Robbie
                                            image1.setBackgroundResource(R.drawable.image_not_available);
                                            image2.setBackgroundResource(R.drawable.image_not_available);
                                            image3.setBackgroundResource(R.drawable.image_not_available);
                                            image4.setBackgroundResource(R.drawable.image_not_available);
                                            image5.setBackgroundResource(R.drawable.image_not_available);
                                            image6.setBackgroundResource(R.drawable.image_not_available);
                                            image7.setBackgroundResource(R.drawable.image_not_available);
                                            image8.setBackgroundResource(R.drawable.image_not_available);
                                            image9.setBackgroundResource(R.drawable.image_not_available);
                                            break;
                                        }
                                }
                            }
                        } else {
                            Log.w(DEBUG_TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    //Back to our MainActivity
    // --Keenan
    public void toSlots(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user", currentUserId);
        startActivity(intent);
    }
}
