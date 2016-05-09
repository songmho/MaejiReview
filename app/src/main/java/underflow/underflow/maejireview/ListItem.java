package underflow.underflow.maejireview;

/**
 * Created by songmho on 2016-05-02.
 */
public class ListItem {
    String title;
    String user;

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }
    public ListItem(String title, String user){
        this.title=title;
        this.user=user;
    }
}
