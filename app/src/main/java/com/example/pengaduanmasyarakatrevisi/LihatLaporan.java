package com.example.pengaduanmasyarakatrevisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LihatLaporan extends AppCompatActivity {
    private String CurrentUserId;
    FirebaseAuth firebaseauth;
    RecyclerView recyclerView;
    List<Laporan> laporanList;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_laporan);
        firebaseauth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseauth.getCurrentUser().getUid();

        recyclerView =findViewById(R.id.recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(LihatLaporan.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(LihatLaporan.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        laporanList = new ArrayList<>();
        MyAdapter myAdapter = new MyAdapter(LihatLaporan.this,laporanList);
        recyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Laporan").child(CurrentUserId);

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                laporanList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Laporan laporan = itemSnapshot.getValue(Laporan.class);
                    laporanList.add(laporan);
                }
                myAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });
    }
}