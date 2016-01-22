package com.just_app.diplom;


import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StartFragment  extends Fragment{
   private ImageButton Numbers,Shapes,Words;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.start_menu,container,false);

        Numbers= (ImageButton)v.findViewById(R.id.Numbers);
        Shapes= (ImageButton)v.findViewById(R.id.Shapes);
        Words= (ImageButton)v.findViewById(R.id.Words);

        View.OnClickListener onClickListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.Numbers:
                        Intent p1= new Intent(getActivity(),Activity_menu.class);
                        p1.putExtra(Fragment_menu.EXTRA_ITEM,"numbers.xml");
                        startActivity(p1);
                        break;
                    case R.id.Words:
                        Intent p2= new Intent(getActivity(),Activity_menu.class);
                        p2.putExtra(Fragment_menu.EXTRA_ITEM, "words.xml");
                        startActivity(p2);
                        break;
                    case R.id.Shapes:
                        Intent p4= new Intent(getActivity(),Activity_menu.class);
                        p4.putExtra(Fragment_menu.EXTRA_ITEM, "shapes.xml");
                        startActivity(p4);
                        break;
                }
            }
        };

        Numbers.setOnClickListener(onClickListener);
        Words.setOnClickListener(onClickListener);
        Shapes.setOnClickListener(onClickListener);

        return v;
    }
}
