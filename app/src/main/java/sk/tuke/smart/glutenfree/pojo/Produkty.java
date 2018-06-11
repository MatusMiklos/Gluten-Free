package sk.tuke.smart.glutenfree.pojo;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hawk on 25.11.2017.
 */

public class Produkty implements Serializable {
    private static final String TAG = "Produkty";
    private transient ParseObject originalParseObject;
    private String objectId;
    private String typ;
    private List<Obchody> obchody = new ArrayList<>();

    public Produkty() {
    }

    public Produkty(ParseObject produkt) {
        this.objectId = produkt.getObjectId();
        this.typ = produkt.getString("Typ");

        this.originalParseObject = produkt;
    }

    public void fetchPodniky() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("obchody");
        List<ParseObject> obchody = relation.getQuery().find();
        this.obchody = new ArrayList<>();
        for (ParseObject obchod : obchody) {
            this.obchody.add(new Obchody(obchod));
        }
    }

    public void fetchPodnikyInBackground() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("obchody");
        relation.getQuery().findInBackground((obchody, exception) -> {
            if (exception == null) {
                this.obchody = new ArrayList<>();
                for (ParseObject obchod : obchody) {
                    this.obchody.add(new Obchody(obchod));
                }

            } else {
                Log.w(TAG, "Could not fetch obchody: " + exception.getMessage());
            }
        });
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public List<Obchody> getObchody() {
        return obchody;
    }

    public void setObchody(List<Obchody> obchody) {
        this.obchody = obchody;
    }
}
