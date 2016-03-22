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
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init
        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!username.isEmpty() && !password.isEmpty()) {
                    // login user
                   // checkLogin(email, password);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new SendData().execute("http://khangserver-khangit.rhcloud.com/checkLogin.php");
                        }
                    });
                } else {
                    // Prompt user to enter cre
                    // dentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter your full account information !!", Toast.LENGTH_LONG).show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent iRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(iRegister);
                finish();

            }
        });

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

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
           if(s.equals("ok")){
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
               Intent iHomeScreen=new Intent(LoginActivity.this,MainActivity.class);
               startActivity(iHomeScreen);
               finish();
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
        nameValuePair.add(new BasicNameValuePair("username",inputUsername.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("password",inputPassword.getText().toString()));

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
}
