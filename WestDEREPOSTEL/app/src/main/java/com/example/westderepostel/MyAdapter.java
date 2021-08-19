package com.example.westderepostel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    RecyclerView recyclerView;
    Context context;
    ArrayList<String> items=new ArrayList<>();
    ArrayList<String> urls=new ArrayList<>();

    public void update(String name,String url) {
       items.add(name);
       urls.add(url);
       notifyDataSetChanged();// refreshes the recycler view automatically...
    }

    public MyAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls=urls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//to create views for recycler view item...
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //initialise the elements of individual items..
        holder.nameOfFile.setText(items.get(position));

    }

    @Override
    public int getItemCount() {//return the No of items...
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameOfFile;

        public ViewHolder(View itemView) {//represents individual list items...
            super(itemView);
            nameOfFile=itemView.findViewById(R.id.idNameOfFile);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position=recyclerView.getChildLayoutPosition(view);
                    Intent intent=new Intent();
                    intent.setType(Intent.ACTION_VIEW);// denotes that we are going to view something...
                    intent.setData(Uri.parse("https://"+urls.get(position)));
                    context.startActivity(Intent.createChooser(intent,"dialogTitle"));

                    //startActivity(Intent.createChooser(marketIntent, "dialogTitle"));

                }
            });
        }

    }

}
