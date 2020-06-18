package com.example.easilylearnenglish.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easilylearnenglish.Learnactivity;
import com.example.easilylearnenglish.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
EditText emailtext,passwordtext;
String email,password;
Boolean remember;
SharedPreferences sharedPreferences;
CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        emailtext= findViewById(R.id.emailtext);
        passwordtext=findViewById(R.id.passwordtext);
        checkBox= findViewById(R.id.checkBox);
        sharedPreferences = this.getSharedPreferences("com.example.easilylearnenglish",MODE_PRIVATE);
        remember = sharedPreferences.getBoolean("remember",false);
        if (remember){
            emailtext.setText(sharedPreferences.getString("email",""));
            passwordtext.setText(sharedPreferences.getString("password",""));

        }
    }
    public void signupbutton(View view){
    email = emailtext.getText().toString();
    password=passwordtext.getText().toString();
    if (!email.matches("")&& !password.matches("")) {
        sharedPreferences.edit().putBoolean("remember",false).apply();
        if (checkBox.isChecked()) {
            sharedPreferences.edit().putBoolean("remember", true).apply();
            sharedPreferences.edit().putString("email", email).apply();
            sharedPreferences.edit().putString("password", password).apply();
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Kullanıcı Oluşturuldu", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Learnactivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Kullanıcı Oluşturulamadı", Toast.LENGTH_SHORT).show();
                emailtext.setText("");
                passwordtext.setText("");
            }
        });

    }
    }
    public void signinbutton(View view){
    email=emailtext.getText().toString();
    password=passwordtext.getText().toString();
        if (!email.matches("")&& !password.matches("")) {
            sharedPreferences.edit().putBoolean("remember",false).apply();

            if (checkBox.isChecked()) {
                sharedPreferences.edit().putBoolean("remember", true).apply();
                sharedPreferences.edit().putString("email", email).apply();
                sharedPreferences.edit().putString("password", password).apply();
            }
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Learnactivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Hatalı Giriş", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public  void  resetpasswordonclick(View view){
        Intent intent= new Intent(getApplicationContext(), Resetpasswordactivity.class);
        startActivity(intent);
    }
}
