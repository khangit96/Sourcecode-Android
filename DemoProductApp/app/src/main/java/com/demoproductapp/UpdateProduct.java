package com.demoproductapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Base64;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UpdateProduct extends AppCompatActivity {
    Button btUpdate, btDelete;
    EditText edName, edPrice;
    ImageView img;
    String name, image;
    int price;
    int id;
    int po;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price", 0);
        image = intent.getStringExtra("image");
        id = intent.getIntExtra("id", 0);
        po = intent.getIntExtra("po", 0);
        init();
    }

    private void init() {
        btUpdate = (Button) findViewById(R.id.btUpdate);
        btDelete = (Button) findViewById(R.id.btDelete);
        img = (ImageView) findViewById(R.id.img);
        edName = (EditText) findViewById(R.id.edName);
        edPrice = (EditText) findViewById(R.id.edPrice);
        edName.setText(name);
        edPrice.setText("" + price);
        String_To_ImageView(image, img);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Update().execute();
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().execute();
            }
        });
    }

    public class Update extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return POST_URL("http://khangit.96.lt/WebServiceLaravel/public/SanPham/" + id, "PUT");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent backIntent = new Intent();
            backIntent.putExtra("name",edName.getText().toString());
            backIntent.putExtra("price",Integer.parseInt(edPrice.getText().toString()));
            setResult(RESULT_OK, backIntent);
           finish();
        }
    }

    public class Delete extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return POST_URL("http://khangit.96.lt/WebServiceLaravel/public/SanPham/" + id, "DELETE");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent backIntent = new Intent();
            setResult(RESULT_OK, backIntent);
            finish();
        }
    }

    private String POST_URL(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(4);
        nameValuePair.add(new BasicNameValuePair("TenSP", edName.getText().toString()));

        String sHinh = ImageView_To_String(img);
        nameValuePair.add(new BasicNameValuePair("HinhSP", sHinh));

        nameValuePair.add(new BasicNameValuePair("Gia", edPrice.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("_method", type));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }

    public void String_To_ImageView(String strBase64, ImageView iv) {
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }

    public String ImageView_To_String(ImageView iv) {
        BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String strHinh = Base64.encodeToString(byteArray, 0);
        return strHinh;
    }
}
