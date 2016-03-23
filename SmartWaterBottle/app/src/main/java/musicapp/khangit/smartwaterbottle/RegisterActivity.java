package musicapp.khangit.smartwaterbottle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/18/2016.
 */
public class RegisterActivity extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    //
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputUsername;
    private EditText inputPassword;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //init
        inputFullName = (EditText) findViewById(R.id.name);
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);


        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String email = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                        runOnUiThread(new Runnable() {//gửi dữ liệu lên server để tiến hành đăng kí
                            @Override
                            public void run() {
                                new SendData().execute("http://khangserver-khangit.rhcloud.com/register.php");
                            }
                        });
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent iLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(iLogin);
                finish();
            }
        });


    }

    //Gửi dữ liệu username  password và fullname  lên server để đăng kí tài khoản
    class SendData extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Registering....");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("ok")){//Đăng kí thành công
                Intent iLogin=new Intent(RegisterActivity.this,LoginActivity.class);
                iLogin.putExtra("username", inputUsername.getText().toString());
                iLogin.putExtra("password", inputPassword.getText().toString());
               // Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
                progressDialog.cancel();
                startActivity(iLogin);
                finish();

            }
            else{
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(),"Register failed!!",Toast.LENGTH_LONG).show();
            }
        }
    }

    //Hàm gửi dữ liệu
    private String makePostRequest(String url) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(3);
        nameValuePair.add(new BasicNameValuePair("username", inputUsername.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("password", inputPassword.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("fullname", inputFullName.getText().toString()));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
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

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
    }

    //trong phương thức onBackPressed()
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) { //doubleBackToExitPressedOnce==true
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit !", Toast.LENGTH_SHORT).show();
    }

}






