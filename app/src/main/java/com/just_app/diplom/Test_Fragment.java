package com.just_app.diplom;


import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class Test_Fragment extends Fragment {

    public static final String EXTRA_ITEM_TEST =
            "com.just_app.diplom.item";
    private ImageView view_1, view_2, view_3, view_4;
    private TextView textViewTestWord;
    private String mItemQuestions;
    private Subject_Question mSubQuest;
    private ObjectMapper mapper;
    private AssetManager mgr;
    private int i = 0;
    private String mQuestion;
    private Uri imgUri;
    private InputStream stream;

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
            mapper = new ObjectMapper();
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
        view_1 = (ImageView) v.findViewById(R.id.button_1);
        view_2 = (ImageView) v.findViewById(R.id.button_2);
        view_3 = (ImageView) v.findViewById(R.id.button_3);
        view_4 = (ImageView) v.findViewById(R.id.button_4);
        textViewTestWord = (TextView) v.findViewById(R.id.textViewTestWord);

        onChangeQuestion();
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_1:
                        String srcimage= (String)view_1.getTag();
                        if (srcimage.equals(mSubQuest.content.get(i).correct_answer)) {
                            textViewTestWord.setText("верно");
                    }
                        break;
                    case R.id.button_2:
                        String srcimage_2 = (String)view_2.getTag();
                        if (srcimage_2.equals(mSubQuest.content.get(i).correct_answer)) {
                            textViewTestWord.setText("верно");
                        }
                }
            }
        };
        view_1.setOnClickListener(onClickListener);
        return v;
    }

    public void onChangeQuestion() {
        mgr = getActivity().getAssets();
        if (mSubQuest.content != null) {
            try {
                imgUri = Uri.parse(mSubQuest.content.get(i).answer_1);
                view_1.setImageDrawable(getDrawable(imgUri));
                view_1.setTag(mSubQuest.content.get(i).answer_1);

                Uri imgUri_2 = Uri.parse(mSubQuest.content.get(i).answer_2);
                view_2.setImageDrawable(getDrawable(imgUri_2));
                view_2.setTag(mSubQuest.content.get(i).answer_1);

                Uri imgUri_3 = Uri.parse(mSubQuest.content.get(i).answer_3);
                view_3.setImageDrawable(getDrawable(imgUri_3));
                view_3.setTag(mSubQuest.content.get(i).answer_1);

                Uri imgUri_4 = Uri.parse(mSubQuest.content.get(i).answer_4);
                view_4.setImageDrawable(getDrawable(imgUri_4));
                view_4.setTag(mSubQuest.content.get(i).answer_1);

                mQuestion = mSubQuest.content.get(i).question;
                textViewTestWord.setText(mQuestion);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Drawable getDrawable(Uri img) throws IOException {
        InputStream stream = mgr.open(
                img.getPath().substring("/android_asset/".length()));
        Drawable drawableView = Drawable.createFromStream(stream, null);
        stream.close();
        return drawableView;
    }

}
