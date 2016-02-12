package musicapp.khangit.listview_extends_baseadapter;

import android.content.Context;

/**
 * Created by Administrator on 2/6/2016.
 */
public class Dog extends BaseAnimalAdapter {
    public Dog(Context context,String name, String color) {
        this.setName(name);
        this.setColor(color);
        this.context=context;
    }
}
