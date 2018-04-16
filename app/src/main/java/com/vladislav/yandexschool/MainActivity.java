package com.vladislav.yandexschool;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKPhotoSizes;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS};
    ImageView imageView;
    GridView gridView;
    GridViewAdapter adapter;
    ArrayList<GridItem> dataGrid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VKSdk.login(this, scope);

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
                        VKPhotoArray list = (VKPhotoArray) response.parsedModel;
                        dataGrid = new ArrayList<>();
                        int i=0;
                        for (VKApiPhoto photo:
                             list) {
                            GridItem item = new GridItem();
                            if (photo.photo_2560!=null)
                                item.setImage(photo.photo_2560);
                            else if (photo.photo_1280!=null)
                                item.setImage(photo.photo_1280);
                            else if (photo.photo_807!=null)
                                item.setImage(photo.photo_807);
                            else if (photo.photo_604!=null)
                                item.setImage(photo.photo_604);
                            else if (photo.photo_130!=null)
                                item.setImage(photo.photo_130);
                            else if (photo.photo_75!=null)
                                item.setImage(photo.photo_75);
                            if (!item.getImage().equals(""))
                            dataGrid.add(item);
                            System.out.println(item.getImage() + " image src " + i);
                            i++;
                        }
                        System.out.println(dataGrid.size());
                        adapter = new GridViewAdapter(MainActivity.this, R.layout.grid_item_layout, dataGrid);




                        gridView.setAdapter(adapter);


                       // VKApiPhoto photo = list.get(0);

                        //imageView = findViewById(R.id.image_view);
                       // Picasso.get().load(photo.photo_604).into(imageView);

                            //Picasso.get().load(photo.photo_604).into();
                        //}
                        Toast.makeText(MainActivity.this, "Кол-во фоток: "+ list.size(), Toast.LENGTH_SHORT).show();

//                        for (VKApiPhoto photo:
//                             list) {
//
//                        }


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
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
