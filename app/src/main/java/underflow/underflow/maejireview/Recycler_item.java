package underflow.underflow.maejireview;

/**
 * Created by 수진 on 2016-05-16.
 */
public class Recycler_item {
    int image;
    String title;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }

    Recycler_item(int image, String title){
        this.image=image;
        this.title=title;
    }
}
