package musicapp.khangit.demosearchview;

import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    android.support.v4.widget.SimpleCursorAdapter suggestionAdapter;
    ArrayList<String>arrName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final String[] from = new String[]{"Name"};
        final int[] to = new int[]{android.R.id.text1};
        suggestionAdapter = new android.support.v4.widget.SimpleCursorAdapter(this,android.R.layout.simple_list_item_1, null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        arrName=new ArrayList<>();
        arrName.add("Nguyễn Hồ Duy Khang");
        arrName.add("Nguyễn Thiện huy");
        arrName.add("Hồ Văn Tiến");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//Khi nhấn Enter
                //  Toast.makeText(MainActivity.this,"Your clicked: "+query,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//Khi người dùng nhập vào khung search
                getSuggestion(newText);
                //  Toast.makeText(MainActivity.this,"Your clicked: "+newText,Toast.LENGTH_LONG).show();
                return true;
            }
        });
        //Lắng nghe Suggestion
      searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
          @Override

          public boolean onSuggestionSelect(int position) {
              Toast.makeText(MainActivity.this,"Posdition: "+""+position,Toast.LENGTH_LONG).show();
              return false;
          }
          //Khi click suggestion
          @Override
          public boolean onSuggestionClick(int position) {
              Toast.makeText(MainActivity.this,"Position: "+""+position,Toast.LENGTH_LONG).show();
              return false;
          }
      });
        return true;
    }

    private void getSuggestion(String text) {
        MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID, "Name"});
        for(int i =0; i< arrName.size(); i++) {
            if(arrName.get(i).toLowerCase().contains(text.toLowerCase())){
                c.addRow(new Object[]{i, arrName.get(i)});
            }
        }
        suggestionAdapter.changeCursor(c);
    }
}
