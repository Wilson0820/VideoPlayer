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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fxc.myvideoplayer.R;
import com.fxc.myvideoplayer.VideoList.FileListActivity;
import com.fxc.myvideoplayer.VideoList.VideoItems;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<FolderItems> folderItems = new ArrayList<>();
    FolderAdapter adapter;
    String folderName,lastfolderName;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new FolderAdapter(this,folderItems);
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
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

    private void loadFolder() {
        int count=0;
        List<FolderItems> list = new ArrayList<>();
        ContentResolver resolver = getContentResolver();
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = resolver.query(videoUri, null, null, null, MediaStore.Video.Media.DATA);
        if(cursor==null){
            Toast.makeText(this, "没有找到可播放视频文件", Toast.LENGTH_SHORT).show();
            return;
        }
        FolderItems folderItem;
        String path = new String();
        String firstPath = new String();
        while (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            if (lastfolderName == null) {
                folderName = getFolderName(path);
                lastfolderName = folderName;
                count++;
                firstPath = path;
            }
            else {
                lastfolderName = folderName;
                folderName = getFolderName(path);
                //folderName一樣
                if (folderName.equals(lastfolderName)) {
                    count = count + 1;
                }
                //folderName不一樣
                else {
                    folderItem = new FolderItems(lastfolderName, String.valueOf(count),firstPath);
                    list.add(folderItem);
                    count = 1;
                    firstPath = path;
                }
            }
        }
        if (count ==1)
            firstPath = path;
        folderItem = new FolderItems(folderName, String.valueOf(count),firstPath);
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
        getMenuInflater().inflate(R.menu.layout_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.action_layout_list) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                item.setIcon(R.drawable.ic_list);
                layoutManager = new StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new FolderAdapterGrid(this,folderItems));
                adapter.notifyDataSetChanged();
                return true;
            } else {
                item.setIcon(R.drawable.ic_grid);
                layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(new FolderAdapter(this,folderItems));
                adapter.notifyDataSetChanged();
                return true;
            }

        }
        return true;
    }


}
