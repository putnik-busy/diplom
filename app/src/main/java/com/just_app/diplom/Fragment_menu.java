package com.just_app.diplom;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;


public class Fragment_menu extends Fragment {
    private ImageView nameImage;
    private Subject mSubject;
    private Drawable drawableView;
    private Button forward, back;
    private TextView tv;
    private String mName;
    private String mSound;
    private MediaPlayer mp;
    private SoundPool mSoundPool;
    private int mTrack;
    private int i = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AssetManager mgr = getActivity().getAssets();

        ObjectMapper mapper = new ObjectMapper();

        try {
            InputStream inputStream;
            inputStream = mgr.open("numbers.xml");
            mSubject = mapper.readValue(inputStream, Subject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu, container, false);

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
                        if (i < mSubject.content.size() - 1) {
                            i++;
                            OnChangePhoto(i);
                        } else {
                            i = 0;
                            OnChangePhoto(i);
                        }

                        if (i == mSubject.content.size() - 1) {
                            OnChangePhoto(i);
                        }
                        break;
                    case R.id.back:
                        if (i == 0) {
                            i = mSubject.content.size() - 1;
                            OnChangePhoto(i);
                        } else if (i < mSubject.content.size()) {
                            i--;
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

    public void OnChangePhoto(int i) {


        AssetManager mgr = getActivity().getAssets();
        if (mSubject.content != null) {
            try {
                Uri imgUri = Uri.parse(mSubject.content.get(i).photos);
                Uri soundUri = Uri.parse(mSubject.content.get(i).sounds);
                mName = mSubject.content.get(i).signature;
              //  mSound = mSubject.content.get(i).sounds;
                InputStream stream = mgr.open(
                        imgUri.getPath().substring("/android_asset/".length())
                );
                drawableView = Drawable.createFromStream(stream, null);
                nameImage.setImageDrawable(drawableView);
                tv.setText(mName);

                if (mSound != null) {
                    mp = new MediaPlayer();
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mp.setDataSource(getActivity().getApplicationContext(),soundUri);
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
