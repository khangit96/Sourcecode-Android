package khangit96.tdmuteamfhome.model;

/**
 * Created by Administrator on 11/23/2016.
 */

public class Review {
    public String commentContent;
    public String commentUser;

    public Review(String commentContent, String commentUser) {
        this.commentContent = commentContent;
        this.commentUser = commentUser;
    }

    public Review() {

    }
}
