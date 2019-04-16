package rob.local.itas274_finalproject_keenanfichtler_robbieschiersmann;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    Button b_roll;
    ImageView image1;
    Random r;
    int img1;
    public final static String DEBUG_TAG = "MainActivity";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String currentUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //
        Intent intent = getIntent();
        currentUserId = intent.getStringExtra("user");
        Log.d(DEBUG_TAG, "Message from Main: Logged in user is: " + currentUserId);

        b_roll = findViewById(R.id.b_roll);
        r = new Random();
        image1 = findViewById(R.id.image1);

    }

    public void roll(View view) {
        Log.d(DEBUG_TAG, "Rolling!");
        //animate first image
        image1.setBackgroundResource(R.drawable.anim);
        final AnimationDrawable image1anim = (AnimationDrawable) image1.getBackground();
        image1anim.start();
        //stop the animation and apply the changes
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                image1anim.stop();
                setImages();
            }
        }, 500);
    }
    public void setImages() {
        //Randomize the images
        img1 = r.nextInt(4) + 1;
        //set the first image
        switch (img1) {
            case 1:
                image1.setBackgroundResource(R.drawable.bar);
                break;
            case 2:
                image1.setBackgroundResource(R.drawable.cherry);
                break;
            case 3:
                image1.setBackgroundResource(R.drawable.lemon);
                break;
            case 4:
                image1.setBackgroundResource(R.drawable.seven);
                break;
        }
        Log.d(DEBUG_TAG, "Picture is set to: " + img1);
        addHistory();
    }

    public void addHistory() {
        //Add the setImage (roll) result to the users' history

        //Create a new history document
        Map<String, Object> history = new HashMap<>();
        history.put("imageCaseNum", img1);

        //Add the history document to the database
        db.collection("users").document(currentUserId)
                .collection("history")
                .add(history)
                //Pass & Fail Checks
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(DEBUG_TAG, "History updated with image: " + img1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(DEBUG_TAG, "Error updating history", e);
                    }
                });

    }


    public void toHistory(View view) {
        Intent intent = new Intent(this, history.class);
        intent.putExtra("user", currentUserId);
        //intent.putExtra("user_history", img1);
        startActivity(intent);
    }

}