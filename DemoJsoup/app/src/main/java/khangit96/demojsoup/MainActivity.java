package khangit96.demojsoup;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import  org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {
    String link = "http://poem.tkaraoke.com/57517/MONG_CHIEU_THU.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetImage().execute();
    }
    public class GetImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.d("tuhoc", "on get data");

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect(link)
                        .get();

                Elements elements = doc.select("div.col-md-12 br");

                for(int i =0; i < elements.size(); i ++){
                    /*Element e = elements.get(i);
                    Elements img = e.select("img");
                    String title = e.attr("title");
                    String image = img.attr("src");*/

                    Log.d("tuhoc",elements.toString());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute (Void aVoid){
            super.onPostExecute(aVoid);

            Log.d("tuhoc", "finish");

        }

    }

}
