package com.vuongthanh.t3h.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

public class MyMediaStore {

    private ContentResolver resolver;


    public MyMediaStore(Context context) {
        this.resolver = context.getContentResolver();
    }

    public void getDataInternal() {
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.e(cursor.getColumnName(i), cursor.getString(i) + "");
            }
            Log.e("===========", "===========");
            cursor.moveToNext();
        }
    }

    public ArrayList<Song> getSongByAlbum(int albumId) {
        String selection = MediaStore.Audio.Media.ALBUM_ID + "=?";
        String[] selctionAgr = {albumId + ""};
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, selection, selctionAgr, null);

        return getDataMedia(cursor);

    }

    public ArrayList<Song> getSongByArtist(int artistId) {
        String selection = MediaStore.Audio.Media.ARTIST_ID + "=?";
        String[] selctionAgr = {artistId + ""};
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, selection, selctionAgr, null);

        return getDataMedia(cursor);

    }

    public ArrayList<Song> getAllSong() {
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, null);
        return getDataMedia(cursor);
    }


    public ArrayList<Song> getDataMedia(Cursor cursor) {
        ArrayList<Song> arr = new ArrayList<>();
//        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
//                null, null, null);

        cursor.moveToFirst();
        int indexId = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexSize = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int indexIdAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
        while (cursor.isAfterLast() == false) {
            String data = cursor.getString(indexData);
            long size = cursor.getLong(indexSize);
            long duration = cursor.getLong(indexDuration);
            String title = cursor.getString(indexTitle);
            String artist = cursor.getString(indexArtist);
            String album = cursor.getString(indexAlbum);
            int idAlbum = cursor.getInt(indexIdAlbum);
            Song song = new Song(data, size, duration, title, album, artist, idAlbum);
            arr.add(song);
            cursor.moveToNext();
        }
        return arr;
    }

    public ArrayList<Album> getAlbumData() {
        ArrayList<Album> arr = new ArrayList<>();
        Cursor cursor = resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null,
                null, null, null);

        cursor.moveToFirst();


        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
        int indexNumSong = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
        int indexImage = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
        int indexId = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
        while (cursor.isAfterLast() == false) {
            String nameAlbum = cursor.getString(indexAlbum);
            String artist = cursor.getString(indexArtist);
            int numSong = cursor.getInt(indexNumSong);
            String nOfSong = String.valueOf(numSong);
            String image = cursor.getString(indexImage);
            int id = cursor.getInt(indexId + 1);
            Album album1 = new Album(nameAlbum, artist, nOfSong, image, id);
            arr.add(album1);
            cursor.moveToNext();
        }
        return arr;
    }

    public ArrayList<Artist> getArtistData() {
        ArrayList<Artist> arr = new ArrayList<>();
        Cursor cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null,
                null, null, null);

        cursor.moveToFirst();

        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
        int indexNumSong = cursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
        int indexIdArtist = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
        while (cursor.isAfterLast() == false) {
            int nOfAlbum = cursor.getInt(indexAlbum);
            String nameArtist = cursor.getString(indexArtist);
            int numSong = cursor.getInt(indexNumSong);
            int id = cursor.getInt(indexIdArtist);
            Artist artist = new Artist(nameArtist, String.valueOf(numSong), String.valueOf(nOfAlbum), id);
            arr.add(artist);
            cursor.moveToNext();
        }
        return arr;
    }
}
