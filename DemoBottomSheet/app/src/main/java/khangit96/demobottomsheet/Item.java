package khangit96.demobottomsheet;

/**
 * Created by Administrator on 6/27/2016.
 */
public class Item {

    private int mDrawableRes;

    private String mTitle;

    public Item(int drawable, String title) {
        mDrawableRes = drawable;
        mTitle = title;
    }

    public int getDrawableResource() {
        return mDrawableRes;
    }

    public String getTitle() {
        return mTitle;
    }
}
