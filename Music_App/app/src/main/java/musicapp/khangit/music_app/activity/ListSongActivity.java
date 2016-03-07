package musicapp.khangit.music_app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import musicapp.khangit.music_app.R;
import musicapp.khangit.music_app.adapter.SongListAdapter;

public class ListSongActivity extends AppCompatActivity {

    ListView listSong;
    SongListAdapter songListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        listSong = (ListView) findViewById(R.id.list_song);

       songListAdapter = new SongListAdapter(this);
        listSong.setAdapter(songListAdapter);
        handleIntent(getIntent());
      /*  listSong.setOnItemClickListener(songListAdapter);

        listSong.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE){
                    ImageLoader.getInstance().resume();
                }else if(scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    ImageLoader.getInstance().stop();
                }else if(scrollState == SCROLL_STATE_FLING){
                    ImageLoader.getInstance().stop();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        handleIntent(getIntent());
        */
    }
    private  void handleIntent(Intent intent){
        String query=intent.getStringExtra(HomeActivity.SONG_NAME);
        Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
    }

   /*  private void handleIntent(Intent intent){
       if(intent.getAction().equals(Intent.ACTION_SEARCH)) {
            String query = intent.getStringExtra(HomeActivity.SONG_NAME);
            for (int i = 0; i < ListSong.getListSong().size(); i++) {
                if (ListSong.getListSong().get(i).getSongName().toLowerCase().contains(query.toLowerCase())) {
                    songListAdapter.addItem(ListSong.getListSong().get(i));
                }
            }
            songListAdapter.notifyDataSetChanged();
        }else if(intent.getAction().equals(SongsElement.OFF_SONG)){
            Cursor c = StorageUtil.getMp3FileCursor(this);
            if(c != null){
                if(c.moveToFirst()){
                    do{
                        songListAdapter.addItem(new Song(
                                c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                                c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                                c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA)),
                                "",
                                ""
                        ));
                    }while(c.moveToNext());
                }
            }
        }
    }
    */
}
