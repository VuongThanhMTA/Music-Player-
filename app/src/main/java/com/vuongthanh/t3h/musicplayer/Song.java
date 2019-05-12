package com.vuongthanh.t3h.musicplayer;

public class Song {
    private int idSong;
    private String data;
    private long size;
    private long duration;
    private String name;
    private String album;
    private String artist;
    private int idAlbum;
    private String image;

    public int getIdSong() {
        return idSong;
    }

    public void setIdSong(int idSong) {
        this.idSong = idSong;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Song(String data, long size, long duration, String name, String album, String artist, int idAlbum) {
        this.data = data;
        this.size = size;
        this.duration = duration;
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.idAlbum = idAlbum;
    }

    public Song() {

    }
    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
