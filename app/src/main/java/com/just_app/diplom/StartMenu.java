package com.just_app.diplom;


import android.app.Fragment;

public class StartMenu extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new StartFragment();
    }
}
