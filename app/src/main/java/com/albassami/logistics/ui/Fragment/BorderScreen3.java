package com.albassami.logistics.ui.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.albassami.logistics.R;


public class BorderScreen3 extends Fragment {
    ImageView background;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_border_screen3, container, false);
        LinearLayout frameLayout=view.findViewById(R.id.f3);
        background=view.findViewById(R.id.border3);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("lang", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("language","").equals("ar")) {
            background.setBackgroundResource(R.drawable.interface3);
            frameLayout.setRotationY(180);
        }
        else{
            background.setBackgroundResource(R.drawable.interfaceeng3);
        }
        return view;

    }
}
