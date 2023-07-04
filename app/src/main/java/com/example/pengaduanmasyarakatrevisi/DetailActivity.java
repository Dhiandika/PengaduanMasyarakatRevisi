package com.example.pengaduanmasyarakatrevisi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView detailImage;
    TextView detailTitle,detailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailImage = findViewById(R.id.detailImage);
        detailTitle = findViewById(R.id.detailTitle);
        detailDesc = findViewById(R.id.detailDesc);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            detailTitle.setText(bundle.getString("judul"));
            detailDesc.setText(bundle.getString("keluhan"));
            Glide.with(this).load(bundle.getString("gambar")).into(detailImage);
        }
    }
}