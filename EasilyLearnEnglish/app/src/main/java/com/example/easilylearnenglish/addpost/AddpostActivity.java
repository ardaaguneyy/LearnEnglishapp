package com.example.easilylearnenglish.addpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easilylearnenglish.Learnactivity;
import com.example.easilylearnenglish.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddpostActivity extends AppCompatActivity {
TextView emailtext;
EditText commenttext;
FirebaseAuth auth;
String comment;
FirebaseFirestore firestore;
Map<String,Object> usercomments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);
        emailtext = findViewById(R.id.useremailaddposttext);
        auth = FirebaseAuth.getInstance();
        commenttext= findViewById(R.id.usercommentaddtext);
        emailtext.setText(auth.getCurrentUser().getEmail());
        firestore = FirebaseFirestore.getInstance();
        usercomments = new HashMap<>();
    }
    public void addpostbutton(View view){
        comment = commenttext.getText().toString();
        if (!comment.matches("")){
        usercomments.put("comment",comment);
        usercomments.put("email",auth.getCurrentUser().getEmail());
        usercomments.put("date", FieldValue.serverTimestamp());
        firestore.collection("Posts").add(usercomments).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AddpostActivity.this, "Paylaşıldı", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(getApplicationContext(), Learnactivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddpostActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }}
}
