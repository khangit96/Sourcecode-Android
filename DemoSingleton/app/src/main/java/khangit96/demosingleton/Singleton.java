package khangit96.demosingleton;
import android.util.Log;

/**
 * Created by Administrator on 8/26/2016.
 */
public class Singleton {
    private static Singleton instance = null;
    private String name;
    private Integer age;

    protected Singleton() {
        name = "Demo Singleton";
        age = 19;
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void show() {
        Log.d("singleton: ",name+age);
    }


}
