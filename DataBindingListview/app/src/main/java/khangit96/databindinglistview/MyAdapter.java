package khangit96.databindinglistview;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import khangit96.databindinglistview.databinding.ListItemBinding;

/**
 * Created by Administrator on 12/11/2016.
 */

public class MyAdapter extends ArrayAdapter<Person> {
    List<Person> personList;
    MainActivity activity;

    public MyAdapter(MainActivity context, int resource, List<Person> personList) {
        super(context, resource, personList);
        this.activity = context;
        this.personList = personList;

    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Person getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      /*  LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item, parent, false);
        *//*TextView textview_name, textview_age;
        textview_name = (TextView) convertView.findViewById(R.id.textview_name);
        textview_age = (TextView) convertView.findViewById(R.id.textview_age);
        textview_name.setText(personList.get(position).name);
        textview_age.setText(personList.get(position).age + "");*//*
        if (inflater == null) {
            inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }*/

        ListItemBinding binding = DataBindingUtil.bind(convertView);
        binding.setUser(personList.get(position));

        return convertView;
    }
}
