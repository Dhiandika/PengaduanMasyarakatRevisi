package com.example.pengaduanmasyarakatrevisi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Laporan>laporanList;

    public MyAdapter(Context context, List<Laporan> laporanList) {
        this.context = context;
        this.laporanList = laporanList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleritem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(laporanList.get(position).getGambar()).into(holder.recImage);
        holder.recTitle.setText(laporanList.get(position).getJudul());
        holder.recDesc.setText(laporanList.get(position).getKeluhan());
        holder.recDelete.setText(laporanList.get(position).getLokasi());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("gambar",laporanList.get(holder.getAdapterPosition()).getGambar());
                intent.putExtra("judul",laporanList.get(holder.getAdapterPosition()).getJudul());
                intent.putExtra("keluhan",laporanList.get(holder.getAdapterPosition()).getKeluhan());
                intent.putExtra("Key",laporanList).get(holder.getAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return laporanList.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle,recDesc,recDelete;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        recImage = itemView.findViewById(R.id.recImage);
        recTitle = itemView.findViewById(R.id.recTitle);
        recDesc = itemView.findViewById(R.id.recdescription);
        recDelete = itemView.findViewById(R.id.recDelete);
        recCard = itemView.findViewById(R.id.recCard);



    }
}
