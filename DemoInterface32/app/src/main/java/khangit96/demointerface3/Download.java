package khangit96.demointerface3;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Administrator on 7/24/2016.
 */
public class Download extends AsyncTask<Void, Void, String> {
    Context context;
    public DownloadListener downloadListener;

    public Download(Context context, DownloadListener downloadListener) {
        this.context = context;
        this.downloadListener = downloadListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Download complete";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        downloadListener.onDownloadComplete(s);
    }

    public void startDownload() {
        this.execute();
    }

}
