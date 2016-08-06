package com.tranduythanh.camerauploader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

/**
 * Created by drthanh on 14/05/2015.
 */
public class UploadToServerTask extends AsyncTask<Void, Void, String> {

    //URL để tải hình lên server
    private String URL = "http://khangserver-khangit.rhcloud.com/home.php";
    private Activity context=null;
    private ProgressDialog progressDialog=null;
    private String ba1;
    public UploadToServerTask(Activity context, String ba1)
    {
        this.context=context;
        this.ba1=ba1;
        this.progressDialog=new ProgressDialog(this.context);
    }
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog.setMessage("Vui lòng chờ hệ thống đang upload hình!");
        this.progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        //Coding gửi hình lên Server
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("base64", ba1));
        nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpg"));
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            String st = EntityUtils.toString(response.getEntity());
            Log.v("log_tag", "In the try Loop" + st);

        } catch (Exception e) {
            Log.v("log_tag", "Lỗi kết nối : " + e.toString());
        }
        return "Thành công";

    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        this.progressDialog.hide();
        this.progressDialog.dismiss();
    }
}
