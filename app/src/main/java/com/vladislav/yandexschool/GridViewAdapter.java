package com.vladislav.yandexschool;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vladi on 16.04.2018.
 */

public class GridViewAdapter extends ArrayAdapter<GridItem> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     * @param mGridData
     */
    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView =  row.findViewById(R.id.iv_Grid);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        Picasso.get().load(item.getImage()).noFade().into(holder.imageView);
        final MainActivity main = (MainActivity) mContext;

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) mContext).setContentView(R.layout.picture_layout);
                LinearLayout layout = main.findViewById(R.id.picture_layout);
                ImageView tmp = main.findViewById(R.id.picture_full);
                main.findViewById(R.id.picture_layout).setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        System.out.println("ПРИВЕТ " + i + " " + keyEvent.getKeyCode());
                        if (i == KeyEvent.KEYCODE_BACK) {
                            ((MainActivity) mContext).setContentView(R.layout.activity_main);
                            return true;
                        }return false;
                    }
                });
                Picasso.get().load(mGridData.get(position).getImage()).noFade().into(tmp);

            }
        });
        return row;
    }
    static class ViewHolder {
        ImageView imageView;
    }
}
