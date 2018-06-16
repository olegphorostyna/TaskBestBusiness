package com.bestbusiness.android.task;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by Fervill on 14.06.2018.
 */

public class MainFragment extends Fragment {
    private static final String EXTRA_FRAGMENT_COUNT = "com.bestbusiness.android.task.count";
    private TextView mTextView;
    private Animation animation;

    public static MainFragment newInstance(int count){

        Bundle args = new Bundle();
        args.putInt(EXTRA_FRAGMENT_COUNT,count);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout,container,false);
        mTextView =(TextView) v.findViewById(R.id.fragment_content);
        mTextView.setText(""+getArguments().getInt(EXTRA_FRAGMENT_COUNT));
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTextView.startAnimation(animation);
        animation.setFillAfter(true);
    }
}
