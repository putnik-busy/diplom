package com.just_app.obuchaika.ui.activity;

import android.app.Fragment;

import com.just_app.obuchaika.ui.fragment.Fragment_menu;

public class Activity_menu extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        String item= (String)getIntent()
                .getSerializableExtra(Fragment_menu.EXTRA_ITEM);
        return Fragment_menu.newInstance(item);
    }
}


