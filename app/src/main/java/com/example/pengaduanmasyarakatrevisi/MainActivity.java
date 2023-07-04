package com.example.pengaduanmasyarakatrevisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText inputemaillog,inputpasslog;
    Button logbutton;
    Button gotoregbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputemaillog = findViewById(R.id.emaillog);
        inputpasslog = findViewById(R.id.passlog);
        logbutton = findViewById(R.id.logbutton);
        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//              cekLogin();
                if (TextUtils.isEmpty(inputemaillog.getText().toString())){
                    inputemaillog.setError("Please fill the Email");
                    return;
                }

                else if (TextUtils.isEmpty(inputpasslog.getText().toString())) {
                    inputpasslog.setError("Please fill the password");
                    return;
                }

                else {
                    firebaseAuth.signInWithEmailAndPassword(inputemaillog.getText().toString(),inputpasslog.getText().toString()).addOnCompleteListener((task) ->  {
                        if(task.isSuccessful()){
                            String uid = task.getResult().getUser().getUid();


                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            firebaseDatabase.getReference().child("Users").child(uid).child("usertype").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int usertype=snapshot.getValue(Integer.class);
                                    if (usertype==1){
                                        Intent in = new Intent(MainActivity.this,AdminDashboard.class);
                                        startActivity(in);
                                    }
                                    else if (usertype==0){
                                        startActivity(new Intent(MainActivity.this,UserDashboard.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        gotoregbutton = findViewById(R.id.gotoregister);
        gotoregbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}