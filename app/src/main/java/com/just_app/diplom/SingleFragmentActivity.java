package com.just_app.diplom;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getFragmentManager();

            Fragment_menu fragment_menu= new Fragment_menu();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment_menu)
                    .commit();
    }
}
