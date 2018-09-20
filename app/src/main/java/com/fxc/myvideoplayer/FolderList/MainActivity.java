package com.fxc.myvideoplayer.FolderList;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fxc.myvideoplayer.R;
import com.fxc.myvideoplayer.VideoList.VideoItems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<FolderItems> folderItems = new ArrayList<>();
    FolderAdapter adapter;
    String folderName,lastfolderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //List<FolderItems> folders = FolderFactory.createFolders(4);
        adapter = new FolderAdapter(this,folderItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new FolderItemDecoration(2));
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    5566);
        else
            loadFolder();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"this is menu!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 5566:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    loadFolder();
                break;
        }
    }

    //
    private void loadFolder() {
        int count=0;
        List<FolderItems> list = new ArrayList<>();

//        1.获取ContentResolver对象
        ContentResolver resolver = getContentResolver();
//        2.获取Uri地址
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//        3.开始查询系统视频数据库
        Cursor cursor = resolver.query(videoUri, null, null, null, MediaStore.Video.Media.DATA);
//      4.移动cursor指针
        if(cursor==null){
            Toast.makeText(this, "没有找到可播放视频文件", Toast.LENGTH_SHORT).show();
            return;
        }
        FolderItems folderItem;
        while (cursor.moveToNext()) {
            //TODO

            //第一個item
            if (lastfolderName == null) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                folderName = getFolderName(path);
                lastfolderName = folderName;
                count++;
            }
            else {
                lastfolderName = folderName;
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Log.i("joy","path" +path);
               // String folder_path =
                folderName = getFolderName(path);
                //folderName一樣
                if (folderName.equals(lastfolderName)) {
                    count = count + 1;
                }
                //folderName不一樣
                else {
                    //TODO
                    folderItem = new FolderItems(lastfolderName, String.valueOf(count));
                    list.add(folderItem);
                    count = 1;
                }
            }

        }


            folderItem = new FolderItems(folderName, String.valueOf(count));
            list.add(folderItem);

        folderItems.addAll(list);
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    public String getFolderName(String path){
        String[] path1= path.split("/");
        String folderName = path1[path1.length-2];
        return folderName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();

            //todo complete setting function in here

            //Seamas-----------------------------------------------------------
           /* Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            String[] projections = {
                    MediaStore.Video.Media.ALBUM
            };
            String order = MediaStore.Video.Media.DATE_MODIFIED + " DESC";
            if (ActivityCompat.checkSelfPermission(
                    MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        5566);
            try {
                Cursor imageCursor = getContentResolver().query(
                        uri,
                        projections,
                        null,
                        null,
                        order
                );
                if (imageCursor != null) {
                    while (imageCursor.moveToNext()) {
                        Log.d("Seamas", "album : " + imageCursor.getString(0));
                    }
                    imageCursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            //-----------------------------------------------------------


            return true;
        } else if (id == R.id.action_search) {
            Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
