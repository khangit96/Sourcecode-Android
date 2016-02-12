package musicapp.khangit.music_app.data;

import java.util.ArrayList;

import musicapp.khangit.music_app.element.Song;

/**
 * Created by Administrator on 2/4/2016.
 */
public class ListSong {
    private static ArrayList<Song> listSong;

    public static ArrayList<Song> getListSong() {
        if (listSong == null) {
            listSong = new ArrayList<>();
            listSong.add(new Song("Tết An Tài Lộc", " Huỳnh Lộc", "http://mp3.zing.vn/xml/load-song/MjAxNiUyRjAxJTJGMjUlMkY2JTJGMiUyRjYyZjdkNzVlYWI1OTZlZmI4MWI3YjkyOTc2ZTUwZDVmLm1wMyU3QzEz", "http://image.mp3.zdn.vn/cover3_artist/7/d/7d47c8e78a665e315770049cc7d3217d_1449029941.jpg", "khangho"));
            listSong.add(new Song("Mong du", " Huỳnh Lộc", "http://mp3.zing.vn/xml/load-song/MjAxNiUyRjAxJTJGMjUlMkY2JTJGMiUyRjYyZjdkNzVlYWI1OTZlZmI4MWI3YjkyOTc2ZTUwZDVmLm1wMyU3QzEz", "http://image.mp3.zdn.vn/cover3_artist/7/d/7d47c8e78a665e315770049cc7d3217d_1449029941.jpg", "khangho"));
            listSong.add(new Song("Mong Thuong", " Huỳnh Lộc", "http://mp3.zing.vn/xml/load-song/MjAxNiUyRjAxJTJGMjUlMkY2JTJGMiUyRjYyZjdkNzVlYWI1OTZlZmI4MWI3YjkyOTc2ZTUwZDVmLm1wMyU3QzEz", "http://image.mp3.zdn.vn/cover3_artist/7/d/7d47c8e78a665e315770049cc7d3217d_1449029941.jpg", "khangho"));
            listSong.add(new Song("Mong Lai", " Huỳnh Lộc", "http://mp3.zing.vn/xml/load-song/MjAxNiUyRjAxJTJGMjUlMkY2JTJGMiUyRjYyZjdkNzVlYWI1OTZlZmI4MWI3YjkyOTc2ZTUwZDVmLm1wMyU3QzEz", "http://image.mp3.zdn.vn/cover3_artist/7/d/7d47c8e78a665e315770049cc7d3217d_1449029941.jpg", "khangho"));
            listSong.add(new Song("Mong du", " Huỳnh Lộc", "http://mp3.zing.vn/xml/load-song/MjAxNiUyRjAxJTJGMjUlMkY2JTJGMiUyRjYyZjdkNzVlYWI1OTZlZmI4MWI3YjkyOTc2ZTUwZDVmLm1wMyU3QzEz", "http://image.mp3.zdn.vn/cover3_artist/7/d/7d47c8e78a665e315770049cc7d3217d_1449029941.jpg", "khangho"));

        }

        return listSong;
    }
}
