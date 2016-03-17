package musicapp.khangit.smartwaterbottle;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Administrator on 3/16/2016.
 */
public class FollowerActivity extends android.support.v4.app.Fragment {
    Button btLitre;
    Boolean clickCheck=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.activity_follower,container,false);
        btLitre=(Button)rootview.findViewById(R.id.btLitre);
        btLitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickCheck==true){//nếu người dunfng click lân đầu để theo dõi
                    Toast.makeText(getContext(),"Following !",LENGTH_LONG).show();
                    btLitre.setText("Following...");
                    btLitre.setTextSize(15);
                    clickCheck=false;
                }
                else{
                    Toast.makeText(getContext(),"Stopped !",LENGTH_LONG).show();
                    btLitre.setText("2.0 Lit");
                    btLitre.setTextSize(30);
                    clickCheck=true;
                }

            }
        });
        return rootview;
    }

}
