package underflow.underflow.maejireview;

/**
 * Created by songmho on 2016-05-02.
 */
public class ListItem {
    int background;
    String title;
    String user;

    public int getBackground() {
        return background;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }
    public ListItem(int background, String title, String user){
        this.background=background;
        this.title=title;
        this.user=user;
    }
}
