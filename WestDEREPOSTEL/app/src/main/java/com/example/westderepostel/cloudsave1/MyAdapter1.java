package com.example.westderepostel.cloudsave1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.westderepostel.R;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {



        private ArrayList<Model1> mList;
        private Context context;

        public MyAdapter1(Context context , ArrayList<Model1> mList){

            this.context = context;
            this.mList = mList;
        }

        @NonNull
        @Override
        public MyAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item1 , parent ,false);
            return new MyAdapter1.MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder holder, int position) {
            Glide.with(context).load(mList.get(position).getImageUrl()).into(holder.imageView1);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView imageView1;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView1 = itemView.findViewById(R.id.m_image);
            }
        }

}
