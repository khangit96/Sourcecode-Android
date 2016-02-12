package musicapp.khangit.music_app.element;

import android.content.Context;
import android.view.View;

import musicapp.khangit.music_app.R;

/**
 * Created by Administrator on 2/5/2016.
 */
public class SongElement extends  BaseListElement {
    public SongElement(Context context){
        updateData();
        this.context=context;
    }
    @Override
    public void updateData() {
        this.setElementName("Songs");
        this.setInconResource(R.drawable.ic_search_white);
        this.setNumber(0);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return null;
    }
}
