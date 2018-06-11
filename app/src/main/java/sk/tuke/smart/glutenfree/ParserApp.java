package sk.tuke.smart.glutenfree;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by pc on 25.11.2017.
 */

public class ParserApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // parse initialization
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("1FYbhbCeWcvwO8B3g4z46R8cSrOyhSeEPZMZ7b7U")
                .clientKey("X8xiMqvoAEesEWxUkCQVj5Gk9W4gDSvNKsxVAU47")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        // set logging
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
    }

}
