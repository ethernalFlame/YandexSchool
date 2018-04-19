package com.vladislav.yandexschool;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vladi on 20.04.2018.
 */

public class YourSavedPicsFragment extends PageFragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_saved_pics, container, false);
        return view;
    }
}
