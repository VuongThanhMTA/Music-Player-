package com.vuongthanh.t3h.musicplayer;

public class Artist {
    private String nameArtist;
    private String numSong;
    private String numAlbum;
    private int idArtist;


    public Artist(String nameArtist, String numSong, String numAlbum, int idArtist) {
        this.nameArtist = nameArtist;
        this.idArtist = idArtist;
        this.numSong = numSong + " Bài hát";
        this.numAlbum = numAlbum + " Albums";
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }
    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getNumSong() {
        return numSong;
    }

    public void setNumSong(String numSong) {
        this.numSong = numSong;
    }

    public String getNumAlbum() {
        return numAlbum;
    }

    public void setNumAlbum(String numAlbum) {
        this.numAlbum = numAlbum;
    }
}
