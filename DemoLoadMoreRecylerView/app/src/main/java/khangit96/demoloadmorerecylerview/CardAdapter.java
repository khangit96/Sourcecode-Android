package khangit96.demoloadmorerecylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Administrator on 7/12/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<SuperHero> superHeroes;

    public CardAdapter(List<SuperHero> superHeroes, Context context) {
        this.context = context;
        this.superHeroes = superHeroes;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.superheroes_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Getting the particular item from the list
        SuperHero superHero = superHeroes.get(position);
        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        //Showing data on the views
        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.textViewName.setText(superHero.getName());
        holder.textViewPublisher.setText(superHero.getPublisher());
    }

    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewPublisher;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher);
        }
    }
}
