package com.just_app.obuchaika.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.just_app.obuchaika.R;
import com.just_app.obuchaika.ui.activity.StartMenu;

public class Fragment_finish extends Fragment implements View.OnClickListener {
    public static final String EXTRA_RESULT = "com.just_app.diplom.result";
    private String mResultStar;

    public static Fragment_finish newInstance(String result) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_RESULT, result);
        Fragment_finish fragment_finish = new Fragment_finish();
        fragment_finish.setArguments(args);
        return fragment_finish;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mResultStar = (String) getArguments()
                .getSerializable(EXTRA_RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.finish_test_activity, container, false);
        TextView result_star = (TextView) v.findViewById(R.id.result_star);
        ImageButton back_main = (ImageButton) v.findViewById(R.id.back_main);
        back_main.setOnClickListener(this);
        result_star.setText(mResultStar);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_main:
                Intent intent = new Intent(getActivity(), StartMenu.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
        }
    }
}
