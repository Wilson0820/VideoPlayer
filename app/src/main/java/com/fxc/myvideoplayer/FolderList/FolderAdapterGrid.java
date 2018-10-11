package com.fxc.myvideoplayer.FolderList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fxc.myvideoplayer.R;
import com.fxc.myvideoplayer.VideoList.FileListActivity;

import java.util.List;


public class FolderAdapterGrid extends RecyclerView.Adapter<FolderAdapterGrid.ViewHolder> {
    Context context;
    List<FolderItems> folders;

    public FolderAdapterGrid(Context context, List<FolderItems> folders) {
        this.context = context;
        this.folders = folders;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.folder_item_grid, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
     final FolderItems folder = folders.get(position);
    //获取视频缩略图，显示缩略图
        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail
                (folder.get_first_Video_Path(), MediaStore.Video.Thumbnails.MINI_KIND);
        holder.img.setImageBitmap(thumbnail);

        holder.folder_name.setText(folder.get_folder_cat_name());
        holder.folder_num.setText(folder.get_video_number());

        holder.folder_item.setOnClickListener(new View.OnClickListener() {
    @Override
        public void onClick(View v) {
       //启动file list activity
        startFileListActivity(folder);
    }
});
    }
    private void startFileListActivity(FolderItems folder){
        Intent intent = new Intent(context,FileListActivity.class);
        intent.putExtra("folder_cat_name",folder.get_folder_cat_name());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return folders.size();
    }
   //内部类
    class ViewHolder extends RecyclerView.ViewHolder{
       ConstraintLayout folder_item;
       TextView folder_name;
       TextView folder_num;
       ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            folder_item = itemView.findViewById(R.id.folder_item);
            folder_name = itemView.findViewById(R.id.folder_cat_name);
            folder_num = itemView.findViewById(R.id.folder_numbers);
            img = itemView.findViewById(R.id.folder_image);
        }
    }
}
