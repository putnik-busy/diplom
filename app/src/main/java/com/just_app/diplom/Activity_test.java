package com.just_app.diplom;


import android.app.Fragment;

public class Activity_test extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        String  item = (String)getIntent()
                .getSerializableExtra(Test_Fragment.EXTRA_ITEM_TEST);
        return Test_Fragment.newInstance(item);
    }
}
