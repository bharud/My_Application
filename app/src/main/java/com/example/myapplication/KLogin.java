package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class KLogin {
    Context c;
    Activity a;
    ProgressDialog pd;
    Fungsi f;

    public KLogin() {
    }

    public KLogin(Context c, Activity a) {
        this.c = c;
        this.a = a;
        f = new Fungsi(c,a);
    }

    public void ubahTeksTombolGoogle(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    public void ambilData(final GoogleSignInResult result, GoogleSignInAccount x, final FirebaseAuth auth, final ProgressDialog pd, final DatabaseReference dr, final SharedPreferences.Editor ed, final GoogleApiClient googleApiClient, final EditText et, final Button btn, final SignInButton gg){
        if(result.isSuccess()){
            x = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(x.getIdToken(), null);
            auth.signInWithCredential(credential).addOnCompleteListener(a, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if (!task.isSuccessful()) {
                        Toast.makeText(c, "noakses", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        f.dr.child("user").orderByChild("gmail").equalTo(f.fu.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0){
                                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                                        ModelUser mu = ds.getValue(ModelUser.class);
                                        f.ed.putString("idku",mu.getIdnya());
                                        f.ed.putString("email", mu.getGmail());
                                        f.ed.putString("nohp", mu.getNohp());
                                        f.ed.putString("nama", mu.getNama());
                                        f.ed.commit();
                                    }
                                    a.startActivity(new Intent(c,MainActivity.class));
                                    a.finish();
                                }else {
                                    gg.setVisibility(View.GONE);
                                    btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String hp = et.getText().toString();
                                            if (!TextUtils.isEmpty(hp)){
                                                Intent up = new Intent(c, DataAwal.class);
                                                up.putExtra("email", f.fu.getEmail());
                                                up.putExtra("nama", f.fu.getDisplayName());
                                                up.putExtra("notlpn", et.getText().toString());
                                                up.putExtra("kode", "gmail");

                                                a.startActivity(up);
                                            }else{
                                                Toast.makeText(c, "Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });
        }
    }

    public void Masuk(ProgressDialog pd, GoogleApiClient googleApiClient){
        pd.show();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        a.startActivityForResult(intent,Fungsi.kodenos);
    }

    public void Keluar(FirebaseAuth auth, GoogleApiClient googleApiClient){
        auth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

            }
        });
    }
}
