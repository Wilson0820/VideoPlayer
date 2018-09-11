package com.fxc.myvideoplayer.VideoList;

public class VideoItems {
    private String video_name;
    private long video_duration;
    private int image_Resource;
    private String video_path;

    public VideoItems(String video_name, long video_duration,String video_path){
        this.video_name = video_name;
        this.video_duration = video_duration;
        this.video_path = video_path;
    }

    public VideoItems(int image_Resource, String video_name, long video_duration) {
        this.image_Resource = image_Resource;
        this.video_name = video_name;
        this.video_duration = video_duration;
    }
    public int get_image_Resource() { return image_Resource; }
    public String get_video_name() { return video_name; }
    public long get_video_duration() { return video_duration; }
    public String get_video_path() { return video_path; }
}
