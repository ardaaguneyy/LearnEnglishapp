package com.example.easilylearnenglish;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReCyclerAdapterforlessons  extends RecyclerView.Adapter<ReCyclerAdapterforlessons.Lessonholder> {

    ArrayList<String> lessonnames;


    public ReCyclerAdapterforlessons(ArrayList<String> lessonnames) {
        this.lessonnames = lessonnames;

    }

    @NonNull
    @Override
    public ReCyclerAdapterforlessons.Lessonholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.lessonslayout,parent,false);
        return new ReCyclerAdapterforlessons.Lessonholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReCyclerAdapterforlessons.Lessonholder holder, final int position) {
        holder.textView.setText(lessonnames.get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Lessondetailactivity.class);
                intent.putExtra("name",lessonnames.get(position));
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return lessonnames.size();
    }

    public class Lessonholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public Lessonholder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.lessonnametext);
            imageView = itemView.findViewById(R.id.lessonimage);


        }



        }

    }

