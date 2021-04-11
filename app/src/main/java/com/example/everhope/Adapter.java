package com.example.everhope;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.awt.font.TextAttribute;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private String[] names;
    private String[] contents;
    private String[] ranks;
    public Adapter(Context context, String[] name, String[] content, String[] rank){
        this.inflater = LayoutInflater.from(context);
        this.names = name;
        this.contents = content;
        this.ranks = rank;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.lb_custom,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String name = names[i];
        String content = contents[i];
        String rank = ranks[i];
        holder.lb_name.setText(name);
        holder.lb_content.setText(content);
        holder.lb_rank.setText(rank);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView lb_name, lb_content, lb_rank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lb_name = (TextView) itemView.findViewById(R.id.names);
            lb_content = (TextView) itemView.findViewById(R.id.events);
            lb_rank = (TextView) itemView.findViewById(R.id.ranks);
        }
    }
}
