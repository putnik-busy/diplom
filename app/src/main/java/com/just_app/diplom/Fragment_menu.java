package com.just_app.diplom;


import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;


public class Fragment_menu extends Fragment {

    public static final String EXTRA_ITEM =
            "com.just_app.diplom.item1";
    private ImageView nameImage;
    private Subject mSubject;
    private Drawable drawableView;
    private Button forward, back;
    private TextView tv;
    private String mName;
    private MediaPlayer mp;
    private int i = 0;
    private AssetManager mgr;
    private ObjectMapper mapper;
    private String mItem;

    public static Fragment_menu newInstance(String item) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ITEM, item);
        Fragment_menu fragment_menu = new Fragment_menu();
        fragment_menu.setArguments(args);
        return fragment_menu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = (String) getArguments().
                getSerializable(EXTRA_ITEM);
        mgr = getActivity().getAssets();
        mapper = new ObjectMapper();
        try {
            InputStream inputStream;
            inputStream = mgr.open(mItem);
            mSubject = mapper.readValue(inputStream, Subject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);
        mp = new MediaPlayer();
        forward = (Button) v.findViewById(R.id.forward);
        back = (Button) v.findViewById(R.id.back);
        nameImage = (ImageView) v.findViewById(R.id.name_image);
        tv = (TextView) v.findViewById(R.id.tv);

        OnChangePhoto(i);

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.forward:
                        mp.reset();
                        if (i < mSubject.content.size() - 1) {
                            i++;
                            OnChangePhoto(i);

                        } else {
                            i = 0;
                            OnChangePhoto(i);
                        }

                        if (i == mSubject.content.size() - 1) {
                            mp.reset();
                            OnChangePhoto(i);
                        }
                        break;
                    case R.id.back:
                        if (i == 0) {
                            mp.reset();
                            i = mSubject.content.size() - 1;
                            OnChangePhoto(i);
                        } else if (i < mSubject.content.size()) {
                            i--;
                            mp.reset();
                            OnChangePhoto(i);
                        }
                        break;
                }
            }
        };

        forward.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        return v;
    }

    public void OnChangePhoto(final int i) {

        AssetManager mgr = getActivity().getAssets();
        if (mSubject.content != null) {
            try {
                Uri imgUri = Uri.parse(mSubject.content.get(i).photos);
                Uri soundUri = Uri.parse(mSubject.content.get(i).sounds);
                mName = mSubject.content.get(i).signature;
                String sound2 = soundUri.getPath().substring("/android_asset/".length());
                InputStream stream = mgr.open(
                        imgUri.getPath().substring("/android_asset/".length())
                );
                drawableView = Drawable.createFromStream(stream, null);

                nameImage.setImageDrawable(drawableView);
                tv.setText(mName);

                if (sound2 != null) {

                    AssetFileDescriptor afd = getActivity().getAssets().openFd(sound2);
                    mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    afd.close();
                    mp.prepare();
                    mp.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("sound error", "sound error");
            }
        }
    }

}
