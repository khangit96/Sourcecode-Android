package musicapp.khangit.music_app.element;

/**
 * Created by Administrator on 2/4/2016.
 */
public class Song {
    private String songName;
    private String songSinger;
    private String songURL;
    private String songImageURL;
    private String lyric;

    public Song(String songName, String songSinger, String songURL, String songImageURL, String lyric) {
        this.songName = songName;
        this.songSinger = songSinger;
        this.songURL = songURL;
        this.songImageURL = songImageURL;
        this.lyric = lyric;

    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongSinger() {
        return songSinger;
    }

    public void setSongSinger(String songSinger) {
        this.songSinger = songSinger;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public String getSongImageURL() {
        return songImageURL;
    }

    public void setSongImageURL(String songImageURL) {
        this.songImageURL = songImageURL;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}
