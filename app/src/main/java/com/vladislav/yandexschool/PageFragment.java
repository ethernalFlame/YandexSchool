package com.vladislav.yandexschool;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vladi on 19.04.2018.
 */

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(int page) {
        PageFragment fragment;
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        if (page==2)
            fragment = new YourSavedPicsFragment();
        else
            fragment = new PageFragment();
        fragment.setArguments(args);
        System.out.println(page + "page number");
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView textView = (TextView) view;
        textView.setText("Fragment #" + mPage);
        return view;
    }
}
