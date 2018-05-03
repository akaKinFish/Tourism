package com.example.rog.tourism.ui.fragment;
/**
 *
 * 查公交地铁的fragment
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rog.tourism.R;

public class SubwayFragment extends Fragment {

    public static SubwayFragment newInstance() {

        SubwayFragment fragment = new SubwayFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subway, container, false);
    }
}
