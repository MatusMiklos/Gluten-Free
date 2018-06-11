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
import sk.tuke.smart.glutenfree.pojo.KategorieJedal;
import sk.tuke.smart.glutenfree.pojo.Podniky;

/**
 * Created by Hawk on 26.11.2017.
 */

public class PodnikyDAO {
    private static final String TAG = "PodnikyDAO";
    private static final PodnikyDAO mInstance = new PodnikyDAO();

    private PodnikyDAO() {
    }

    public static PodnikyDAO getInstance() {
        return mInstance;
    }

    public void getAllPodnikyWithKategorieJedal(DbCallback<Podniky> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Podniky");

        query.findInBackground((podniky, e) -> {
            if (e == null) {
                List<Podniky> podnikyList = new ArrayList<>();
                for (ParseObject podnik : podniky) {
                    Podniky newPodnik = new Podniky(podnik);
                    try {
                        newPodnik.fetchKategorieJedal();
                    } catch (ParseException e1) {
                        Log.w(TAG, "Error fetching kategorieJedal: " + e1.getMessage());
                        callback.call(null, e1);
                    }
                    podnikyList.add(newPodnik);
                }
                callback.call(podnikyList, null);
            } else {
                Log.w(TAG, "Error downloading Podniky: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getFilteredPodnikyWithKategorieJedal(List<String> kategorieJedal, DbCallback<Podniky> callback) {
        ParseQuery<ParseObject> filter = ParseQuery.getQuery("KategorieJedal")
                .whereContainedIn("typ", kategorieJedal);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Podniky")
                .whereMatchesQuery("kategorieJedal", filter);

        query.findInBackground((podniky, e) -> {
            if (e == null) {
                List<Podniky> podnikyList = new ArrayList<>();
                for (ParseObject podnik : podniky) {
                    Podniky newPodnik = new Podniky(podnik);
                    try {
                        newPodnik.fetchKategorieJedal();
                    } catch (ParseException e1) {
                        Log.w(TAG, "Error fetching kategorieJedal: " + e1.getMessage());
                        callback.call(null, e1);
                    }
                    podnikyList.add(newPodnik);
                }
                callback.call(podnikyList, null);
            } else {
                Log.w(TAG, "Error downloading Podniky: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getAllPodniky(DbCallback<Podniky> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Podniky");

        query.findInBackground((podniky, e) -> {
            if (e == null) {
                List<Podniky> podnikyList = new ArrayList<>();
                for (ParseObject podnik : podniky) {
                    podnikyList.add(new Podniky(podnik));
                }
                callback.call(podnikyList, null);
            } else {
                Log.w(TAG, "Error downloading Podniky: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getFilteredPodniky(List<String> kategorieJedal, DbCallback<Podniky> callback) {
        ParseQuery<ParseObject> filter = ParseQuery.getQuery("KategorieJedal")
                .whereContainedIn("typ", kategorieJedal);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Podniky")
                .whereMatchesQuery("kategorieJedal", filter);

        query.findInBackground((podniky, e) -> {
            if (e == null) {
                List<Podniky> podnikyList = new ArrayList<>();
                for (ParseObject podnik : podniky) {
                    podnikyList.add(new Podniky(podnik));
                }
                callback.call(podnikyList, null);
            } else {
                Log.w(TAG, "Error downloading Podniky: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getAllKategorieJedalWithPodniky(DbCallback<KategorieJedal> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("KategorieJedal");

        query.findInBackground((kategorieJedal, e) -> {
            if (e == null) {
                List<KategorieJedal> kategorieJedalList = new ArrayList<>();
                for (ParseObject kategoriaJedla : kategorieJedal) {
                    KategorieJedal newKategoriaJedla = new KategorieJedal(kategoriaJedla);
                    try {
                        newKategoriaJedla.fetchPodniky();
                    } catch (ParseException e1) {
                        Log.w(TAG, "Error fetching podniky: " + e1.getMessage());
                        callback.call(null, e1);
                    }
                    kategorieJedalList.add(newKategoriaJedla);
                }
                callback.call(kategorieJedalList, null);
            } else {
                Log.w(TAG, "Error downloading KategorieJedal: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void getAllKategorieJedal(DbCallback<KategorieJedal> callback) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("KategorieJedal");

        query.findInBackground((kategorieJedal, e) -> {
            if (e == null) {
                List<KategorieJedal> kategorieJedalList = new ArrayList<>();
                for (ParseObject kategoriaJedla : kategorieJedal) {
                    kategorieJedalList.add(new KategorieJedal(kategoriaJedla));
                }
                callback.call(kategorieJedalList, null);
            } else {
                Log.w(TAG, "Error downloading KategorieJedal: " + e.getMessage());
                callback.call(null, e);
            }
        });
    }

    public void createPodniky(String name, double longitude, double latitude, int stars, String text, String address, File file, List<String> categories) {
        ParseObject podnik = new ParseObject("Podniky");
        podnik.put("Name", name);
        podnik.put("Longtitude", longitude);
        podnik.put("Lattitude", latitude);
        podnik.put("Stars", stars);
        podnik.put("Text", text);
        podnik.put("Address", address);
        if (file != null)
            podnik.put("Photo", new ParseFile(file));

        ParseQuery<ParseObject> query = ParseQuery.getQuery("KategorieJedal")
                .whereContainedIn("typ", categories);

        query.findInBackground((kategorieJedal, e) -> {
            if (e == null) {
                ParseRelation<ParseObject> relation = podnik.getRelation("kategorieJedal");
                for (ParseObject kategoriaJedla : kategorieJedal) {
                    relation.add(kategoriaJedla);
                }

                try {
                    podnik.save();
                } catch (ParseException saveException) {
                    Log.e(TAG, "Could not save Podniky: " + saveException.getMessage());
                }
            } else {
                Log.w(TAG, "Error downloading KategorieJedal: " + e.getMessage());
            }
        });
    }

    public void createPodniky(Podniky podnik, File photo, List<String> categories) {
        createPodniky(podnik.getName(),
                podnik.getLongtitude(),
                podnik.getLattitude(),
                podnik.getStars(),
                podnik.getText(),
                podnik.getAddress(),
                photo,
                categories
                );
    }
}
