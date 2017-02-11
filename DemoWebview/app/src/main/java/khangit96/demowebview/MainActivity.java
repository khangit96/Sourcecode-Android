package khangit96.demowebview;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.util.Log;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            ProgressDialog pDialog = new ProgressDialog(MainActivity.this);

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pDialog.setMessage("Loading...");
                pDialog.show();
                Log.d ("test", "url: "+url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pDialog.dismiss();
                String cookies = CookieManager.getInstance().getCookie(url);
               //Log.d ("test", "All the cookies in a string:" + cookies);
                super.onPageFinished(view, url);
            }
        });
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.loadUrl("http://dkmh.tdmu.edu.vn/");
    }
}
