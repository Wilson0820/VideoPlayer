package com.fxc.myvideoplayer.FolderList;

import com.fxc.myvideoplayer.R;

import java.util.ArrayList;
import java.util.List;

public class FolderFactory {

    private static int[] image_Resources = {
            R.drawable.item1,R.drawable.item2,R.drawable.item3,R.drawable.item4
    };
    private static String[] folder_cat_names =
            {"football", "basketball", "movies","others"};

    private static String[] folder_numbers =
            {"10", "11", "12","13"};

    public static List<FolderItems> createFolders(int num) {

        List<FolderItems> folders = new ArrayList<>();
        int arySize = image_Resources.length;
        for (int i = 0; i < num; i++) {
            int a = i % arySize;
            int image_Resource = image_Resources[a];
            String folder_cat_name = folder_cat_names[a];
            String folder_number = folder_numbers[a];

            folders.add(new FolderItems(image_Resource, folder_cat_name, folder_number));
        }
        return folders;
    }

}