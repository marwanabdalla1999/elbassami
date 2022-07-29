package com.albassami.logistics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.albassami.logistics.R;
import com.albassami.logistics.ui.Adapter.recycleradapter;

import java.util.ArrayList;
import java.util.Locale;

public class contactus extends AppCompatActivity {
LinearLayout calling,sending;
TextView phone,email;
ImageView btnBack;
ArrayList<Bitmap> imgarr;
    recycleradapter recylceradapter;
RecyclerView recyclerView;
ImageView img;

    public void setUpLocale() {

        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            Locale myLocale = new Locale("ar");
            Locale.setDefault(myLocale);
            Configuration config = new Configuration();
            config.locale = myLocale;
            getBaseContext().getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());
            // new PreferenceHelper(this).getLanguage();
            sharedPreferences=getSharedPreferences("lang",MODE_PRIVATE);
            sharedPreferences.edit().putString("language","ar").apply();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLocale();
        setContentView(R.layout.activity_contactus);
 calling=findViewById(R.id.phoneus);
 sending=findViewById(R.id.sendemail);
 phone=findViewById(R.id.ourphone);
 img=findViewById(R.id.twetter);
 img.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Uri uri=Uri.parse("https://twitter.com/albassamiint?s=21");
         Intent intent=new Intent(Intent.ACTION_VIEW,uri);
         startActivity(intent);
     }
 });
 email=findViewById(R.id.email);
        btnBack=findViewById(R.id.btnBack3);
        recyclerView=findViewById(R.id.ads);
        imgarr=new ArrayList<>();
        imgarr.add(BitmapFactory.decodeResource(getResources(),R.drawable.fourth));
        imgarr.add(BitmapFactory.decodeResource(getResources(),R.drawable.frist));
        imgarr.add(BitmapFactory.decodeResource(getResources(),R.drawable.second));
        imgarr.add(BitmapFactory.decodeResource(getResources(),R.drawable.third));
        imgarr.add(BitmapFactory.decodeResource(getResources(),R.drawable.fifth));
        recylceradapter=new recycleradapter(this,imgarr);
        recyclerView.setAdapter(recylceradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        SharedPreferences sharedPreferences=getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")){
            btnBack.setRotation(180);}
        btnBack.setOnClickListener(i->onBackPressed());
 calling.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         Intent callIntent = new Intent(Intent.ACTION_DIAL);
         callIntent.setData(Uri.parse("tel: "+phone.getText().toString()));


         try {
             startActivity(callIntent);
         }
     catch (SecurityException s){
         Toast.makeText(contactus.this,s.getMessage(), Toast.LENGTH_SHORT)
                 .show();

     }
     }
 });
 sending.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         try{
             Intent intent = new Intent (Intent.ACTION_SEND);
             String[] recipients={email.getText().toString()};
             intent.putExtra(Intent.EXTRA_EMAIL, recipients);
             intent.putExtra(Intent.EXTRA_CC,email.getText().toString());
             intent.setType("text/html");
             intent.setPackage("com.google.android.gm");
             startActivity(intent);
         }catch(ActivityNotFoundException e){
             Toast.makeText(contactus.this, "can't find activity", Toast.LENGTH_SHORT).show();

         }
     }
 });

    }
}
