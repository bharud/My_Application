package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText et_nama, et_nim, et_jk;
    private Button btn_simpan;
    private RecyclerView rv;
    ArrayList<ModelData> modelData = new ArrayList<>();
    AdapterData ad;
    Fungsi f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f = new Fungsi(this, this);

        ad = new AdapterData(this, modelData, this);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(this,1));
        rv.setHasFixedSize(true);
        rv.setAdapter(ad);
        et_nama = findViewById(R.id.et_nama);
        et_nim = findViewById(R.id.et_nim);
        et_jk = findViewById(R.id.et_jk);
        btn_simpan = findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelData md = new ModelData(et_nama.getText().toString(), et_jk.getText().toString(), Integer.parseInt(et_nim.getText().toString()));
                f.dr.child("mahasiswa").push().setValue(md, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(MainActivity.this, "Oke", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        f.dr.child("mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                modelData.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    ModelData md = ds.getValue(ModelData.class);
                    md.setId(ds.getKey());
                    modelData.add(md);
                }

                ad.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}