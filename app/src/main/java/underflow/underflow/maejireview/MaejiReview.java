package underflow.underflow.maejireview;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by songmho on 2016-05-21.
 */
public class MaejiReview extends Application {
    String APPLICATION_ID = "cFORtt75eLiaEH2WXGpFyAqQiG1AdPOV2dyJYgYI";
    String CLIENT_KEY = "RSUllLMxkPjGObbF93LqgUKgCrxcpoU2uG4Dj6Vq";
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
