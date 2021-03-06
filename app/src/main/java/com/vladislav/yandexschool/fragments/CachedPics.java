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
 * Created by vladi on 02.05.2018.
 */
// Недоработанная фича
public class CachedPics extends PageFragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private GridView gridView;

    public static PageFragment newInstance(int page) {
        CachedPics fragment;
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment = new CachedPics();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cached_pics, container, false);
        GridViewAdapter adapter = new GridViewAdapter(getContext(), R.layout.grid_item_layout, MainActivity.getSaved());
        gridView = (GridView) view;
        MainActivity.setSavedGridView(gridView);
        if (gridView!=null)
            gridView.setAdapter(adapter);
        return view;
    }

    public GridView getGridView() {
        return gridView;
    }
}
