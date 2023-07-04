package com.example.pengaduanmasyarakatrevisi;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pengaduanmasyarakatrevisi.Models.NewsHeadline;
import com.squareup.picasso.Picasso;

public class DetailActivityNews extends AppCompatActivity {
    NewsHeadline headline;
    TextView txt_title,txt_author,txt_time,txt_detail,txt_content;
    ImageView img_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
        txt_detail = findViewById(R.id.text_detail_detail);
        txt_content = findViewById(R.id.text_detail_content);
        img_news = findViewById(R.id.img_detail_news);

        headline = (NewsHeadline) getIntent().getSerializableExtra("data");

        txt_title.setText(headline.getTitle());
        txt_author.setText(headline.getAuthor());
        txt_time.setText(headline.getPublishedAt());
        txt_detail.setText(headline.getDescription());
        txt_content.setText(headline.getContent());
        Picasso.get().load(headline.getUrlToImage()).into(img_news);



    }
}