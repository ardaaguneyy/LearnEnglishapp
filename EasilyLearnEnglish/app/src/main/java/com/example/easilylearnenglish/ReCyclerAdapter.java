package com.example.easilylearnenglish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReCyclerAdapter extends RecyclerView.Adapter<ReCyclerAdapter.PostHolder> {
ArrayList<String> emaillist ;
ArrayList<String> commentlist ;
    public ReCyclerAdapter(ArrayList<String> emaillist, ArrayList<String> commentlist) {
        this.emaillist = emaillist;
        this.commentlist = commentlist;
    }
    @NonNull
    @Override
    public ReCyclerAdapter.PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.postsreyclerview,parent,false);
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReCyclerAdapter.PostHolder holder, int position) {
        holder.email.setText(emaillist.get(position));
        holder.comment.setText(commentlist.get(position));
    }

    @Override
    public int getItemCount() {
        return commentlist.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        TextView comment,email;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.emailtextposts);
            comment = itemView.findViewById(R.id.commenttextposts);

        }
    }


}
