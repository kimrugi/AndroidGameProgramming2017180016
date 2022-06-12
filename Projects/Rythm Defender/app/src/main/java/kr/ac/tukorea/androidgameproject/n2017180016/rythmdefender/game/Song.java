package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song {
    private static final String TAG = Song.class.getSimpleName();

    public String toJson() {
        return "{" +
                "\"file\": \"" + mp3File + "\"," +
                "\"title\": \"" + title + "\"," +
                "\"artist\": \"" + artist + "\"," +
                "\"albumArt\": \"" + albumFile + "\"," +
                "\"note\": \"" + chartFile + "\"" +
                "}";
    }

    public MediaPlayer loadMusic(AssetManager assets) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = assets.openFd(mp3File);
            mediaPlayer.setDataSource(afd);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    public String mp3File;
    public String chartFile;
    public String title;
    public String artist;
    public String albumFile;
    public Bitmap albumBitmap;

    private int current;
    private long startedOn;

    private float length;
    public float getLength() {
        return length;
    }

    public Song(JsonReader reader, AssetManager assets) throws IOException {
        while (reader.hasNext()) {
            String name = reader.nextName();
            //Log.d(TAG, "Reading name: " + name);
            if (name.equals("file")) {
                mp3File = reader.nextString();
            } else if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("artist")) {
                artist = reader.nextString();
            } else if (name.equals("albumArt")) {
                albumFile = reader.nextString();
                albumBitmap = BitmapFactory.decodeStream(assets.open(albumFile));
            } else if (name.equals("note")) {
                chartFile = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
    }

    public void start() {
        current = 0;
        startedOn = System.currentTimeMillis();
    }
}
