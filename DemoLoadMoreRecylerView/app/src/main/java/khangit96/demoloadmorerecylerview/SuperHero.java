package khangit96.demoloadmorerecylerview;

/**
 * Created by Administrator on 7/12/2016.
 */
public class SuperHero {
    //Data Variables
    private String imageUrl;
    private String name;
    private String publisher;

    public SuperHero(String imageUrl, String name, String publisher) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.publisher = publisher;
    }

    //Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
