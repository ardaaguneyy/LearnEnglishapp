package com.example.easilylearnenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Lessondetailactivity extends AppCompatActivity {
    String lessonname;
    TextView wordview, wordview2,wordview3,wordview4,wordview5,wordview6,wordview7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessondetailactivity);
        //findviewbyid
        wordview = findViewById(R.id.wordview);
        wordview2 =findViewById(R.id.wordview2);
        wordview3= findViewById(R.id.wordview3);
        wordview4 = findViewById(R.id.wordview4);
        wordview5 = findViewById(R.id.wordview5);
        wordview6 = findViewById(R.id.wordview6);
        wordview7 = findViewById(R.id.wordview7);
        //intent
        Intent intent = getIntent();
        lessonname =intent.getStringExtra("name");
        System.out.println(lessonname);
        getdata();
    }
   public void lessondetailonclick(View view){
        Intent intent = new Intent(getApplicationContext(),Lessondetailactivitytwo.class);
        intent.putExtra("lessonname",lessonname);
        startActivity(intent);
   }
   public void getdata(){
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Lessons",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT word,word2,word3,word4,word5,word6,word7 FROM lessons WHERE name = ?",new String[]{lessonname});
            int word = cursor.getColumnIndex("word");
            int word2 = cursor.getColumnIndex("word2");
            int word3 = cursor.getColumnIndex("word3");
            int word4 =cursor.getColumnIndex("word4");
            int word5 =cursor.getColumnIndex("word5");
            int word6 = cursor.getColumnIndex("word6");
            int word7 = cursor.getColumnIndex("word7");
            while (cursor.moveToNext()){
                wordview.setText(cursor.getString(word));
                wordview2.setText(cursor.getString(word2));
                wordview3.setText(cursor.getString(word3));
                wordview4.setText(cursor.getString(word4));
                wordview5.setText(cursor.getString(word5));
                wordview6.setText(cursor.getString(word6));
                wordview7.setText(cursor.getString(word7));
            }

            cursor.close();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }


   }
}