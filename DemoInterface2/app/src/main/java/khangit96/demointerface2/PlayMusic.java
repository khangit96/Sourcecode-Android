package khangit96.demointerface2;

/**
 * Created by Administrator on 7/22/2016.
 */
public class PlayMusic {
    public PlayListener playListener;

    public void setPlayListener(PlayListener playListener) {
        this.playListener = playListener;
    }

    public void playMp3() {
        playListener.play("Mp3");
    }

    public void playMp4() {
        playListener.play("Mp4");
    }

    public void playVideo() {
        playListener.play("Video");
    }

}
