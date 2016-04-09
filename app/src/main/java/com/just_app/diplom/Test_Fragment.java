package com.just_app.diplom;


import android.app.Fragment;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Test_Fragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_ITEM_TEST =
            "com.just_app.diplom.item";
    private ImageView view_1;
    private ImageView view_2;
    private ImageView view_3;
    private ImageView view_4;
    private TextView textViewTestWord, scoreTextView;
    private String mItemQuestions;
    private Subject_Question mSubQuest;
    private LinearLayout mLinearLayout;
    private AssetManager mgr;
    private int i = 0;
    private int score = 0;
    private boolean flag_answer = false;

    public static Test_Fragment newInstance(String item) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEM_TEST, item);
        Test_Fragment test_fragment = new Test_Fragment();
        test_fragment.setArguments(args);
        return test_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemQuestions = (String) getArguments()
                .getSerializable(EXTRA_ITEM_TEST);
        LoadQuestionTask loadQuestionTask = new LoadQuestionTask();
        loadQuestionTask.execute(mItemQuestions);
        try {
            mSubQuest = loadQuestionTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    class LoadQuestionTask extends AsyncTask<String, String, Subject_Question> {
        @Override
        protected Subject_Question doInBackground(String... params) {
            mgr = getActivity().getAssets();
            ObjectMapper mapper = new ObjectMapper();
            try {
                InputStream inputStream;
                inputStream = mgr.open(mItemQuestions);
                mSubQuest = mapper.readValue(inputStream, Subject_Question.class);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mSubQuest;
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_test, container, false);
        init(v);
        onChangeQuestion();
        return v;
    }
    public void onChangeQuestion() {
        mgr = getActivity().getAssets();
        Collections.shuffle(mSubQuest.content);
        if (mSubQuest.content != null && i < mSubQuest.content.size()) {
            Uri imgUri = Uri.parse(mSubQuest.content.get(i).answer_1);
            iteration_Change_Question(imgUri, view_1);
            view_1.setTag(mSubQuest.content.get(i).answer_1);
            Uri imgUri_2 = Uri.parse(mSubQuest.content.get(i).answer_2);
            iteration_Change_Question(imgUri_2, view_2);
            view_2.setTag(mSubQuest.content.get(i).answer_2);
            Uri imgUri_3 = Uri.parse(mSubQuest.content.get(i).answer_3);
            iteration_Change_Question(imgUri_3, view_3);
            view_3.setTag(mSubQuest.content.get(i).answer_3);
            Uri imgUri_4 = Uri.parse(mSubQuest.content.get(i).answer_4);
            iteration_Change_Question(imgUri_4, view_4);
            view_4.setTag(mSubQuest.content.get(i).answer_4);
            String question = mSubQuest.content.get(i).question;
            textViewTestWord.setText(question);
        }
    }

    @Override
    public void onClick(View v) {
        if (i < mSubQuest.content.size())
            switch (v.getId()) {
                case R.id.button_1:
                    String srcimage = (String) view_1.getTag();
                    flag_answer = srcimage.equals(mSubQuest.content.get(i).correct_answer);
                    custom_Toast(v);
                    break;
                case R.id.button_2:
                    String srcimage_2 = (String) view_2.getTag();
                    flag_answer = srcimage_2.equals(mSubQuest.content.get(i).correct_answer);
                    custom_Toast(v);
                    break;
                case R.id.button_3:
                    String srcimage_3 = (String) view_3.getTag();
                    flag_answer = srcimage_3.equals(mSubQuest.content.get(i).correct_answer);
                    custom_Toast(v);
                    break;
                case R.id.button_4:
                    String srcimage_4 = (String) view_4.getTag();
                    flag_answer = srcimage_4.equals(mSubQuest.content.get(i).correct_answer);
                    custom_Toast(v);
                    break;
                case R.id.next:
                    i++;
                    onChangeQuestion();
                    lock_Button(true);
            }
    }

    public void init(View v) {
        view_1 = (ImageView) v.findViewById(R.id.button_1);
        view_2 = (ImageView) v.findViewById(R.id.button_2);
        view_3 = (ImageView) v.findViewById(R.id.button_3);
        view_4 = (ImageView) v.findViewById(R.id.button_4);
        Button next = (Button) v.findViewById(R.id.next);
        scoreTextView = (TextView) v.findViewById(R.id.scoreTextView);
        textViewTestWord = (TextView) v.findViewById(R.id.textViewTestWord);
        view_1.setOnClickListener(this);
        view_2.setOnClickListener(this);
        view_3.setOnClickListener(this);
        view_4.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    public void custom_Toast(View toast) {
        String[] correct_answer = getResources().getStringArray(R.array.list_correct_answer);
        String[] incorrect_answer = getResources().getStringArray(R.array.list_incorrect_answer);
        List correct_answer_list = Arrays.asList(correct_answer);
        List incorrect_answer_list = Arrays.asList(incorrect_answer);
        Collections.shuffle(correct_answer_list);
        Collections.shuffle(incorrect_answer_list);
        Random random = new Random();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                (ViewGroup) toast.findViewById(R.id.toast_layout));
        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        if (flag_answer) {
            lock_Button(false);
            scoreTextView.setText("" + ++score);
            text.setText(String.valueOf(correct_answer_list.get(random.nextInt(5))));
        } else {
            text.setText(String.valueOf(incorrect_answer_list.get(random.nextInt(5))));
        }
        Toast tost = new Toast(getActivity().getApplicationContext());
        tost.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        tost.setDuration(Toast.LENGTH_SHORT);
        tost.setView(layout);
        tost.show();
    }

    public void lock_Button(boolean type_lock) {
        view_1.setClickable(type_lock);
        view_2.setClickable(type_lock);
        view_3.setClickable(type_lock);
        view_4.setClickable(type_lock);
    }

    public void iteration_Change_Question(Uri imgUri, ImageView view) {
        String stream = imgUri.getPath().substring("/android_asset/".length());
        String add = "assets://" + stream;
        ImageLoader.getInstance().displayImage(add, view);
    }
}
