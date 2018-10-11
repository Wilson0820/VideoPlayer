package com.fxc.myvideoplayer.FolderList;

public class FolderItems {
private String folder_cat_name;
private String video_number;
private String first_Video_Path;

    public FolderItems( String folder_cat_name, String video_number,String first_Video_Path) {
        this.first_Video_Path = first_Video_Path;
        this.video_number = video_number;
        this.folder_cat_name = folder_cat_name;
    }

    public String get_video_number() { return video_number; }

    public String get_folder_cat_name() { return folder_cat_name; }

    public String get_first_Video_Path() { return first_Video_Path; }

}
