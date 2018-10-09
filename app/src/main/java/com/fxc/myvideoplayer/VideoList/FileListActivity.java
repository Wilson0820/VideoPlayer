package com.fxc.myvideoplayer.VideoList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.fxc.myvideoplayer.FolderList.FolderItemDecoration;
import com.fxc.myvideoplayer.R;

import java.util.ArrayList;
import java.util.List;

public class FileListActivity extends AppCompatActivity {
    ActionBar actionBar;
    RecyclerView videoRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    VideoAdapter adapter;
    List<VideoItems> videos = new ArrayList<>();
    ArrayList<String> pathlist = new ArrayList<String>();
    String folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        actionBar = getSupportActionBar();
        folderName = getIntent().getStringExtra("folder_cat_name");
        actionBar.setTitle(folderName);

        adapter = new VideoAdapter(this, videos, pathlist);
        //设置为list
        //layoutManager = new LinearLayoutManager(this);
        // 设置为grid显示
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL
        );
        videoRecyclerView = findViewById(R.id.rv_video);
        videoRecyclerView.setAdapter(adapter);
        videoRecyclerView.setLayoutManager(layoutManager);
        videoRecyclerView.addItemDecoration(new FolderItemDecoration(2));
        // 加载sd卡当中的视频
        loadData();
    }

    private void loadData() {
        List<VideoItems> list = new ArrayList<>();
//        1.获取ContentResolver对象
        ContentResolver resolver = getContentResolver();
//        2.获取Uri地址
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//        3.开始查询系统视频数据库
        Cursor cursor = resolver.query(videoUri, null, null, null, MediaStore.Video.Media.DISPLAY_NAME);
//      4.移动cursor指针
        if (cursor == null) {
            Toast.makeText(this, "没有找到可播放视频文件", Toast.LENGTH_LONG).show();
            return;
        }
        while (cursor.moveToNext()) {

            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            String folderNameSub = getFolderName(path);
            if (folderNameSub.equals(folderName)) {
                String video_name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                String video_path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                // String video_album = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ALBUM));
                long video_duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                //long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                VideoItems videoItem = new VideoItems(video_name, video_duration, video_path);
                list.add(videoItem);
                pathlist.add(video_path);
            }
        }
        videos.addAll(list);
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    public String getFolderName(String path) {
        String[] path1 = path.split("/");
        String folderName = path1[path1.length - 2];
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
            if (videoRecyclerView.getLayoutManager() instanceof LinearLayoutManager){
                item.setIcon(R.drawable.ic_list);
                layoutManager = new StaggeredGridLayoutManager(
                        2, StaggeredGridLayoutManager.VERTICAL);
                videoRecyclerView.setLayoutManager(layoutManager);
                videoRecyclerView.setAdapter(new VideoAdapter(this, videos, pathlist));
                adapter.notifyDataSetChanged();
                return true;
            } else {
                item.setIcon(R.drawable.ic_grid);
                layoutManager = new LinearLayoutManager(FileListActivity.this);
                videoRecyclerView.setLayoutManager(layoutManager);
                videoRecyclerView.setAdapter(new VideoAdapterLinear(this, videos, pathlist));
                adapter.notifyDataSetChanged();
                return true;
            }

        }
        return true;
        }

    }

