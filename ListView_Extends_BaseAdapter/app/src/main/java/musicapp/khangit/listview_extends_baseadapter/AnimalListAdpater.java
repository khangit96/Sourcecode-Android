package musicapp.khangit.listview_extends_baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2/6/2016.
 */
public class AnimalListAdpater extends BaseAdapter {
    List<BaseAnimalAdapter> list;
    private Context context;

    public AnimalListAdpater(Context context, List<BaseAnimalAdapter> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tvName,tvColor;
        convertView=View.inflate(context,R.layout.list_item,null);
        tvName=(TextView)convertView.findViewById(R.id.tvName);
        tvColor=(TextView)convertView.findViewById(R.id.tvColor);
        tvName.setText(list.get(position).getName().toString());
        tvColor.setText(list.get(position).getColor().toString());
        return convertView;
    }
}
