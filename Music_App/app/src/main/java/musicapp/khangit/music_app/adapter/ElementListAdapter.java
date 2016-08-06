package musicapp.khangit.music_app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import musicapp.khangit.music_app.R;
import musicapp.khangit.music_app.element.BaseListElement;

/**
 * Created by Administrator on 2/5/2016.
 */
public class ElementListAdapter extends BaseAdapter{
    public List<BaseListElement> listElements;
    private Context context;
    public ElementListAdapter(Context context, List<BaseListElement> listElements){
        this.listElements = listElements;
        this.context=context;
    }

    //Hàm đếm số lương item
    @Override
    public int getCount() {
        return listElements.size();
    }

    //Hàm vẽ lên 1 đối tượng item
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
   //Hàm thể hiện giao diện của 1 dòng item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context, R.layout.list_item,null);
        ImageView icon;
        TextView elementName, elementNumber;
        icon = (ImageView) convertView.findViewById(R.id.image);
        elementName = (TextView) convertView.findViewById(R.id.element_name);
        elementNumber = (TextView) convertView.findViewById(R.id.element_number);
        icon.setImageResource(listElements.get(position).getInconResource());
        elementName.setText(listElements.get(position).getElementName());
        elementNumber.setText(String.valueOf(listElements.get(position).getNumber()));
        return convertView;
    }
}
