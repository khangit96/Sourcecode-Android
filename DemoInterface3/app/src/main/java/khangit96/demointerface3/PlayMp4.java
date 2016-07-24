package khangit96.demointerface3;

/**
 * Created by Administrator on 7/23/2016.
 */
public class PlayMp4 {
    PlayListener playListener;

    public PlayMp4(PlayListener playListener) {
        this.playListener = playListener;
    }

    public void play() {
        playListener.play("Mp4");
    }
}
