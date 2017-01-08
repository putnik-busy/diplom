package com.just_app.obuchaika.ui.fragment;

import android.app.Fragment;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.crash.FirebaseCrash;
import com.just_app.obuchaika.utils.CustomViewAware;
import com.just_app.obuchaika.model.Subject;
import com.just_app.obuchaika.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import static android.view.GestureDetector.OnGestureListener;
import static android.widget.ViewSwitcher.OnTouchListener;
import static android.widget.ViewSwitcher.ViewFactory;


public class Fragment_menu extends Fragment implements ViewFactory,
        OnGestureListener, OnTouchListener {

    public static final String EXTRA_ITEM =
            "com.just_app.diplom.item1";
    public static final String EXTRA_STATE_KEY =
            "com.just_app.diplom.state";
    private Subject mSubject;
    private TextView tv;
    private MediaPlayer mp;
    private int position = 0;
    private AssetManager mgr;
    private String mItem;
    Animation in, out;
    private ImageSwitcher mImageSwitcher;
    private GestureDetector mGestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 40;


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
        setRetainInstance(true);
        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_STATE_KEY))
            position = savedInstanceState.getInt(EXTRA_STATE_KEY);
        mItem = (String) getArguments().
                getSerializable(EXTRA_ITEM);
        LoadMediaTask loadMediaTask = new LoadMediaTask();
        loadMediaTask.execute(mItem);
        try {
            mSubject = loadMediaTask.get();
        } catch (InterruptedException | ExecutionException e) {
            FirebaseCrash.report(e);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_STATE_KEY, position);
    }

    class LoadMediaTask extends AsyncTask<String, String, Subject> {

        @Override
        protected Subject doInBackground(String... params) {
            mgr = getActivity().getAssets();
            ObjectMapper mapper = new ObjectMapper();
            try {
                InputStream inputStream;
                inputStream = mgr.open(mItem);
                mSubject = mapper.readValue(inputStream, Subject.class);
                inputStream.close();
            } catch (IOException e) {
                FirebaseCrash.report(e);
            }
            return mSubject;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(
                R.layout.fragment_menu, container, false);
        mp = new MediaPlayer();
        mGestureDetector = new GestureDetector(getActivity(), this);
        ImageButton forward = (ImageButton) v.findViewById(R.id.forward);
        ImageButton back = (ImageButton) v.findViewById(R.id.back);
        tv = (TextView) v.findViewById(R.id.tv);
        mImageSwitcher = (ImageSwitcher) v.findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(this);
        mImageSwitcher.setOnTouchListener(this);

        in = AnimationUtils.loadAnimation(getActivity(), R.anim.left_slide);
        out = AnimationUtils.loadAnimation(getActivity(), R.anim.right_slide);

        OnChangePhoto();

        View.OnClickListener onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.forward:

                        setPositionNext();
                        OnChangePhoto();
                        break;
                    case R.id.back:
                        setPositionPrev();
                        OnChangePhoto();
                        break;
                }
            }
        };

        forward.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
        return v;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public void OnChangePhoto() {
        mp.reset();
        mgr = getActivity().getAssets();
        if (mSubject.content != null) {
            try {
                Uri imgUri = Uri.parse(mSubject.content.get(position).photos);
                iteration_Change_Question(imgUri,mImageSwitcher);

                String mName = mSubject.content.get(position).signature;
                tv.setText(mName);

                Uri soundUri = Uri.parse(mSubject.content.get(position).sounds);
                String sound2 = soundUri.getPath().substring("/android_asset/".length());

                AssetFileDescriptor afd = getActivity().getAssets().openFd(sound2);
                mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mp.prepare();
                mp.start();
            } catch (IOException e) {
                FirebaseCrash.report(e);
            }
        }
    }

    @Override
    public View makeView() {
       ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
        return imageView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                setPositionNext();
                OnChangePhoto();
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                setPositionPrev();
                OnChangePhoto();
            }
        } catch (Exception e) {
            FirebaseCrash.report(e);
            return true;
        }
        return true;
    }

    public void setPositionNext() {
        position = (position + 1) % mSubject.content.size();
        mImageSwitcher.startAnimation(in);
    }

    public void setPositionPrev() {
        position = (position - 1) % mSubject.content.size();
        if (position < 0)
            position = mSubject.content.size() - 1;
        mImageSwitcher.startAnimation(out);
    }

    public void iteration_Change_Question(Uri imgUri, ImageSwitcher view) {
        String stream = imgUri.getPath().substring("/android_asset/".length());
        String add = "assets://" + stream;
        ImageLoader.getInstance().displayImage(add, new CustomViewAware(view,getActivity()));
    }
}