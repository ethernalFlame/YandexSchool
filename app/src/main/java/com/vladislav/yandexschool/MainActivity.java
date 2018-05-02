package com.vladislav.yandexschool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKPhotoArray;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    static GridView gridView, savedGridView;
    static ArrayList<GridItem> dataGrid, savedGrid;
    private String filename, path;
    private File tmpFile;
    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS};
    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    File file = new File(Environment.getExternalStorageDirectory()
                            .getPath() + filename);
                    file.getParentFile().mkdirs();
                    System.out.println(file.getAbsolutePath());
                    try
                    {
                        System.out.println(file.exists() + "exists");
                        file.createNewFile();
                        path = file.getAbsolutePath();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.close();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (placeHolderDrawable != null) {
            }
        }
    };
    public final static String PICTURE = "PICTURE";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        VKSdk.login(this, scope);

    }

    public static ArrayList<GridItem> getSaved(){
        return savedGrid;
    }

    public static ArrayList<GridItem> getDataGrid(){
        return dataGrid;
    }

    public static GridView getGridView(){
        return gridView;
    }

    public static void setGridView(GridView gridView) {
        MainActivity.gridView = gridView;
    }

    public void savePics(){
        for (int i = 0; i < dataGrid.size(); i++) {
            filename = dataGrid.get(i) + ".jpg";
            //filename = dataGrid.get(i).getImage();
            GridItem tmp = new GridItem();
            tmp.setImage(path);
           // tmp.setImage(dataGrid.get(i).getImage());
            savedGrid.add(tmp);
           // System.out.println(path);
            Picasso.get().load(dataGrid.get(i).getImage()).into(target);

        }
    }

    public static GridView getSavedGridView() {
        return savedGridView;
    }

    public static void setSavedGridView(GridView savedGridView) {
        MainActivity.savedGridView = savedGridView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKAccessToken token = VKAccessToken.currentToken();
                final VKRequest request = new VKRequest("photos.get", VKParameters.from(VKApiConst.ALBUM_ID, "saved",
                        VKApiConst.USER_ID, token.userId), VKPhotoArray.class);
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);
                        gridView = findViewById(R.id.grid_view);
                        savedGridView = findViewById(R.id.grid_view);
                        VKPhotoArray list = (VKPhotoArray) response.parsedModel;
                        dataGrid = new ArrayList<>(list.size());
                        savedGrid = new ArrayList<>(list.size());
                        for (VKApiPhoto photo :
                                list) {
                            GridItem item = new GridItem();
                            if (photo.src.size() != 0) {
                                if (photo.src.getByType('w') != null)
                                    item.setImage(photo.src.getByType('w'));
                                else if (photo.src.getByType('z') != null)
                                    item.setImage(photo.src.getByType('z'));
                                else if (photo.src.getByType('y') != null)
                                    item.setImage(photo.src.getByType('y'));
                                else if (photo.src.getByType('r') != null)
                                    item.setImage(photo.src.getByType('r'));
                                else if (photo.src.getByType('q') != null)
                                    item.setImage(photo.src.getByType('q'));
                                else if (photo.src.getByType('p') != null)
                                    item.setImage(photo.src.getByType('p'));
                                else if (photo.src.getByType('o') != null)
                                    item.setImage(photo.src.getByType('o'));
                                else if (photo.src.getByType('x') != null)
                                    item.setImage(photo.src.getByType('x'));
                                else if (photo.src.getByType('m') != null)
                                    item.setImage(photo.src.getByType('m'));
                                else if (photo.src.getByType('s') != null)
                                    item.setImage(photo.src.getByType('s'));
                            }

                            if (!item.getImage().equals(""))
                                dataGrid.add(item);
                        }
                        viewPager = findViewById(R.id.view_pager);
                        viewPager.setAdapter(
                                new SampleFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));
                        TabLayout tabLayout = findViewById(R.id.tab_layout);
                        tabLayout.setupWithViewPager(viewPager);
                        Toast.makeText(MainActivity.this, "Кол-во фоток: " + list.size(), Toast.LENGTH_SHORT).show();
                        savePics();
                    }

                    @Override
                    public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                        super.attemptFailed(request, attemptNumber, totalAttempts);
                    }

                    @Override
                    public void onError(VKError error) {
                        super.onError(error);
                    }

                    @Override
                    public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                        super.onProgress(progressType, bytesLoaded, bytesTotal);
                    }
                });
            }


            @Override
            public void onError(VKError error) {
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            System.out.println("privet");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}