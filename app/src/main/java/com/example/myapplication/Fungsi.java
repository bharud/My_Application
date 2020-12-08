package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Fungsi {
    Context c;
    Activity a;
    public SharedPreferences sp;
    public SharedPreferences.Editor ed;
    public DatabaseReference dr;
    public ProgressDialog pd;
    public static final String xyz = "https://fcm.googleapis.com/fcm/send";
    public static final String tag = "com.nusantara.online.sistem.noscustomer";
    public static final int kodenos = 100;
    public FirebaseAuth fa;
    public FirebaseUser fu;

    public Fungsi() {
    }

    public Fungsi(Context c) {
        this.c = c;
        sp = c.getSharedPreferences(tag, Context.MODE_PRIVATE);
        ed = sp.edit();
        dr = FirebaseDatabase.getInstance().getReference();
        fa = FirebaseAuth.getInstance();
        fu = fa.getCurrentUser();
        pd = new ProgressDialog(c);
        pd.setMessage("proses");
        pd.setCancelable(false);
    }

    public String Rupiah(int uang){
        Locale localeID = new Locale("in", "ID");
        NumberFormat df = NumberFormat.getCurrencyInstance(localeID);
        CharSequence cs = df.format(uang);
        String hasil = cs.toString();
        return hasil.substring(2).replaceAll(",00", "");
    }

    public double jarakGratis(String k1, String k2, String l1, String l2){
        Location loc1 = new Location("");
        loc1.setLatitude(Float.valueOf(l1));
        loc1.setLongitude(Float.valueOf(l2));

        Location loc2 = new Location("");
        loc2.setLatitude(Float.valueOf(k1));
        loc2.setLongitude(Float.valueOf(k2));

        double kilometer = loc1.distanceTo(loc2) / 1000;
        return kilometer;
    }

    public static int getPixelsFromDPs(Activity activity, int dps){
        Resources r = activity.getResources();

        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

    public Fungsi(Context c, Activity a) {
        this.c = c;
        this.a = a;
        sp = c.getSharedPreferences(tag, Context.MODE_PRIVATE);
        ed = sp.edit();
        dr = FirebaseDatabase.getInstance().getReference();
        fa = FirebaseAuth.getInstance();
        fu = fa.getCurrentUser();
        pd = new ProgressDialog(c);
        pd.setMessage("proses");
        pd.setCancelable(false);
    }

    public String getAlamat(Context con, double lat, double lon) {
        String alamatLengkap = null;
        try {
            Geocoder geocoder = new Geocoder(con, Locale.getDefault());
            List<Address> ambilalamat = geocoder.getFromLocation(lat, lon, 1);
            if (ambilalamat.size() > 0) {
                Address alamat = ambilalamat.get(0);
                alamatLengkap = alamat.getAddressLine(0);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return alamatLengkap;
    }

    public void bsbOff(){
        View v = a.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }catch(NullPointerException e){
        }
    }
}


