package teamapex.kr.we_t_rip.Fragment.data;

import java.io.Serializable;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PreviewCourse implements Serializable {
    private String title;
    private String imageURL;
    private int cost;
    private int id;
    private boolean islike;
    private String intro;
    private String schedule;

    public PreviewCourse(String title, String imageURL, int cost, int id, boolean islike, String intro, String schedule) {
        this.title = title;
        this.imageURL = imageURL;
        this.cost = cost;
        this.id = id;
        this.islike = islike;
        this.intro = intro;
        this.schedule = schedule;
    }

    public PreviewCourse(String title, String imageURL, int cost) {

        this.title = title;
        this.imageURL = imageURL;
        this.cost = cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean islike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }


}
