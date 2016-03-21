package musicapp.khangit.smartwaterbottle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 3/17/2016.
 */
public class LoginActivity extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
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
}
