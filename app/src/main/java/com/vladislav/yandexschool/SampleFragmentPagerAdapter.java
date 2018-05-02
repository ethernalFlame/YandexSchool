package com.vladislav.yandexschool;



import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vladislav.yandexschool.fragments.CachedPics;
import com.vladislav.yandexschool.fragments.PageFragment;
import com.vladislav.yandexschool.fragments.YourSavedPicsFragment;

/**
 * Created by vladi on 19.04.2018.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Сохраненные картинки", "Tab3" };
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override public int getCount() {
        return PAGE_COUNT;
    }

    @Override public Fragment getItem(int position) {
        if (position==1)
            return YourSavedPicsFragment.newInstance(position+1);
        else if (position==2)
            return CachedPics.newInstance(position+1);
        return PageFragment.newInstance(position + 1);
    }

    @Override public CharSequence getPageTitle(int position) {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position];
    }
}
