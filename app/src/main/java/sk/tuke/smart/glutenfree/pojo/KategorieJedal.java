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

public class KategorieJedal implements Serializable {
    private static final String TAG = "KategorieJedal";
    private transient ParseObject originalParseObject;
    private String objectId;
    private String typ;
    private List<Podniky> podniky = null;

    public KategorieJedal() {
    }

    public KategorieJedal(ParseObject kategoriaJedla) {
        this.objectId = kategoriaJedla.getObjectId();
        this.typ = kategoriaJedla.getString("typ");

        this.originalParseObject = kategoriaJedla;
    }

    public void fetchPodniky() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("podniky");
        List<ParseObject> podniky = relation.getQuery().find();
        this.podniky = new ArrayList<>();
        for (ParseObject podnik : podniky) {
            this.podniky.add(new Podniky(podnik));
        }
    }

    public void fetchPodnikyInBackground() throws ParseException {
        ParseRelation<ParseObject> relation = originalParseObject.getRelation("podniky");
        relation.getQuery().findInBackground((podniky, exception) -> {
            if (exception == null) {
                this.podniky = new ArrayList<>();
                for (ParseObject podnik : podniky) {
                    this.podniky.add(new Podniky(podnik));
                }

            } else {
                Log.w(TAG, "Could not fetch podniky: " + exception.getMessage());
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

    public List<Podniky> getPodniky() throws DataNotFetched {
        if (podniky == null) {
            throw new DataNotFetched("Fetch podniky in KategorieJedal first.");
        }
        return podniky;
    }

    public void setPodniky(List<Podniky> podniky) {
        this.podniky = podniky;
    }
}
