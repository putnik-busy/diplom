package com.just_app.obuchaika.ui.fragment;


import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.just_app.obuchaika.R;
import com.just_app.obuchaika.ui.dialog.SelectDialog;
import com.just_app.obuchaika.ui.activity.Activity_menu;
import com.just_app.obuchaika.ui.activity.Activity_test;

public class StartFragment extends Fragment {
    DialogFragment dlg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dlg = new SelectDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_menu, container, false);
        ImageButton numbers = (ImageButton) v.findViewById(R.id.Numbers);
        ImageButton shapes = (ImageButton) v.findViewById(R.id.Shapes);
        ImageButton words = (ImageButton) v.findViewById(R.id.Words);
        ImageButton exit = (ImageButton) v.findViewById(R.id.exit);
        ImageButton mTest = (ImageButton) v.findViewById(R.id.test);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.Numbers:
                        Intent p1 = new Intent(getActivity(), Activity_menu.class);
                        p1.putExtra(Fragment_menu.EXTRA_ITEM, "numbers.xml");
                        startActivity(p1);
                        break;
                    case R.id.Words:
                        Intent p2 = new Intent(getActivity(), Activity_menu.class);
                        p2.putExtra(Fragment_menu.EXTRA_ITEM, "words.xml");
                        startActivity(p2);
                        break;
                    case R.id.Shapes:
                        Intent p4 = new Intent(getActivity(), Activity_menu.class);
                        p4.putExtra(Fragment_menu.EXTRA_ITEM, "shapes.xml");
                        startActivity(p4);
                        break;
                    case R.id.exit:
                        dlg.show(getFragmentManager(), "dlg");
                        break;
                    case R.id.test:
                        Intent p5 = new Intent(getActivity(), Activity_test.class);
                        p5.putExtra(Test_Fragment.EXTRA_ITEM_TEST, "test_question.xml");
                        startActivity(p5);
                        break;
                }
            }
        };

        numbers.setOnClickListener(onClickListener);
        words.setOnClickListener(onClickListener);
        shapes.setOnClickListener(onClickListener);
        exit.setOnClickListener(onClickListener);
        mTest.setOnClickListener(onClickListener);
        return v;
    }
}
