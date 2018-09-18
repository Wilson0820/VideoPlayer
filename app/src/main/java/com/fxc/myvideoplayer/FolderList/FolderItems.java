package com.fxc.myvideoplayer.FolderList;

public class FolderItems {
private String folder_cat_name;
private String video_number;
    private int image_Resource;

    public FolderItems(String folder_cat_name){
        this.folder_cat_name = folder_cat_name;
    }
    public FolderItems(String folder_cat_name,String video_number){
        this.folder_cat_name = folder_cat_name;
        this.video_number = video_number;
    }
    public FolderItems(int image_Resource, String folder_cat_name, String video_number) {
        this.image_Resource = image_Resource;
        this.video_number = video_number;
        this.folder_cat_name = folder_cat_name;
    }
    public int get_image_Resource() { return image_Resource; }
    public String get_video_number() { return video_number; }
    public String get_folder_cat_name() { return folder_cat_name; }

}
