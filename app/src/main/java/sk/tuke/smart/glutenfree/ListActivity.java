package sk.tuke.smart.glutenfree;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sk.tuke.smart.glutenfree.adapters.ListAdapter;
import sk.tuke.smart.glutenfree.adapters.ListAdapterObchod;
import sk.tuke.smart.glutenfree.dao.ObchodyDAO;
import sk.tuke.smart.glutenfree.dao.PodnikyDAO;
import sk.tuke.smart.glutenfree.pojo.Category;
import sk.tuke.smart.glutenfree.pojo.Obchody;
import sk.tuke.smart.glutenfree.pojo.Podniky;

public class ListActivity extends AppCompatActivity {
    ListAdapter listAdapter;
    ListAdapterObchod listAdapterObchod;
    List<Obchody> obchodyList;
    List<Podniky> podnikyList;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    Category category;
    ArrayList<String> checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        checked =  getIntent().getStringArrayListExtra("zoznamPotravin");
        floatingActionButton = (FloatingActionButton) findViewById(R.id.list_floatingActionButton);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        recyclerView = (RecyclerView) findViewById(R.id.list);

        category = (Category) getIntent().getSerializableExtra("kategoria");
        if (category==Category.OBCHOD){
            obchodyList = new ArrayList<>();
            listAdapterObchod = new ListAdapterObchod(obchodyList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(listAdapterObchod);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            ObchodyDAO.getInstance().getFilteredObchodyWithProdukty(checked,(obchody, e) -> {
                if (e == null) {
                    //TODO osetrim podniky
                    obchodyList = obchody;
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            intent.putExtra("obchody", (Serializable) obchodyList);
                            startActivity(intent);
                        }
                    });
                    listAdapterObchod.addAll(obchodyList);
                    //...
                } else {
                    //todo osetrit exception
                }
            });

        } else {

            podnikyList = new ArrayList<>();
            listAdapter = new ListAdapter(podnikyList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(listAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            PodnikyDAO.getInstance().getFilteredPodnikyWithKategorieJedal(checked,(podniky, e) -> {
                if (e == null) {
                    //TODO osetrim podniky
                    podnikyList = podniky;
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            intent.putExtra("podniky", (Serializable) podnikyList);
                            startActivity(intent);
                        }
                    });
                    listAdapter.addAll(podnikyList);
                    //...
                } else {
                    //todo osetrit exception
                }
            });

        }



//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Obchody");
//        query.whereEqualTo("Name", "Tesco");
//
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> obchody, ParseException e) {
//                if (e == null) {
//
//                } else {
//                    Log.i("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//

    }
}
