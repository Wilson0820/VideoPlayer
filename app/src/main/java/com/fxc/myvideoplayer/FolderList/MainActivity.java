package com.fxc.myvideoplayer.FolderList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fxc.myvideoplayer.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<FolderItems> folders = FolderFactory.createFolders(4);
        FolderAdapter adapter = new FolderAdapter(this,folders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new FolderItemDecoration(2));

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
