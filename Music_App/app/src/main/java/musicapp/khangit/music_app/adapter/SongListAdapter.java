package musicapp.khangit.music_app.adapter;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import musicapp.khangit.music_app.R;
import musicapp.khangit.music_app.element.Song;
/**
 * Created by tikier on 1/11/16.
 */
public class SongListAdapter extends BaseAdapter {

    ArrayList<Song> listSong;
    Context context;
 //   DownloadManager downloadManager;
  //  long enqueue;
   // DisplayImageOptions options;

    public SongListAdapter(Context context){
        listSong = new ArrayList<>();
        this.context = context;
     /*   ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.ic_default)
                .showImageOnFail(R.drawable.ic_default)
                .build();
     */
    }

    @Override
    public int getCount() {
        return listSong.size();
    }

    @Override
    public Object getItem(int position) {
        return listSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.song_list_item, null);

        TextView songName = (TextView) convertView.findViewById(R.id.song_name);
        TextView songSinger = (TextView) convertView.findViewById(R.id.song_singer);
        ImageView songImage = (ImageView) convertView.findViewById(R.id.song_image);
        Button downloadButton = (Button) convertView.findViewById(R.id.download_button);

    /*    BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
                    Toast.makeText(context, "Download thanh cong", Toast.LENGTH_LONG).show();
                }
            }
        };

     context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(listSong.get(position).getSongURL()));

                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                        .setTitle(listSong.get(position).getSongName() + ".mp3")
                        .setDescription(listSong.get(position).getSongName() + "-" + listSong.get(position).getSongSinger())
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, listSong.get(position).getSongName() + ".mp3");

                enqueue = downloadManager.enqueue(request);

                Intent i = new Intent();
                i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                context.startActivity(i);
            }
        });

        ImageLoader.getInstance().displayImage(listSong.get(position).getSongImageURL(), songImage, options);
        songName.setText(listSong.get(position).getSongName());
        songSinger.setText(listSong.get(position).getSongSinger());
        */
        return convertView;

    }


   public void addItem(Song song){
        if(listSong != null && song != null){
            listSong.add(song);
        }
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(context, PlayerActivity.class);
        i.putExtra("songName", listSong.get(position).getSongName());
        i.putExtra("songURL", listSong.get(position).getSongURL());
        context.startActivity(i);
    }
    */

}
