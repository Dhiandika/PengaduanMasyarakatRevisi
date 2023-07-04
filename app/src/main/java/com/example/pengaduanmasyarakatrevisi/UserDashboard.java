package com.example.pengaduanmasyarakatrevisi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pengaduanmasyarakatrevisi.Models.NewsApiResponse;
import com.example.pengaduanmasyarakatrevisi.Models.NewsHeadline;
import com.example.pengaduanmasyarakatrevisi.Models.RequestManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class UserDashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ExtendedFloatingActionButton addbutton,tambahlaporan,lihatlaporan;
    Boolean visiblefalse;
    Button btnberita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener,"general",null);
        btnberita = findViewById(R.id.btnberita);



        addbutton = findViewById(R.id.floatingActionButton);
        tambahlaporan = findViewById(R.id.addfab);
        lihatlaporan = findViewById(R.id.seefab);
        visiblefalse=false;
        addbutton.shrink();
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!visiblefalse){
                    tambahlaporan.show();
                    lihatlaporan.show();
                }else {
                    tambahlaporan.hide();
                    lihatlaporan.hide();
                    addbutton.shrink();
                    visiblefalse = false;
                }
            }
        });

        tambahlaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, TambahLaporan.class);
                startActivity(intent);
            }
        });

        lihatlaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, LihatLaporan.class);
                startActivity(intent);
            }
        });
    }
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onfectchData(List<NewsHeadline> list, String message) {
            showNews(list);

        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadline> list) {
        recyclerView = findViewById(R.id.reycycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list, null);
        recyclerView.setAdapter(adapter);
    }

}