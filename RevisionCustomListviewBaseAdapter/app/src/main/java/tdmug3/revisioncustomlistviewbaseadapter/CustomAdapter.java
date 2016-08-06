package tdmug3.revisioncustomlistviewbaseadapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 4/30/2016.
 */
public class CustomAdapter extends BaseAdapter {
    List<Person> listPerson;
    private Context context;
    SharedPreferences sp;

    public CustomAdapter(Context context, List<Person> listPerson) {
        this.context = context;
        this.listPerson = listPerson;
        sp = context.getSharedPreferences("TEST", Context.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return listPerson.size();
    }

    @Override
    public Object getItem(int position) {
        return listPerson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.list_item, null);
        TextView tvName, tvAge;
        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvAge = (TextView) convertView.findViewById(R.id.tvAge);
        tvName.setText(listPerson.get(position).getName().toString());
        tvAge.setText(String.valueOf(listPerson.get(position).getAge()));
        if (sp.getString("test", "").equals("full")) {
            Toast.makeText(context, "Full Mode", Toast.LENGTH_LONG).show();
        } else if (sp.getString("test", "").equals("short")) {
            Toast.makeText(context, "Short Mode", Toast.LENGTH_LONG).show();
        }
        return convertView;
    }
}
