package com.example.easilylearnenglish;


import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.easilylearnenglish.Learnactivity.changelanguageboolean;
import static com.example.easilylearnenglish.Learnactivity.engbitmap;
import static com.example.easilylearnenglish.Learnactivity.trbitmap;

public class Recycyleradapterfortranslation extends RecyclerView.Adapter<Recycyleradapterfortranslation.Accountholder> {
    TextView translationresult;
    @NonNull
    @Override
    public Accountholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.translationlayout,parent,false);
        return new Recycyleradapterfortranslation.Accountholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Accountholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class Accountholder extends RecyclerView.ViewHolder {
        EditText translationscannertext;
        Button buton ;
        ImageView changelanguage, turkishimage , englishimage ;
        public Accountholder(@NonNull View itemView) {
            super(itemView);
            translationresult = itemView.findViewById(R.id.translationresulttext);
            translationscannertext=itemView.findViewById(R.id.translationtext);
            buton = itemView.findViewById(R.id.button6);
            changelanguage = itemView.findViewById(R.id.imageView5);
            turkishimage = itemView.findViewById(R.id.imageView6);
            englishimage = itemView.findViewById(R.id.imageView3);
            changelanguage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if (!changelanguageboolean){
                        turkishimage.setImageBitmap(engbitmap);
                        englishimage.setImageBitmap(trbitmap);
                       changelanguageboolean = true;
                   }else {
                       turkishimage.setImageBitmap(trbitmap);
                       englishimage.setImageBitmap(engbitmap);
                       changelanguageboolean = false;
                   }
                }
            });
             buton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
            String firsttext =translationscannertext.getText().toString();
            try{
                if(!firsttext.matches("")){
                if (!changelanguageboolean) {
                    DownloadData data = new DownloadData();
                    String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200520T112427Z.6ffb212cd8713520.54a625435e89f52934a7947a27052018aa4772dd&text="+firsttext+"&lang=en";
                    data.execute(url);
                }else {
                    DownloadData data = new DownloadData();
                    String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20200520T112427Z.6ffb212cd8713520.54a625435e89f52934a7947a27052018aa4772dd&text="+firsttext+"&lang=tr";

                    data.execute(url);
                }
            }

            }catch (Exception e){
                e.printStackTrace();
            }




             }

         });
        }
    }
    private class DownloadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }


                return result;

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("text");
                String rates1 = rates.substring(2,rates.length()-2);
                translationresult.setText(rates1);


            } catch (Exception e) {
            e.printStackTrace();
            }


        }
    }


}
