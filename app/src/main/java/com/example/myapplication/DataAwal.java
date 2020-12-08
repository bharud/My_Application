package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class DataAwal extends AppCompatActivity {
    EditText et_nama, et_email;
    Button btn_signin;
    String email, nama, notlpn, kode;
    Fungsi f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_awal);

        f = new Fungsi(this, this);
        et_nama = findViewById(R.id.et_nama);
        et_email = findViewById(R.id.et_email);
        btn_signin = findViewById(R.id.btn_signin);

        email = getIntent().getExtras().getString("email");
        nama = getIntent().getExtras().getString("nama");
        notlpn = getIntent().getExtras().getString("notlpn");
        kode = getIntent().getExtras().getString("kode");

        et_nama.setText(nama);
        et_email.setText(email);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idnya = f.dr.push().getKey();
                ModelUser mu = new ModelUser();

                if (kode.equals("gmail")) {
                    mu.setFb("kosong");
                    mu.setGmail(et_email.getText().toString());
                } else {
                    mu.setFb(et_email.getText().toString());
                    mu.setGmail("kosong");
                }
                mu.setNama(et_nama.getText().toString());
                mu.setNohp(notlpn);
                mu.setIdnya(idnya);

                f.dr.child("user").child(idnya).setValue(mu, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(DataAwal.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                        } else {
                            f.ed.putString("idku",idnya);
                            f.ed.putString("email", et_email.getText().toString());
                            f.ed.putString("nohp", notlpn);
                            f.ed.putString("nama", et_nama.getText().toString());
                            f.ed.commit();
                            startActivity(new Intent(DataAwal.this, MainActivity.class));
                        }
                    }
                });

            }
        });
    }
}