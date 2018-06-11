package sk.tuke.smart.glutenfree.pojo;

/**
 * Created by Dominik on 25.11.2017.
 */

import java.util.Map;
import java.util.HashMap;


public class ObchodPodnik {

    private String objectId;
    private String name;
    private String createdAt;
    private String updatedAt;
    private Integer podnikID;
    private Double longtitude;
    private Double lattitude;
    private Integer stars;
    private String text;
    private Photo photo;
    private String address;
    private String stuff;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPodnikID() {
        return podnikID;
    }

    public void setPodnikID(Integer podnikID) {
        this.podnikID = podnikID;
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

    public String getStuff() {return stuff;}

    public void setStuff(String stuff) {this.stuff = stuff;}

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
