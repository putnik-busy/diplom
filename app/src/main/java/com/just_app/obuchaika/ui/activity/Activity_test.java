package com.just_app.obuchaika.ui.activity;


import android.app.Fragment;

import com.just_app.obuchaika.ui.fragment.Test_Fragment;

public class Activity_test extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String  item = (String)getIntent()
                .getSerializableExtra(Test_Fragment.EXTRA_ITEM_TEST);
        return Test_Fragment.newInstance(item);
    }
}
