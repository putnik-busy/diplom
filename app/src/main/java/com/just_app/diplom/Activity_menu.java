package com.just_app.diplom;


import android.app.Fragment;

public class Activity_menu extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return new Fragment_menu();
    }
}
