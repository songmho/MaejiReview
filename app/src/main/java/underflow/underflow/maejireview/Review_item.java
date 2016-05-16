package underflow.underflow.maejireview;

/**
 * Created by 수진 on 2016-05-16.
 */
public class Review_item {
    int image;
    String title;
    String user;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }
    String getUser(){
        return this.user;
    }

    Review_item(int image, String title, String user){
        this.image=image;
        this.title=title;
        this.user=user;
    }
}
