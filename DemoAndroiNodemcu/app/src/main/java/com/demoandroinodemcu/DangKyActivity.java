package com.demoandroinodemcu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DangKyActivity extends AppCompatActivity {
    EditText edUsername, edPassword;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
    }

    public void DangKy(View v) {
        ProgressDialog pg = new ProgressDialog(DangKyActivity.this);
        pg.setMessage("Đang đăng kí");
        pg.setCanceledOnTouchOutside(false);
        pg.show();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NguoiDung");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Intent intent = new Intent(DangKyActivity.this, LoginActivity.class);
                        intent.putExtra("username", edUsername.getText().toString());
                        intent.putExtra("password", edPassword.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                DatabaseReference m1 = FirebaseDatabase.getInstance().getReference().child("NguoiDung/" +String.valueOf(dataSnapshot.getChildrenCount()+1)+"/ThongTinChung");
                m1.setValue(new ThongTinChung(0, 0, edPassword.getText().toString(), 0, edUsername.getText().toString()), listener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
