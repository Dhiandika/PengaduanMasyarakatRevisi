package com.example.pengaduanmasyarakatrevisi;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class TambahLaporan extends AppCompatActivity {
    ImageView uploadimage;
    FirebaseAuth firebaseauth;
    private String CurrentUserId;
    private String DocId;
    Button uploadbutton;
    EditText judul,keluhan,lokasi;
    String ImageUri;
    Uri uri;

    String[] items = {"Ringan", "Sedang", "Berat"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    TextView backto;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_laporan);
        firebaseauth = FirebaseAuth.getInstance();
        CurrentUserId = firebaseauth.getCurrentUser().getUid();
        backto = findViewById(R.id.backtohome);
        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TambahLaporan.this, UserDashboard.class);
                startActivity(intent);
            }
        });

        uploadimage = findViewById(R.id.uploadgambar);
        uploadbutton = findViewById(R.id.uploadlaporn);
        judul = findViewById(R.id.judul);
        keluhan = findViewById(R.id.keluhan);
        lokasi = findViewById(R.id.lokasi);

        ActivityResultLauncher<Intent>activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadimage.setImageURI(uri);
                        }else {
                            Toast.makeText(TambahLaporan.this,"No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(TambahLaporan.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlimage = uriTask.getResult();
                ImageUri = urlimage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData(){
        String uploadjudul = judul.getText().toString();
        String uploadkeluhan = keluhan.getText().toString();
        String uploadlokasi = lokasi.getText().toString();

        Laporan laporan = new Laporan(CurrentUserId,uploadjudul,uploadkeluhan,"",uploadlokasi,ImageUri);
        FirebaseDatabase.getInstance().getReference().child("Laporan").child(CurrentUserId).child(uploadjudul).setValue(laporan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(TambahLaporan.this,"Laporan Diterima", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TambahLaporan.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



//    firestore = FirebaseFirestore.
//    firebaseStorage = FirebaseStorage.getInstance();
//    storageReference = firebaseStorage.getReference();
//
//    //GetCurrent User
//    CurrentUserId = firebaseauth.getCurrentUser().getUid();
//
//    // Inflate the layout for this fragment
//
//    judul = findViewById(R.id.judul);
//    keluhan = findViewById(R.id.keluhan);
//    lokasi = view.findViewById(R.id.lokasi);
//    buttonupload = view.findViewById(R.id.uploadlaporn);
//    autoCompleteTextView = view.findViewById(R.id.autocompletetext);
//    adapterItems = new ArrayAdapter<String>(requireContext(),R.layout.list_item,items);
//
//        autoCompleteTextView.setAdapter(adapterItems);
//
//    SelectPhoto = view.findViewById(R.id.uploadgambar);
//        SelectPhoto.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            CheckStoragePermission();
//            AmbilGambarDariGallery();
//
//        }
//    });
//
//        buttonupload.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            UploadImage();
//        }
//    });
//        return view;
//}
//
//
//    private void CheckStoragePermission() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if(ContextCompat.checkSelfPermission(getActivity(),
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(getActivity(),
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            }else{
//                AmbilGambarDariGallery();
//            }
//        }else {
//            AmbilGambarDariGallery();
//        }
////        Intent intent = new Intent();
////        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//    }
//
//    private void AmbilGambarDariGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        launcher.launch(intent);
//    }
//    ActivityResultLauncher<Intent> launcher
//            =registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if(result.getResultCode() == Activity.RESULT_OK){
//                    Intent data = result.getData();
//                    if(data != null && data.getData()!= null){
//                        ImageUri = data.getData();
//
//                        // Mengubah image menjadi bitmap
//                        try {
//                            bitmap = MediaStore.Images.Media.getBitmap(
//                                    getActivity().getContentResolver(), ImageUri
//                            );
//
//                        }catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if(ImageUri != null) {
//                        SelectPhoto.setImageBitmap(bitmap);
//                    }
//                }
//            }
//    );
//    // Upload Image ke Firebase
//    private void UploadImage(){
//        if(ImageUri!= null){
//            //Create Storage Instances
//            final StorageReference myref = storageReference.child("photo/" + ImageUri.getLastPathSegment());
//            myref.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    myref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            if(uri != null){
//                                photo = uri.toString();
//                                UploadDataLaporan();
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//    // Upload Data Laporan
//    private void UploadDataLaporan(){
//
//        autoCompleteTextView.setAdapter(adapterItems);
//
//        if (TextUtils.isEmpty(judullaporan) && TextUtils.isEmpty(keluhanlaporan) && TextUtils.isEmpty(lokasilaporan)){
//            judul.setError("Please Fill Judul");
//            keluhan.setError("Please Fill Keluhan");
//            lokasi.setError("Please Fill Lokasi");
//            return;
//        }else {
//            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//            DocumentReference documentReference = firestore.collection("LaporanPengaduan").document();
//            Laporan laporan = new Laporan("",CurrentUserId,judullaporan,keluhanlaporan,"ArrayAdapter",lokasilaporan,photo);
//            documentReference.set(laporan, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    DocId = documentReference.getId();
//                    laporan.setDocid(DocId);
//                    documentReference.set(laporan, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()){
//                                firebaseDatabase.getReference().child("LaporanPengaduan").child(DocId).setValue(laporan);
//                                Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }
}