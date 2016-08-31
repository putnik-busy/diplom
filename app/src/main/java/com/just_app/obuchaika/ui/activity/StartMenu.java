package com.just_app.obuchaika.ui.activity;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;

import com.just_app.obuchaika.ui.dialog.SelectDialog;
import com.just_app.obuchaika.ui.fragment.StartFragment;

public class StartMenu extends SingleFragmentActivity {
    DialogFragment dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dlg= new SelectDialog();
    }

    @Override
    protected Fragment createFragment() {
        return new StartFragment();
    }

    @Override
    public void onBackPressed() {
        dlg.show(getFragmentManager(), "dlg");
    }
}
