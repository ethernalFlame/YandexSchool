package com.vladislav.yandexschool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

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
//                main.setPreviousLayout(R.layout.activity_main);
//                main.setContentView(R.layout.picture_layout);
//                ImageView tmp = main.findViewById(R.id.picture_full);
//                main.getSupportActionBar().hide();
//                main.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                Picasso.get().load(mGridData.get(position).getImage()).noFade().into(tmp);
//                createListener(tmp, main);
                Intent intent = new Intent(main, FullPicActivity.class);
                intent.putExtra("PICTURE", mGridData.get(position).getImage());
                main.startActivity(intent);
            }
        });
        return row;
    }

    private void createListener(ImageView view, AppCompatActivity activity){
        view.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;
            @Override
            public void onClick(View view) {
                if(!flag){
                    activity.getSupportActionBar().show();
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                else {
                    activity.getSupportActionBar().hide();
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                flag = !flag;
            }
        });
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
