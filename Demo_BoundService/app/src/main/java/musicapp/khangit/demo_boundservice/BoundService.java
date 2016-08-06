package musicapp.khangit.demo_boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2/14/2016.
 */
public class BoundService extends Service {
    IBinder iBinder=new LocalBinder();//liên kết đến client
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public class LocalBinder extends Binder{
        LocalBinder getLocalBinder(){
            return LocalBinder.this;//Phương thức khởi tạo khi client gọi tới các phương thức của service
        }
    }
    public String show(){
        return "I am coder";
    }
}
