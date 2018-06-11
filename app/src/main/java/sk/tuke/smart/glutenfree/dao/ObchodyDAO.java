package sk.tuke.smart.glutenfree.dao;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sk.tuke.smart.glutenfree.dao.callbacks.DbCallback;
import sk.tuke.smart.glutenfree.pojo.Obchody;
import sk.tuke.smart.glutenfree.pojo.Produkty;

/**
 * Created by Hawk on 26.11.2017.
 */

public class ObchodyDAO {
    private final String TAG = "ObchodyDAO";
    private static final ObchodyDAO ourInstance = new ObchodyDAO();

    public static ObchodyDAO getInstance() {
        return ourInstance;
    }

    private ObchodyDAO() {
    }

    public void getAllObchodyWithProdukty(DbCallback<Obchody> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Obchody");

        downloadObchodyWithProdukty(callback, query);
    }

    public void getFilteredObchodyWithProdukty(List<String> produkty, DbCallback<Obchody> callback) {
        ParseQuery<ParseObject> filter = ParseQuery.getQuery("Produkty")
                .whereContainedIn("Typ", produkty);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Obchody")
                .whereMatchesQuery("produkty", filter);

        downloadObchodyWithProdukty(callback, query);
    }

    private void downloadObchodyWithProdukty(DbCallback<Obchody> callback, ParseQuery<ParseObject> query) {
        query.findInBackground((obchody, e) -> {
            if (e == null) {
                List<Obchody> obchodyList = new ArrayList<>();
                for (ParseObject obchod : obchody) {
                    Obchody newObchod = new Obchody(obchod);
                    try {
                        newObchod.fetchProdukty();
                    } catch (ParseException e1) {
                        Log.w(TAG, "Error fetching produkty: " + e1.getMessage());
                        callback.call(null, e1);
                    }
                    obchodyList.add(newObchod);
                }
                callback.call(obchodyList, null);
            } else {
                Log.w(TAG, "Error downloading Obchody: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getAllObchody(DbCallback<Obchody> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Obchody");

        downloadObchody(callback, query);
    }

    private void downloadObchody(DbCallback<Obchody> callback, ParseQuery<ParseObject> query) {
        query.findInBackground((obchody, e) -> {
            if (e == null) {
                List<Obchody> obchodyList = new ArrayList<>();
                for (ParseObject obchod : obchody) {
                    obchodyList.add(new Obchody(obchod));
                }
                callback.call(obchodyList, null);
            } else {
                Log.w(TAG, "Error downloading Obchody: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getFilteredObchody(List<String> produkty,DbCallback<Obchody> callback) {
        ParseQuery<ParseObject> filter = ParseQuery.getQuery("Produkty")
                .whereContainedIn("Typ", produkty);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Obchody")
                .whereMatchesQuery("produkty", filter);

        downloadObchody(callback, query);
    }

    public void getAllProduktyWithObchody(DbCallback<Produkty> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Produkty");

        query.findInBackground((produkty, e) -> {
            if (e == null) {
                List<Produkty> produktyList = new ArrayList<>();
                for (ParseObject produkt : produkty) {
                    Produkty newProdukt = new Produkty(produkt);
                    try {
                        newProdukt.fetchPodniky();
                    } catch (ParseException e1) {
                        Log.w(TAG, "Error fetching podniky: " + e1.getMessage());
                        callback.call(null, e1);
                    }
                    produktyList.add(newProdukt);
                }
                callback.call(produktyList, null);
            } else {
                Log.w(TAG, "Error downloading Produkty: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getAllProdukty(DbCallback<Produkty> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Produkty");

        query.findInBackground((produkty, e) -> {
            if (e == null) {
                List<Produkty> produktyList = new ArrayList<>();
                for (ParseObject produkt : produkty) {
                    produktyList.add(new Produkty(produkt));
                }
                callback.call(produktyList, null);
            } else {
                Log.w(TAG, "Error downloading Produkty: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void createObchody(String name, double longitude, double latitude, int stars, String text, String address, File file, List<String> products) {
        ParseObject obchod = new ParseObject("Obchody");
        obchod.put("Name", name);
        obchod.put("Longtitude", longitude);
        obchod.put("Lattitude", latitude);
        obchod.put("Stars", stars);
        obchod.put("Text", text);
        obchod.put("Address", address);
        if (file != null)
            obchod.put("Photo", new ParseFile(file));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Produkty")
                .whereContainedIn("Typ", products);

        query.findInBackground((produkty, e) -> {
            if (e == null) {
                ParseRelation<ParseObject> relation = obchod.getRelation("produkty");
                for (ParseObject produkt : produkty) {
                    relation.add(produkt);
                }

                try {
                    obchod.save();
                } catch (ParseException saveException) {
                    Log.e(TAG, "Could not save Obchody: " + saveException.getMessage());
                }
            } else {
                Log.w(TAG, "Error downloading Produkty: " + e.getMessage());
            }
        });
    }

    public void createObchody(Obchody obchod, File photo, List<String> products) {
        createObchody(obchod.getName(),
                obchod.getLongtitude(),
                obchod.getLattitude(),
                obchod.getStars(),
                obchod.getText(),
                obchod.getAddress(),
                photo,
                products
        );
    }
}
