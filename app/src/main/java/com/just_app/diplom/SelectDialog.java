package com.just_app.diplom;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelectDialog extends DialogFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog, null);
        v.findViewById(R.id.btn_no).setOnClickListener(this);
        v.findViewById(R.id.btn_yes).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                System.exit(0);
                break;
            case R.id.btn_no:
                dismiss();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        dismiss();
    }
}

