package tdmug3.demonotifiaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 3/26/2016.
 */
public class NotificationView extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
      //  Intent myData= getIntent();
        //Trích xuất dữ liệu của Notification
       // String msg= myData.getStringExtra("extendedTitle") + "\n" + myData.getStringExtra("extendedText");
        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
    }
}
