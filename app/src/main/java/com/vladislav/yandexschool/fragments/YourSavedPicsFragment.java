package com.vladislav.yandexschool.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.vladislav.yandexschool.GridViewAdapter;
import com.vladislav.yandexschool.MainActivity;
import com.vladislav.yandexschool.R;

/**
 * Created by vladi on 20.04.2018.
 */

public class YourSavedPicsFragment extends PageFragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(int page) {
        YourSavedPicsFragment fragment;
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment = new YourSavedPicsFragment();
        fragment.setArguments(args);
        System.out.println(page + "page number");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
        System.out.println("sozdanie");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_saved_pics, container, false);
      //  MainActivity.initPhotos(getContext());
        System.out.println((getContext() == null) + " ой а как же так");
        GridViewAdapter adapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, MainActivity.getDataGrid());
        GridView gridView = (GridView) view;
        if (gridView!=null)
        gridView.setAdapter(adapter);
        return view;
    }
}
