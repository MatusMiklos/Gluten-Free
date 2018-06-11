package sk.tuke.smart.glutenfree.pojo;


import com.parse.ParseFile;

import java.io.Serializable;

/**
 * Created by Dominik on 25.11.2017.
 */

public class Photo implements Serializable {
    private String name;
    private String url;

    public Photo() {
    }

    public Photo(ParseFile photo) {
        this.name = photo.getName();
        this.url = photo.getUrl();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
