package com.example.pengaduanmasyarakatrevisi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pengaduanmasyarakatrevisi.Models.NewsHeadline;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private Context context;
    private List<NewsHeadline> headline;
    private SelectListener listener;

    public CustomAdapter(Context context, List<NewsHeadline> headline,SelectListener listener) {
        this.context = context;
        this.headline = headline;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headline_list_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        holder.text_title.setText(headline.get(position).getTitle());
        holder.text_source.setText(headline.get(position).getSource().getName());

        if (headline.get(position).getUrlToImage()!=null){
            Picasso.get().load(headline.get(position).getUrlToImage()).into(holder.img_headline);

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnNewsClicked(headline.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return headline.size();
    }
}
