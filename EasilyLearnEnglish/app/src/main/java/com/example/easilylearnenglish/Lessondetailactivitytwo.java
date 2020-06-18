package com.example.easilylearnenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Lessondetailactivitytwo extends AppCompatActivity {
String lessonname;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessondetailactivitytwo);
        textView = findViewById(R.id.textView7);
        Intent intent = getIntent();
        lessonname =  intent.getStringExtra("lessonname");
        getdata();
    }
    public void succesfullonclick(View view){
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Lessons",MODE_PRIVATE,null);
            String sqlstring = "UPDATE lessons SET finish = 'true' WHERE name = ?";
            SQLiteStatement statement = database.compileStatement(sqlstring);
            statement.bindString(1,lessonname);
            statement.execute();
        }catch (Exception e ){
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(),Learnactivity.class);
        startActivity(intent);
    }
    public void getdata(){
        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Lessons",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT info FROM lessons WHERE name = ?",new String[]{lessonname});
            int info = cursor.getColumnIndex("info");
            while (cursor.moveToNext()){
            textView.setText(cursor.getString(info));
            }

            cursor.close();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }


    }
}