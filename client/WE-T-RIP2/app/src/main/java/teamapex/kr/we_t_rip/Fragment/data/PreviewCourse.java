package teamapex.kr.we_t_rip.Fragment.data;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class PreviewCourse {
    private String title;
    private String imageURL;
    private int cost;

    public PreviewCourse(String title, String imageURL, int cost) {

        this.title = title;
        this.imageURL = imageURL;
        this.cost = cost;
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


}
