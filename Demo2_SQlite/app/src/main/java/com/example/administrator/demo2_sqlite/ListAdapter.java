package com.example.administrator.demo2_sqlite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/28/2015.
 */
public class ListAdapter extends ArrayAdapter<user> {
    Database db=new Database(getContext());
    Context context;
    List<user> arrUser;
    int layoutId;

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<user> items) {
        super(context, resource, items);
        this.context=context;
        this.arrUser=items;
        this.layoutId=resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.content_custom__listview, null);
        }

        user u = getItem(position);

        if (u != null) {
            final ArrayList<Integer> arrId=new ArrayList<Integer>();
            Cursor kq=db.GetData("SELECT * FROM user");
            while(kq.moveToNext()){
                Integer id=kq.getInt(0);;
                arrId.add(id);
            }
            // Anh xa + Gan gia tri
            TextView tvUsrername = (TextView) v.findViewById(R.id.tvUsername);
            Button btEdit=(Button)v.findViewById(R.id.btEdit);
            Button btDelete=(Button)v.findViewById(R.id.btDelete);
            tvUsrername.setText(u.Username);
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Intent Main_Screen=new Intent(ListAdapter.this,MainActivity.class);
                   // Toast.makeText(getContext(),""+position,Toast.LENGTH_LONG).show();
                    db.delete_byID(arrId.get(position),"user","_id");
                   arrUser.remove(position);
                    notifyDataSetChanged();

                }
            });
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   user u=arrUser.get(position);
                    String username=u.Username;
                    String password=u.Password;
                    Intent i=new Intent(context,Edit.class);
                    Bundle bd=new Bundle();
                    bd.putInt("id",arrId.get(position));
                    i.putExtra("username",username);
                    i.putExtra("password",password);
                    i.putExtra("id",arrId.get(position));
                   // i.putExtra("id",bd);
                    context.startActivity(i);
                }
            });

        }

        return v;
    }


}