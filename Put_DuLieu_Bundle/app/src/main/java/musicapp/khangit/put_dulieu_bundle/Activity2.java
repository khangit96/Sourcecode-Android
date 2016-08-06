package musicapp.khangit.put_dulieu_bundle;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2/25/2016.
 */
public class Activity2 extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_2);
        Intent intent=getIntent();
       /* Bundle bd1=intent.getBundleExtra("bundle1");
        Bundle bd2=intent.getBundleExtra("bundle2");
        String name=bd1.getString("name");
        String phone=bd2.getString("phone");
        */
        //String name=intent.getStringExtra("name");
       // Toast.makeText(getApplicationContext(),"Name: "+name+"\n"+"Phone: "+phone,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"Name: "+name,Toast.LENGTH_LONG).show();

        //Get Data From class
        Employee employee= (Employee) getIntent().getSerializableExtra("employee");
        Toast.makeText(getApplicationContext(),"Name: "+employee.getName(),Toast.LENGTH_LONG).show();

    }
}
