package sk.tuke.smart.glutenfree.pojo;


import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sk.tuke.smart.glutenfree.pojo.exceptions.DataNotFetched;

/**
 * Created by Hawk on 25.11.2017.
 */

public class Podniky implements Serializable {
    private static final String TAG = "Podniky";
    private transient ParseObject originalParseObject;
    private String objectId;
    private String name;
    private Double longtitude;
    private Double lattitude;
    private Integer stars;
    private String text;
    private Photo photo;
    private String address;
    private List<KategorieJedal> kategorieJedal = null;


    public Podniky() {
    }

    public Podniky(ParseObject podnik) {
        this.objectId = podnik.getObjectId();
        this.name = podnik.getString("Name");
        this.longtitude = podnik.getDouble("Longtitude");
        this.lattitude = podnik.getDouble("Lattitude");
        this.stars = podnik.getInt("Stars");
        this.text = podnik.getString("Text");
        this.address = podnik.getString("Address");
        this.photo = new Photo(podnik.getParseFile("Photo"));

        this.originalParseObject = podnik;
    }

    public void fetchKategorieJedal() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("kategorieJedal");
        List<ParseObject> kategorieJedal = relation.getQuery().find();
        this.kategorieJedal = new ArrayList<>();
        for (ParseObject kategoriaJedla : kategorieJedal) {
            this.kategorieJedal.add(new KategorieJedal(kategoriaJedla));
        }
    }

    public void fetchKategorieJedalInBackground() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("kategorieJedal");
        relation.getQuery().findInBackground((kategorieJedal, exception) -> {
            if (exception == null) {
                this.kategorieJedal = new ArrayList<>();
                for (ParseObject kategoriaJedla : kategorieJedal) {
                    this.kategorieJedal.add(new KategorieJedal(kategoriaJedla));
                }

            } else {
                Log.w(TAG, "Could not fetch kategorieJedal: " + exception.getMessage());
            }
        });
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<KategorieJedal> getKategorieJedal() throws DataNotFetched {
        if (kategorieJedal == null) {
            throw new DataNotFetched("Fetch katogorieJedal in Podniky first.");
        }
        return kategorieJedal;
    }

    public void setKategorieJedal(List<KategorieJedal> kategorieJedal) {
        this.kategorieJedal = kategorieJedal;
    }

}
