package com.example.kbmonly;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private Context mContext;
    private List<GameModelClass> mData;

    public Adaptery(Context mContext, List<GameModelClass> mData, RecyclerViewInterface recyclerViewInterface) {
        this.mContext = mContext;
        this.mData = mData;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.item_game, parent, false);

        return new MyViewHolder(v, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.title.setText(mData.get(position).getTitle());
        holder.description.setText(mData.get(position).getDescription());
        holder.genre.setText(mData.get(position).getGenre());
        holder.releaseYear.setText(mData.get(position).getReleaseYear());

        // Используем Glide библиотеку для отображения картинок
        Glide.with(mContext)
                .load(mData.get(position).getImageUrl())
                .into(holder.imageUrl);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView imageUrl;
        TextView description;
        TextView genre;
        TextView releaseYear;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            description = itemView.findViewById(R.id.tvDescription);
            genre = itemView.findViewById(R.id.tvGenre);
            releaseYear = itemView.findViewById(R.id.tvReleaseYear);
            imageUrl = itemView.findViewById(R.id.ivImageUrl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
