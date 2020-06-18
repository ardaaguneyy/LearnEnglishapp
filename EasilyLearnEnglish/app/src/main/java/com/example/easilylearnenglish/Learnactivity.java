package com.example.easilylearnenglish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.easilylearnenglish.addpost.AddpostActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Map;


public class Learnactivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    private ArrayList<String> emaillist;
    private ArrayList<String> commentlist;
    ReCyclerAdapter reCyclerAdapter;
    static  Boolean changelanguageboolean ;
    static Bitmap trbitmap;
    static Bitmap engbitmap;
    ArrayList<String> lessonnames;
    SQLiteDatabase database;
    Boolean controllessons;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menulayout,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.add_post){
            Intent intent = new Intent(getApplicationContext(), AddpostActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learnactivity);
        database = this.openOrCreateDatabase("Lessons",MODE_PRIVATE,null);
        emaillist = new ArrayList<>();
        commentlist=new ArrayList<>();
        lessonnames = new ArrayList<>();
        //Preferences
        preferences();
        //Firebase
        firestore=FirebaseFirestore.getInstance();
        getdata();
        //Recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reCyclerAdapter = new ReCyclerAdapter(emaillist,commentlist);
        recyclerView.setAdapter(reCyclerAdapter);
        //translate
        changelanguageboolean = false;
        trbitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.tr) ;
        engbitmap =  BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.eng) ;
        controllessons = true;
    }
    public void mainrecycleronclick(View view ){
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reCyclerAdapter = new ReCyclerAdapter(emaillist,commentlist);
        recyclerView.setAdapter(reCyclerAdapter);

    }
    public void  lessonrecycleronclick(View view){
        if (controllessons==true){
            try {
                Cursor cursor = database.rawQuery("SELECT name FROM lessons",null);
                int NameIx = cursor.getColumnIndex("name");
                while (cursor.moveToNext()){
                    lessonnames.add(cursor.getString(NameIx));
                }
                cursor.close();
                controllessons = false;
            }catch(Exception a){
                a.printStackTrace();
            }
        }


            try{
                RecyclerView recyclerView = findViewById(R.id.recyclerView2);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                ReCyclerAdapterforlessons  reCyclerAdapterforlessons= new ReCyclerAdapterforlessons(lessonnames);
                recyclerView.setAdapter(reCyclerAdapterforlessons);
            }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void translaterecycleronclick(View view){
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Recycyleradapterfortranslation reCyclerAdapter  = new Recycyleradapterfortranslation();
        recyclerView.setAdapter(reCyclerAdapter);

    }
    public void getdata(){
        commentlist.clear();
        emaillist.clear();
        CollectionReference reference = firestore.collection("Posts");
        reference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
        if (e!=null){
            Toast.makeText(Learnactivity.this, "Hata", Toast.LENGTH_SHORT).show();
        }
        if (queryDocumentSnapshots!=null){
            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){
                Map<String,Object> data = snapshot.getData();
                String comment = (String)data.get("comment");
                String email = (String) data.get("email");
                commentlist.add(comment);
                emaillist.add(email);
                reCyclerAdapter.notifyDataSetChanged();

            }
        }
            }
        });

    }
    public void adddata(){
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS lessons (name VARCHAR , word VARCHAR , word2 VARCHAR , word3 VARCHAR , word4 VARCHAR , word5 VARCHAR , word6 VARCHAR , word7 VARCHAR , info VARCHAR,finish VARCHAR)");
            database.execSQL("INSERT INTO lessons (name,word,word2,word3,word4,word5,word6,word7,info,finish) VALUES('Özneler', 'I = Ben' , 'He = O(erkek)' , 'She = O(Kadın)' , 'It=Hayvan+CansızVarlıklar', 'My=Benim' , 'His = Onun(Erkek)' ,'Her=Onun(Kadın)','Cümlede isim yerini tutan ifadelere zamir deriz. Türkçede oluğu gibi, İngilizce konuşurken de zamir ifadeleri çok sık kullanılır. Zamir kullanımı konusu altında ilk olarak “kişi (özne) zamirleri” nden bahsetmeliyiz. Kişi zamirleri, özel isimlerin yerine kullanılan “ben, biz, onlar” gibi ifadelerdir.\n" +
                    "\n" +
                    "Bu bölümde kişi zamirlerini öncelikle öğreneceğiz. Yeni öğrenenler için en uygun yöntem, şu anda kişi zamirlerini güzelce öğrenip, diğer zamir çeşitlerini daha sonra seviyemiz ilerleyince öğrenmek olacaktır.\n" +
                    "\n" +
                    "I              →           Ben\n" +
                    "You        →           Sen\n" +
                    "He          →           O (Erkek)\n" +
                    "She        →           O (Kadın)\n" +
                    "It             →           O (Hayvan, bitki, cansız varlık)\n" +
                    "We         →           Biz\n" +
                    "You        →           Siz\n" +
                    "They      →           Onlar\n" +
                    "\n" +
                    "Zamirler cümlede “özne” olarak kullanılar.\n" +
                    "\n" +
                    "“you” zamiri hem “sen” hem “siz” anlamında kullanılır.\n" +
                    "\n" +
                    "Üçüncü tekil kişi de cins ve cinsiyet ayrımı yapılır.\n" +
                    "\n" +
                    "Üçüncü çoğul kişiler için bu ayrım yapılmadan sadece “they” kullanılır.\n" +
                    "\n" +
                    "ingilizce özne zamirleri – subject pronouns ile ilgili örnek cümleler;\n" +
                    "\n" +
                    "My name is Ahmet. I am 12 years old.\n" +
                    "You are very kind.\n" +
                    "My father is a teacher. He works at a school.\n" +
                    "My mother is a nurse. She works at a hospital.\n" +
                    "The pencil is on the table. It is blue.\n" +
                    "Ali and I are at class. We love our teacher.\n" +
                    "Ayşe and you are very noisy. (You are very noisy.)\n" +
                    "The pencils are on the table. They are blue.','false')");
            database.execSQL("INSERT INTO lessons (name,word,word2,word3,word4,word5,word6,word7,info,finish) VALUES('Aylar ve Günler','Monday = Pazartesi','Tuesday = Salı','Wednesday = Çarşamba','Thursday = Perşembe','Friday = Cuma','Saturday = Cumartesi','Sunday = Pazar','January (Jan): Ocak\n" +
                    "February (Feb): Şubat\n" +
                    "March (Mar): Mart\n" +
                    "April (Apr): Nisan\n" +
                    "May (-): Mayıs\n" +
                    "June (-): Haziran\n" +
                    "July (-): Temmuz\n" +
                    "August (Aug): Ağustos\n" +
                    "September (Sep): Eylül\n" +
                    "October (Oct): Ekim\n" +
                    "November (Nov): Kasım\n" +
                    "December (Dec): Aralık\n" +"Hangi ay olduğunu sormak için şu cümleyi kurarız:\n" +
                    "\n" +
                    "What month is it?\n" +
                    "\n" +
                    "Cevap olarak ise:\n" +
                    "\n" +
                    "It is January’ diyebiliriz.','false')");
        }catch (Exception e ){
            e.printStackTrace();

        }

    }
    public void preferences(){
        SharedPreferences preferences = this.getSharedPreferences("com.example.easilylearnenglish", Context.MODE_PRIVATE);
        boolean control = preferences.getBoolean("control",true);
        if (control==true){
            adddata();
        }
        preferences.edit().putBoolean("control",false).apply();
    }
}