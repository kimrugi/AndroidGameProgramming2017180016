package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game.Song;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private ArrayList<Song> songs = new ArrayList<>();
    private int selectedPosition = -1;
    class Holder {
        TextView title;
        TextView artist;
        ImageView thumbnail;
        Holder(View view) {
            title = view.findViewById(R.id.title);
            artist = view.findViewById(R.id.artist);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        setContentView(R.layout.activity_main);
        loadSongs();
        listView = findViewById(R.id.listView);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return songs.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Song song = songs.get(i);
                LinearLayout layout = (LinearLayout) view;
                Holder holder;
                if (layout == null) {
                    layout = (LinearLayout) getLayoutInflater().inflate(R.layout.song_item, null);
                    holder = new Holder(layout);
                    layout.setTag(holder);
                } else {
                    holder = (Holder) layout.getTag();
                }
                holder.title.setText(song.title);
                holder.artist.setText(song.artist);
                holder.thumbnail.setImageBitmap(song.albumBitmap);

                return layout;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.setSelected(true);
                selectedPosition = position;
            }
        });
    }

    private void loadSongs() {
        AssetManager assets = getAssets();
        try {
            InputStream is = assets.open("songs.json");
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                Song song = new Song(reader, assets);
                reader.endObject();
                Log.d(TAG, "Bitmap #" + songs.size() + " = " + song.albumBitmap);
                songs.add(song);
            }
            reader.endArray();
            Log.d(TAG, "Loaded " + songs.size() + " songs");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStartButton(View view) {
        Song song = songs.get(selectedPosition);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.MUSIC, song.mp3File);
        intent.putExtra(GameActivity.CHART, song.chartFile);
        intent.putExtra(GameActivity.IMAGE, song.albumFile);
        startActivity(intent);
    }

}