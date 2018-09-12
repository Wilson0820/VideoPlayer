package com.fxc.myvideoplayer.FolderList;

public class FolderItems {
private String folder_cat_name;
private String folder_number;
    private int image_Resource;

    public FolderItems(String folder_cat_name){
        this.folder_cat_name = folder_cat_name;
    }

    public FolderItems(int image_Resource, String folder_cat_name, String folder_number) {
        this.image_Resource = image_Resource;
        this.folder_number = folder_number;
        this.folder_cat_name = folder_cat_name;
    }
    public int get_image_Resource() { return image_Resource; }
    public String get_folder_number() { return folder_number; }
    public String get_folder_cat_name() { return folder_cat_name; }

}
