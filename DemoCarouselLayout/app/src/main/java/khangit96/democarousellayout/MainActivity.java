package khangit96.democarousellayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class MainActivity extends AppCompatActivity {
    /*private FeatureCoverFlow coverFlow;
    private CoverFlowAdapter adapter;
    private ArrayList<Game> games;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* coverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);

        settingDummyData();
        adapter = new CoverFlowAdapter(this, games);
        coverFlow.setAdapter(adapter);
        coverFlow.setOnScrollPositionListener(onScrollListener());*/
    }
    private FeatureCoverFlow.OnScrollPositionListener onScrollListener() {
        return new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
               // Log.v("MainActiivty", "position: " + position);
            }

            @Override
            public void onScrolling() {
            //    Log.i("MainActivity", "scrolling");
            }
        };
    }

    private void settingDummyData() {
      /*  games = new ArrayList<>();
        games.add(new Game(R.drawable.order, "Cà Phê Misol"));
        games.add(new Game(R.drawable.order, "Cà Phê Yummi"));
        games.add(new Game(R.drawable.order, "Cà Phê Misol"));
        games.add(new Game(R.drawable.order, "Cà Phê Misol"));
*/
    }
}
