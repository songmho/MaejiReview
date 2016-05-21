package underflow.underflow.maejireview;

/**
 * Created by 수진 on 2016-05-16.
 */
public class Review_item {
    int image;
    String title;
    String user;

    public int getImage(){
        return this.image;
    }
    public String getTitle(){
        return this.title;
    }
    public String getUser(){
        return this.user;
    }

    public Review_item(int image, String title, String user){
        this.image=image;
        this.title=title;
        this.user=user;
    }
}
