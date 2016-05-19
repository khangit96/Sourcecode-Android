package com.demoproductapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView lvProduct;
    ArrayList<Product>productArrayList;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvProduct = (ListView) findViewById(R.id.lvProduct);
        productArrayList=new ArrayList<>();
        productAdapter=new ProductAdapter(this,productArrayList);
        new LoadProduct().execute("http://192.168.1.12/code/WebServiceLaravel/public/SanPham");
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent updateIntent=new Intent(ListActivity.this,UpdateProduct.class);
                startActivity(updateIntent);
            }
        });
    }

    class LoadProduct extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return GET_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String name=jsonObject.getString("TenSP");
                    int price=jsonObject.getInt("Gia");
                    String img=jsonObject.getString("HinhSP");
                    productArrayList.add(new Product(name,price,img));
                }
                lvProduct.setAdapter(productAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String GET_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}
