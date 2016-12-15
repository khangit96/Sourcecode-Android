package khangit96.tdmuteamfhome.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.activity.Review;

/**
 * Created by Administrator on 11/23/2016.
 */

public class ListViewReviewAdapter extends ArrayAdapter<Review> {
    private ReviewActivity activity;
    private List<Review> reviewList;

    public ListViewReviewAdapter(ReviewActivity context, int resource, List<Review> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.reviewList = objects;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Review getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview_review, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }

        //     holder.friendName.setText(getItem(position));
        holder.textview_username.setText(getItem(position).commentUser);
        holder.textView_content.setText(getItem(position).commentContent);

        //get first letter of each String item
        String firstLetter = String.valueOf(getItem(position).commentUser.charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(getItem(position));
        //int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.imageView.setImageDrawable(drawable);

        return convertView;
    }

    private class ViewHolder {
        private ImageView imageView;
        private TextView textview_username, textView_content;

        public ViewHolder(View v) {
            imageView = (ImageView) v.findViewById(R.id.image_view);
            textview_username = (TextView) v.findViewById(R.id.textview_username);
            textView_content = (TextView) v.findViewById(R.id.textview_content);

        }
    }

}
