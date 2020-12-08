package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    private SignInButton masuk;
    private GoogleApiClient googleApiClient;
    private GoogleSignInAccount x;

    FirebaseAuth auth;
    FirebaseUser fu;
    KLogin kLogin;
    Fungsi f;
    Button btn_masuk;
    EditText et_nomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_masuk = findViewById(R.id.btn_masuk);
        et_nomor = findViewById(R.id.et_nomor);

        init();
    }

    private void init() {
        f = new Fungsi(this, this);
        kLogin = new KLogin(this, this);
        auth = FirebaseAuth.getInstance();
        fu = auth.getCurrentUser();
        masuk = (SignInButton) findViewById(R.id.masuk);
        kLogin.ubahTeksTombolGoogle(masuk, "Google");
        masuk.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.masuk:
                kLogin.Masuk(f.pd, googleApiClient);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Fungsi.kodenos){
            GoogleSignInResult hasil = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            kLogin.ambilData(hasil,x,auth, f.pd, f.dr,f.ed,googleApiClient, et_nomor, btn_masuk, masuk);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}