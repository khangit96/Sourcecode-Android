package khangit96.demowebview;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pDialog.dismiss();
                super.onPageFinished(view, url);
            }
        });
        myWebView.loadUrl("https://www.facebook.com/nailphuongle/");
    }
}
