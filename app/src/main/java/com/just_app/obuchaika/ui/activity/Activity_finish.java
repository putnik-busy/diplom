package com.just_app.obuchaika.ui.activity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.just_app.obuchaika.ui.fragment.Fragment_finish;

public class Activity_finish extends SingleFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        String result_star = (String) getIntent()
                .getSerializableExtra(Fragment_finish.EXTRA_RESULT);
        return Fragment_finish.newInstance(result_star);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
