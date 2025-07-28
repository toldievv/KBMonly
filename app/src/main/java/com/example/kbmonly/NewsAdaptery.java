package com.example.kbmonly;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdaptery extends RecyclerView.Adapter<NewsAdaptery.MyViewHolder> {

    private Context nsContext;
    private List<NewsModelClass> nsData;

    public NewsAdaptery(Context nsContext, List<NewsModelClass> nsData) {
        this.nsContext = nsContext;
        this.nsData = nsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(nsContext);
        v = inflater.inflate(R.layout.item_news, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.description.setText(nsData.get(position).getDescription());

        // Используем Glide библиотеку для отображения картинок
        Glide.with(nsContext)
                .load(nsData.get(position).getImageUrl())
                .into(holder.imageUrl);
    }

    @Override
    public int getItemCount() {
        return nsData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView description;
        ImageView imageUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.tvDescription);
            imageUrl = itemView.findViewById(R.id.ivImageUrl);
        }
    }
}
