package khangit96.demoreclyerviewcardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> itemArrayList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initItemArrayList();

        initRecylerView();

        showData();

    }

    public void initRecylerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if(lastVisibleItemPosition==recyclerView.getAdapter().getItemCount()-1)
                Toast.makeText(getApplicationContext(),"End",Toast.LENGTH_LONG).show();
           }
       });
    }


    public void initItemArrayList() {
        itemArrayList = new ArrayList<>();
        itemArrayList.add(new Item("Khang", 19));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));
        itemArrayList.add(new Item("Duy Khang", 30));

    }

    public void showData() {
        adapter = new CardAdapter(MainActivity.this, itemArrayList);
        recyclerView.setAdapter(adapter);
    }

}
