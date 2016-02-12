package musicapp.khangit.music_app.element;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2/5/2016.
 */
public abstract class BaseListElement {
    private  int inconResource;
    private  String elementName;
    private  int number;
   public Context context;
    public int getInconResource() {
        return inconResource;
    }

    public void setInconResource(int inconResource) {
        this.inconResource = inconResource;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public abstract void updateData();
    public abstract View.OnClickListener getOnClickListener();
}
