package musicapp.khangit.music_app.activity;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Build;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import musicapp.khangit.music_app.R;
import musicapp.khangit.music_app.adapter.ElementListAdapter;
import musicapp.khangit.music_app.data.ListSong;
import musicapp.khangit.music_app.element.AlbumElement;
import musicapp.khangit.music_app.element.BaseListElement;
import musicapp.khangit.music_app.element.SongElement;

public class HomeActivity extends AppCompatActivity {
    ListView listView;
    List<BaseListElement> offlineListElement;
    ElementListAdapter elementListAdapter;
    View listHeader, listFooter;
    android.support.v4.widget.SimpleCursorAdapter suggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView = (ListView) findViewById(R.id.list_element);
        final String[] from = new String[]{"songName"};
        final int[] to = new int[]{android.R.id.text1};
        suggestionAdapter = new android.support.v4.widget.SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        offlineListElement = new ArrayList<>();
        addElement();
        listView.setAdapter(elementListAdapter);
        setupHeaderAndFooter();
    }

    private void setupHeaderAndFooter() {
        listHeader = View.inflate(this, R.layout.home_list_header, null);
        listFooter = View.inflate(this, R.layout.home_list_footer, null);
        listView.addHeaderView(listHeader);
        listView.addFooterView(listFooter);
    }

    private void addElement() {
        offlineListElement.add(new SongElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new SongElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new SongElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        offlineListElement.add(new AlbumElement((this)));
        elementListAdapter = new ElementListAdapter(this, offlineListElement);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSuggestionsAdapter(suggestionAdapter);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSuggestion(newText);
                return true;
            }
        });
        return true;

    }
    private void getSuggestion(String text){
        MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "songName"});
        for(int i =0; i< ListSong.getListSong().size(); i++) {
            if(ListSong.getListSong().get(i).getSongName().toLowerCase().contains(text.toLowerCase())){
                c.addRow(new Object[]{i, ListSong.getListSong().get(i).getSongName()});
            }
        }
        suggestionAdapter.changeCursor(c);
    }

}
