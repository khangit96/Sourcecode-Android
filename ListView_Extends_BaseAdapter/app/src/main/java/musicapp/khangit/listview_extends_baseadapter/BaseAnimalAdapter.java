package musicapp.khangit.listview_extends_baseadapter;

import android.content.Context;

/**
 * Created by Administrator on 2/6/2016.
 */
public class BaseAnimalAdapter {
        private  String name;
        private  String color;
        public Context context;
        public String getName() {
            return name;
        }

        public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
