package sk.tuke.smart.glutenfree;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import sk.tuke.smart.glutenfree.pojo.Category;

public class MenuActivity extends AppCompatActivity {
    ArrayList listPodniky = new ArrayList();
    ArrayList listObchody = new ArrayList();

    Button restauracie;
    Button obchody;
    Button odosli;

    boolean weNeedToGoBack;

    ListView potravinylist;
    LayoutInflater inflater;
    View dialoglayout, itemlayout;
    String action; //find, add

    //String kategoria="";
    Category kategoria;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }



    //---Vyhodi ListView---
    private View.OnClickListener listener(ArrayList listObPod, Category obPod) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kategoria=obPod;
                potravinovyList(listObPod,obPod);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MenuActivity.this);
                 dialog = alertDialogBuilder.create();

                if (dialoglayout.getParent() == null) {
                    alertDialogBuilder.setView(dialoglayout);
                } else {
                    dialoglayout = null; //set it to null
                    dialoglayout = inflater.inflate(R.layout.list_view, null);// now initialized yourView and its component again
                    odosli = (Button) dialoglayout.findViewById(R.id.button1);
                    if(action.equals("find")){
                        odosli.setOnClickListener(listener2("list"));
                    }
                    else if(action.equals("add")){
                        odosli.setOnClickListener(listener2("form"));
                    }
                    potravinylist = (ListView) dialoglayout.findViewById(R.id.listView1);
                    potravinovyList(listObPod,obPod);
                    alertDialogBuilder.setView(dialoglayout);
                }

                alertDialogBuilder.show();

            }
        };}

    private void potravinovyList(ArrayList listObPod, Category obPod) { //NAPLNI TO LISTVIEW

        //final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.row, android.R.layout.simple_list_item_1, listPodniky);
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, listObPod);
            potravinylist.setAdapter(adapter);
            potravinylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        }


    private View.OnClickListener listener2(String aktivita) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> zaskrtnute = new ArrayList<>();

                //String checked = "";


                SparseBooleanArray checkedItems = potravinylist.getCheckedItemPositions();
                if (checkedItems.size() >0) {
                    for (int i=0; i<checkedItems.size(); i++) {
                        if (checkedItems.valueAt(i)) {
                            String item = potravinylist.getAdapter().getItem(
                                    checkedItems.keyAt(i)).toString();
                            zaskrtnute.add(item);
                        }
                    }


                }


                Intent spustList;
                if(aktivita.equals("list"))
                    spustList=new Intent(getApplicationContext(), ListActivity.class);
                else
                    spustList=new Intent(getApplicationContext(), FormActivity.class);
                spustList.putExtra("kategoria",kategoria); //podnik, obchod
                spustList.putStringArrayListExtra("zoznamPotravin",zaskrtnute);
                weNeedToGoBack = true;
                startActivity(spustList);


            }
        };}


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("action", action);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        if(weNeedToGoBack == true)
        {
            finish();
//            Intent intent = new Intent(this, MainActivity.class);
       }

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));


        inflater=getLayoutInflater();


        // Pull out what kind of intent it is
        action = getIntent().getStringExtra("action");



        listPodniky.add("Cestoviny");
        listPodniky.add("Dezerty");
        listPodniky.add("Mäso");
        listPodniky.add("Pizza");
        listPodniky.add("Polievky");
        listPodniky.add("Ryby");
        listPodniky.add("Slovenská kuchyňa");
        listPodniky.add("Vege");

        listObchody.add("Alkohol");
        listObchody.add("Cestoviny");
        listObchody.add("Múka");
        listObchody.add("Pečivo");
        listObchody.add("Sladkosti");
        listObchody.add("Slané");




        inflater=getLayoutInflater();

        restauracie=(Button) findViewById(R.id.menu_button_restaurants);
        restauracie.setOnClickListener(listener(listPodniky,Category.PODNIK));
        obchody=(Button) findViewById(R.id.menu_button_shops);
        obchody.setOnClickListener(listener(listObchody,Category.OBCHOD));

        dialoglayout = inflater.inflate(R.layout.list_view, null); //to false by malo fixnut exception???
        potravinylist = (ListView) dialoglayout.findViewById(R.id.listView1);
        odosli = (Button) dialoglayout.findViewById(R.id.button1);

        //itemlayout = inflater.inflate(R.layout.list_item, null); //to false by malo fixnut exception???


        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        TextView textView = findViewById(R.id.textView2);

        if(action.equals("find")){
            odosli.setOnClickListener(listener2("list"));
            textView.setText(R.string.menu_button_find);

        }
        else if(action.equals("add")){
            odosli.setOnClickListener(listener2("form"));
            textView.setText(R.string.menu_button_add_shop);
        }




        //itemlayout = inflater.inflate(R.layout.list_item, null); //to false by malo fixnut exception???


        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        super.onResume();
    }


}
