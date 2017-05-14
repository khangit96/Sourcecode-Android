package com.smarthome;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pg;
    CheckBox cb;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();
        intitEvents();
    }

    /*
    * init
    *  Controls
    * */
    private void initControls() {
        pg = new ProgressDialog(MainActivity.this);
        pg.setCanceledOnTouchOutside(false);
        cb = (CheckBox) findViewById(R.id.cb);
    }


    /*
    * init Events
    * SwitchCompat
    * */
    private void intitEvents() {
        final SwitchCompat switchBatDen = (SwitchCompat) findViewById(R.id.switchBatDen);
        switchBatDen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchBatDen.isChecked()) {
                    if (check) {
                        proceccBatDen(true, "Đang bật đèn...");
                        return;
                    }
                    processBatDenServer(true,"Đang bật đèn...");
                } else {
                    if (check) {
                        proceccBatDen(false, "Đang tắt đèn...");
                        return;
                    }
                    processBatDenServer(false,"Đang tắt đèn...");
                }
            }
        });

        final SwitchCompat switchBatOCamDien = (SwitchCompat) findViewById(R.id.switchOCamDien);
        switchBatOCamDien.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchBatOCamDien.isChecked()) {
                    if(check) {
                        proceccBatOCamDien(true, "Đang bật ổ cắm điện...");
                        return;
                    }
                    proceccBatOCamDienServer(true,"Đang bật ổ cắm điện...");

                } else {
                    if(check) {
                        proceccBatOCamDien(false, "Đang tắt ổ cắm điện...");
                        return;
                    }
                    proceccBatOCamDienServer(false,"Đang tắt ổ cắm điện");

                }
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb.isChecked()) {
                    check = true;
                } else {
                    check = false;
                }
            }
        });

    }

    /*
    * Process BatDen
    * */
    public void proceccBatDen(final boolean value, final String pgMessage) {
        pg.setMessage(pgMessage);
        pg.show();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                mDatabase.setValue(true);

                pg.dismiss();
            }
        };

        mDatabase.child("1/DieuKhien/batDen").setValue(value, listener);
    }

    /*
    *Process BatOCamDien
    * */
    public void proceccBatOCamDien(final boolean value, final String pgMessage) {
        pg.setMessage(pgMessage);
        pg.show();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("1/DieuKhien/tinhTrang");
                mDatabase.setValue(true);

                pg.dismiss();
            }
        };

        mDatabase.child("1/DieuKhien/oCamDien").setValue(value, listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cauHinhWifi) {
            showDialogConfigWifi();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showDialogConfigWifi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View inflate = layoutInflater.inflate(R.layout.dialog_config_wifi, null);
        final EditText edTenWifi = (EditText) inflate.findViewById(R.id.edTenWifi);
        final EditText edMatKhau = (EditText) inflate.findViewById(R.id.edMatKhau);


        builder.setView(inflate);

        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!edTenWifi.getText().toString().equals("") && !edMatKhau.getText().toString().equals("")) {
                    requestConfigWifi(edTenWifi.getText().toString(), edMatKhau.getText().toString());
                    return;
                }
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin wifi", Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public void requestConfigWifi(String wifiName, String wifiPass) {
        pg.setMessage("Đang thay đổi cấu hình wifi...");
        pg.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        // prepare the Request
        String wifiNameFix = wifiName.replaceAll("\\s+", "+");
        String url = "http://192.168.4.1/setting?ssid=" + wifiNameFix + "&pass=" + wifiPass;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        pg.dismiss();
                        Toast.makeText(getApplicationContext(), "Cấu hình thành công. Vui lòng khởi động lại thiết bị!", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pg.dismiss();
                        Toast.makeText(getApplicationContext(), "Error+\n" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getRequest.setRetryPolicy(policy);

        queue.add(getRequest);
    }

    /*
    *
    * */
    public void processBatDenServer(boolean value,String message){
        pg.setMessage(message);
        pg.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        // prepare the Request
        String para="";
        if(value) {
            para="batDen";
        }
        else {
            para="tatDen";
        }
        String url = "http://192.168.4.1/controls?para="+para;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        pg.dismiss();
                        Toast.makeText(getApplicationContext(), "Bật đèn thành công", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pg.dismiss();
                        Toast.makeText(getApplicationContext(), "Error+\n" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getRequest.setRetryPolicy(policy);

        queue.add(getRequest);
    }

    /*
    *
    * */
    public void proceccBatOCamDienServer(boolean value,String message){
        pg.setMessage(message);
        pg.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        // prepare the Request
        String para="";
        if(value) {
            para="batOCamDien";
        }
        else {
            para="tatOCamDien";
        }
        String url = "http://192.168.4.1/controls?para="+para;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pg.dismiss();
                        // display response
                               Toast.makeText(getApplicationContext(), "Bật ổ cắm điện thành công", Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pg.dismiss();
                        Toast.makeText(getApplicationContext(), "Error+\n" + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        getRequest.setRetryPolicy(policy);

        queue.add(getRequest);
    }

}
