package com.example.administrator.demorecyclerview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 1/25/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
    List<Person> persons;
    RVAdapter(List<Person> persons){
        this.persons = persons;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        final PersonViewHolder pvh = new PersonViewHolder(view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.person_name.setText(persons.get(i).name);
        personViewHolder.person_age.setText(persons.get(i).age);
        personViewHolder.photo_person.setImageResource(persons.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public static class PersonViewHolder  extends RecyclerView.ViewHolder{
        CardView cv;
        TextView person_name;
        TextView person_age;
        ImageView photo_person;
        public PersonViewHolder(final View itemView) {
            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.cv);
            person_name=(TextView)itemView.findViewById(R.id.person_name);
            person_age=(TextView)itemView.findViewById(R.id.person_age);
            photo_person=(ImageView)itemView.findViewById(R.id.person_photo);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                   Toast.makeText(v.getContext(),person_age.getText(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
