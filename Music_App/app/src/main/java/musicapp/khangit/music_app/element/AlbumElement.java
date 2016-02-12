package musicapp.khangit.music_app.element;

import android.content.Context;
import android.view.View;

import java.util.List;

import musicapp.khangit.music_app.R;

/**
 * Created by Administrator on 2/5/2016.
 */
public class AlbumElement extends BaseListElement {
    public Context context;
    public AlbumElement(Context context){
        this.context = context;
        updateData();
    }
    @Override
    public void updateData() {
        this.setNumber(0);
        this.setElementName("Album");
        this.setInconResource(R.drawable.ic_default);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return null;
    }
}
