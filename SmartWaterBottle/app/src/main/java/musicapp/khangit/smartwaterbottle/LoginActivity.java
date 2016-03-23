package musicapp.khangit.smartwaterbottle;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
 * Created by Administrator on 3/17/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputUsername;
    private EditText inputPassword;

    //SharedPreferences
    SharedPreferences spLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spLogin = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);//thông tin lít nước

        //Khỏi tạo
        init();
        //Kiểm tra nếu người dùng mới vùa đăng kí (đk gửi từ RegisterActivity)
        Bundle bdRegister = getIntent().getExtras();
        if (bdRegister != null) {
            String username = bdRegister.getString("username");
            String password = bdRegister.getString("password");

            inputUsername.setText(username);
            inputPassword.setText(password);
        }

        //Kiểm tra xem người dùng có logout hay ko
        Bundle bdLogin = getIntent().getExtras();
        if (bdLogin != null) {//Nếu người dùng logout
            PutDataSharepreferences("login", "");
        }
        //Kiểm tra nếu xem người dùng đã đăng nhập hay chưa
        String checkLogin = spLogin.getString("login", "");

        if (checkLogin.equals("")) {//đăng nhập lần đầu

            // Login button Click Event
            btnLogin.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    String username = inputUsername.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();

                    // Check for empty data in the form
                    if (!username.isEmpty() && !password.isEmpty()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {//bắt đầu gủi dữ liệu lên server và kiểm tra
                                new SendData().execute("http://khangserver-khangit.rhcloud.com/checkLogin.php");
                            }
                        });
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(getApplicationContext(), "Please enter your full account information !!", Toast.LENGTH_LONG).show();
                    }

                }

            });
        } else if (checkLogin.equals("ok")) {
            //Ẩn thông tin đăng nhập
            HideLogin();
            //To HomeScreen
            String username = spLogin.getString("username", "");
            String password = spLogin.getString("password", "");
            String fullname = spLogin.getString("fullname", "");
            Intent iHomeScreen = new Intent(LoginActivity.this, MainActivity.class);
            iHomeScreen.putExtra("username", username);
            iHomeScreen.putExtra("password", password);
            iHomeScreen.putExtra("fullname", fullname);
            startActivity(iHomeScreen);
            finish();
        }
    }

    //Hàm khởi tạo
    public void init() {
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent iRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(iRegister);
                finish();

            }
        });
    }

    //Hàm thông tin đăng nhập
    public void HideLogin() {
        inputUsername.setVisibility(View.INVISIBLE);
        inputPassword.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);
        btnLinkToRegister.setVisibility(View.INVISIBLE);
    }

    private void checkLogin(String email, String password) {
        Intent iHome = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(iHome);
        finish();//kết thúc activity Login

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

    //Gửi dữ liệu username và password lên server để kiểm tra
    class SendData extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Logging....");
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
            if (!s.equals("Failed")) {
                ArrayList<String> arrUsername = new ArrayList<String>();
                ArrayList<String> arrPassword = new ArrayList<String>();
                ArrayList<String> arrFulname = new ArrayList<String>();
                ArrayList<String> arrRemaing = new ArrayList<String>();
                ArrayList<String> arrDrank = new ArrayList<String>();
                ArrayList<String> arrLitre = new ArrayList<String>();
                try {
                    JSONArray mang = new JSONArray(s);
                    for (int i = 0; i < mang.length(); i++) {
                        JSONObject test = mang.getJSONObject(i);
                        arrUsername.add(test.getString("username"));
                        arrPassword.add(test.getString("password"));
                        arrFulname.add(test.getString("fullname"));
                        arrRemaing.add(test.getString("remaining"));
                        arrDrank.add(test.getString("drank"));
                        arrLitre.add(test.getString("litre"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Put data cho login
                PutDataSharepreferences("login", "ok");

                //PUt data cho Username
                PutDataSharepreferences("username", arrUsername.get(0));
                PutDataSharepreferences("password", arrPassword.get(0));
                PutDataSharepreferences("fullname", arrFulname.get(0));

                //To HomeScreen
                Intent iHomeScreen = new Intent(LoginActivity.this, MainActivity.class);
                iHomeScreen.putExtra("username", arrUsername.get(0));
                iHomeScreen.putExtra("password", arrPassword.get(0));
                iHomeScreen.putExtra("fullname", arrFulname.get(0));
                iHomeScreen.putExtra("remaining", arrRemaing.get(0));
                iHomeScreen.putExtra("drank", arrDrank.get(0));
                iHomeScreen.putExtra("litre", arrLitre.get(0));
                Toast.makeText(getApplicationContext(), "Login Successed!", Toast.LENGTH_LONG).show();
                startActivity(iHomeScreen);
                finish();
                progressDialog.cancel();
            } else {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Hàm gửi dữ liệu
    private String makePostRequest(String url) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("username", inputUsername.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("password", inputPassword.getText().toString()));

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

    //Hàm put data Sharepreferences spLogin
    public void PutDataSharepreferences(String name, String data) {
        SharedPreferences.Editor editor = spLogin.edit();
        editor.putString(name, data);
        editor.commit();
    }

}
