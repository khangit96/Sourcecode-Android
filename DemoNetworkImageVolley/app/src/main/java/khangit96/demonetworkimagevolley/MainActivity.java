package khangit96.demonetworkimagevolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends AppCompatActivity {
    NetworkImageView img;
    ImageLoader imageLoader;
    String imageUrl = "http://4.bp.blogspot.com/-Nyfdpymc_Lo/VkQw-nJ79mI/AAAAAAAARYg/6o9VeoTvu-I/s1600-r/logo_chrome.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (NetworkImageView) findViewById(R.id.img);
    }

    public void Get(View v) {
        imageLoader = CustomVolleyRequest.getInstance(getApplicationContext()).getImageLoader();
        imageLoader.get(imageUrl, ImageLoader.getImageListener(img, R.mipmap.ic_launcher, android.R.drawable.ic_menu_report_image));
        img.setImageUrl(imageUrl, imageLoader);
    }

}
