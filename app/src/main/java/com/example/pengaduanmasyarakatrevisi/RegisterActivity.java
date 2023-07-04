package com.example.pengaduanmasyarakatrevisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    TextView Login,logocalender;
    Button gotolog;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    EditText userreg,passreg,verifyreg,nik,ttl,notelp;
    String userregister, passregister,verifyregister;
    private int hari,bulan,tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Login = (TextView)findViewById(R.id.gotologin);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        nik = findViewById(R.id.nikreg);
        notelp = findViewById(R.id.notelpreg);
        ttl = findViewById(R.id.tanggallahir);
        logocalender = (TextView)findViewById(R.id.logocalender);
        userreg = findViewById(R.id.usernamereg);
        passreg = findViewById(R.id.passreg);
        verifyreg = findViewById(R.id.verifypassreg);
        gotolog = findViewById(R.id.regbutton);
        gotolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                if (TextUtils.isEmpty(nik.getText().toString()) && TextUtils.isEmpty(ttl.getText().toString())
                        && TextUtils.isEmpty(userreg.getText().toString()) && TextUtils.isEmpty(passreg.getText().toString())
                        && TextUtils.isEmpty(verifyreg.getText().toString())){
                    nik.setError("Please fill the NIK");
                    ttl.setError("Please fill the Tanggal Lahir");
                    userreg.setError("Please fill the Username");
                    passreg.setError("Please fill the password");
                    verifyreg.setError("Please verify the password");
                    return;
                }
                if (TextUtils.isEmpty(nik.getText().toString())){
                    nik.setError("Please fill the NIK");
                    return;
                }

                else if (TextUtils.isEmpty(notelp.getText().toString())){
                    nik.setError("Please fill Call Number");
                    return;
                }

                else if (TextUtils.isEmpty(ttl.getText().toString())){
                    ttl.setError("Please fill the Tanggal Lahir");
                    return;
                }

                else if (TextUtils.isEmpty(userreg.getText().toString())){
                    userreg.setError("Please fill the Username");
                    return;
                }

                else if (TextUtils.isEmpty(passreg.getText().toString())){
                    passreg.setError("Please fill the password");
                    return;
                }

                else if (TextUtils.isEmpty(verifyreg.getText().toString())){
                    verifyreg.setError("Please verify the password");
                    return;

                }

                else if (!(passreg.getText().toString().matches(verifyreg.getText().toString()))){
                    Toast.makeText(RegisterActivity.this, "Password not Match", Toast.LENGTH_LONG).show();
                }

                else {
                    firebaseAuth.createUserWithEmailAndPassword(userreg.getText().toString(),passreg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String uid = task.getResult().getUser().getUid();
                                Users user = new Users(uid,nik.getText().toString(),notelp.getText().toString(), ttl.getText().toString(), userreg.getText().toString(),passreg.getText().toString(), 0);


                                firebaseDatabase.getReference().child("Users").child(uid).setValue(user);
                                Toast.makeText(RegisterActivity.this, "Register Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));

                            }

                            else {
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
//                else {
//                    cekReg();
//                }
            }
        });

        logocalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                hari = calendar.get(Calendar.DAY_OF_MONTH);
                bulan = calendar.get(Calendar.MONTH);
                tahun = calendar.get(Calendar.YEAR);

                DatePickerDialog dialog;
                dialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        tahun = year;
                        bulan = month;
                        hari = day;

                        ttl.setText(hari + "-" + bulan + "-" + tahun);
                    }
                },tahun,bulan,hari);
                dialog.show();
            }
        });
    }
}