package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterData extends RecyclerView.Adapter<AdapterData.VHnya> {
Context context;
ArrayList<ModelData> modelData;
Activity ac;
DatabaseReference dr;

    public AdapterData(Context context, ArrayList<ModelData> modelData, Activity ac) {
        this.context = context;
        this.modelData = modelData;
        this.ac = ac;
        dr = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public VHnya onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_data, null);
        return new VHnya(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VHnya holder, int position) {
        final ModelData md = modelData.get(position);
        holder.nama.setText(md.getNama());
        holder.nim.setText(md.getNim()+"");
        holder.jk.setText(md.getJenis_kelamin());

        holder.btn_hapus.setTag(holder);
        holder.btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr.child("mahasiswa").child(md.getId()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelData.size();
    }

    public class VHnya extends RecyclerView.ViewHolder{
        TextView nama, nim, jk;
        Button btn_hapus;
        public VHnya(View v){
            super(v);

            nama = v.findViewById(R.id.tv_nama);
            nim = v.findViewById(R.id.tv_nim);
            jk = v.findViewById(R.id.tv_jk);
            btn_hapus = v.findViewById(R.id.btn_hapus);
        }
    }

}
