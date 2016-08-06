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
    ArrayList<Product> productArrayList;
    ProductAdapter productAdapter;
    int po;
    int id;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lvProduct = (ListView) findViewById(R.id.lvProduct);
        productArrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(this, productArrayList);
        new LoadProduct().execute("http://khangit.96.lt/WebServiceLaravel/public/SanPham");
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent updateIntent = new Intent(ListActivity.this, UpdateProduct.class);
                updateIntent.putExtra("name", productArrayList.get(position).TenSP);
                updateIntent.putExtra("price", productArrayList.get(position).GiaSP);
                updateIntent.putExtra("image", productArrayList.get(position).HinhAnh);
                updateIntent.putExtra("id", productArrayList.get(position).id);
                updateIntent.putExtra("po", position);
                startActivityForResult(updateIntent, 1);
                po = position;
                id = productArrayList.get(position).id;
                img = productArrayList.get(position).HinhAnh;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String check=data.getStringExtra("name");
            if (check==null) {
               productArrayList.remove(po);
            } else {
                productArrayList.set(po, new Product(data.getStringExtra("name"), data.getIntExtra("price",0),img,id));
            }
            productAdapter.notifyDataSetChanged();
        }
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
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("TenSP");
                    int price = jsonObject.getInt("Gia");
                    String img = jsonObject.getString("HinhSP");
                    int id = jsonObject.getInt("id");
                    productArrayList.add(new Product(name, price, img, id));
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
