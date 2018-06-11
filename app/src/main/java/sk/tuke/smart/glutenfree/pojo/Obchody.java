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

public class Obchody implements Serializable {
    private static final String TAG = "Obchody";
    private transient ParseObject originalParseObject;
    private String objectId;
    private String name;
    private String address;
    private Double lattitude;
    private Double longtitude;
    private Integer stars;
    private String text;
    private Photo photo;
    private List<Produkty> produkty = null;

    public Obchody() {
    }

    public Obchody(ParseObject obchod) {
        this.objectId = obchod.getObjectId();
        this.name = obchod.getString("Name");
        this.address = obchod.getString("Address");
        this.lattitude = obchod.getDouble("Lattitude");
        this.longtitude = obchod.getDouble("Longtitude");
        this.stars = obchod.getInt("Stars");
        this.text = obchod.getString("Text");
        this.photo = new Photo(obchod.getParseFile("Photo"));

        this.originalParseObject = obchod;
    }

    public void fetchProdukty() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("produkty");
        List<ParseObject> produkty = relation.getQuery().find();
        this.produkty = new ArrayList<>();
        for (ParseObject produkt : produkty) {
            this.produkty.add(new Produkty(produkt));
        }
    }

    public void fetchProduktyInBackground() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("produkty");
        relation.getQuery().findInBackground((produkty, exception) -> {
            if (exception == null) {
                this.produkty = new ArrayList<>();
                for (ParseObject produkt : produkty) {
                    this.produkty.add(new Produkty(produkt));
                }

            } else {
                Log.w(TAG, "Could not fetch produkty: " + exception.getMessage());
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
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

    public List<Produkty> getProdukty() {
        if (produkty == null) {
            throw new DataNotFetched("Fetch produkty in Obchody first.");
        }
        return produkty;
    }

    public void setProdukty(List<Produkty> produkty) {
        this.produkty = produkty;
    }
}
