package sk.tuke.smart.glutenfree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sk.tuke.smart.glutenfree.dao.ObchodyDAO;
import sk.tuke.smart.glutenfree.dao.PodnikyDAO;
import sk.tuke.smart.glutenfree.pojo.Category;
import sk.tuke.smart.glutenfree.pojo.KategorieJedal;
import sk.tuke.smart.glutenfree.pojo.Obchody;
import sk.tuke.smart.glutenfree.pojo.Photo;
import sk.tuke.smart.glutenfree.pojo.Podniky;
import sk.tuke.smart.glutenfree.pojo.Produkty;


public class FormActivity extends AppCompatActivity {
    private static final String TAG = "FormActivity";
    private static final int GET_FROM_GALLERY = 3;
    @BindView(R.id.editText4)
    EditText editText_URI;
    @BindView(R.id.editText2)
    EditText nazov;
    @BindView(R.id.editText3)
    EditText popis;
    @BindView(R.id.editText)
    EditText adresa;
    @BindView(R.id.form_button_send)
    Button odosli;


    private Category category;
    private Obchody obchod;
    private Podniky podnik;
    private ArrayList<String> additionalProperties;
    private static ArrayList<String> staticProperties;
    ImageButton imageButton, imageButton2, imageButton3, imageButton4, imageButton5;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        imageButton = (ImageButton) findViewById(R.id.form_imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.form_imageButton1);
        imageButton3 = (ImageButton) findViewById(R.id.form_imageButton2);
        imageButton4 = (ImageButton) findViewById(R.id.form_imageButton3);
        imageButton5 = (ImageButton) findViewById(R.id.form_imageButton4);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
        editText = (EditText) findViewById(R.id.editText);



        View view = getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (savedInstanceState != null) {

            category = (Category) savedInstanceState.getSerializable("category");
            if (category != null) {
                switch (category) {
                    case PODNIK:
                        podnik = (Podniky) savedInstanceState.getSerializable("podnik");
                        break;
                    case OBCHOD:
                        obchod = (Obchody) savedInstanceState.getSerializable("obchod");
                        break;
                }
            }
            additionalProperties = savedInstanceState.getStringArrayList("additionalProperties");
        } else {
            category = (Category) intent.getSerializableExtra("kategoria");
            switch (category) {
                case PODNIK:
                    podnik = new Podniky();
                    break;
                case OBCHOD:
                    obchod = new Obchody();
                    break;
            }
        }

        additionalProperties = intent.getStringArrayListExtra("zoznamPotravin");

        }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @OnClick(R.id.form_button_send)
    public void onClickOdosli(View v){
        if(category!= null){
            Photo photo = new Photo();
            photo.setUrl(editText_URI.getText().toString());
            switch (category) {
                case PODNIK:
                    podnik.setName(nazov.getText().toString());
                    podnik.setText(popis.getText().toString());
                    podnik.setPhoto(photo);
                    break;
                case OBCHOD:
                    obchod.setName(nazov.getText().toString());
                    obchod.setText(popis.getText().toString());
                    obchod.setPhoto(photo);
                    break;
            }
        }
        switch (category) {
            case PODNIK:
                PodnikyDAO.getInstance().createPodniky(podnik, new File(URI.create("file://"+this.editText_URI.getText().toString())), additionalProperties);
                break;
            case OBCHOD:
                ObchodyDAO.getInstance().createObchody(obchod, new File(URI.create("file://"+this.editText_URI.getText().toString())), additionalProperties);
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

        @OnClick(R.id.form_coordinates_button)
        public void onClickCoordinatesButton (View view){
            Intent intent = new Intent(this, MapsActivity.class);
            if(category!= null){
                Photo photo = new Photo();
                photo.setUrl(editText_URI.getText().toString());
                switch (category) {
                    case PODNIK:
                        podnik.setName(nazov.getText().toString());
                        podnik.setText(popis.getText().toString());
                        podnik.setPhoto(photo);
                        intent.putExtra("podnik", podnik);
                        break;
                    case OBCHOD:
                        obchod.setName(nazov.getText().toString());
                        obchod.setText(popis.getText().toString());
                        obchod.setPhoto(photo);
                        intent.putExtra("obchod", obchod);
                        break;
                }
            }
            intent.putStringArrayListExtra("additional",additionalProperties);
            intent.putExtra("kategoria",category);
            startActivity(intent);
        }


        @OnClick(R.id.form_upload_button)
        public void onClickUploadButton (View view){
            startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        }

        @OnClick (R.id.form_imageButton)
        public void oneStar(View view){
            imageButton.setImageResource(R.drawable.star);
            imageButton2.setImageResource(R.drawable.star_outline);
            imageButton3.setImageResource(R.drawable.star_outline);
            imageButton4.setImageResource(R.drawable.star_outline);
            imageButton5.setImageResource(R.drawable.star_outline);
            switch (category) {
                case PODNIK:
                    podnik.setStars(1);
                    break;
                case OBCHOD:
                    obchod.setStars(1);
                    break;
            }
        }

    @OnClick (R.id.form_imageButton1)
    public void twoStars(View view){
        imageButton.setImageResource(R.drawable.star);
        imageButton2.setImageResource(R.drawable.star);
        imageButton3.setImageResource(R.drawable.star_outline);
        imageButton4.setImageResource(R.drawable.star_outline);
        imageButton5.setImageResource(R.drawable.star_outline);
        switch (category) {
            case PODNIK:
                podnik.setStars(2);
                break;
            case OBCHOD:
                obchod.setStars(2);
                break;
        }
    }

    @OnClick (R.id.form_imageButton2)
    public void threeStars(View view){
        imageButton.setImageResource(R.drawable.star);
        imageButton2.setImageResource(R.drawable.star);
        imageButton3.setImageResource(R.drawable.star);
        imageButton4.setImageResource(R.drawable.star_outline);
        imageButton5.setImageResource(R.drawable.star_outline);
        switch (category) {
            case PODNIK:
                podnik.setStars(3);
                break;
            case OBCHOD:
                obchod.setStars(3);
                break;
        }
    }

    @OnClick (R.id.form_imageButton3)
    public void fourStar(View view){
        imageButton.setImageResource(R.drawable.star);
        imageButton2.setImageResource(R.drawable.star);
        imageButton3.setImageResource(R.drawable.star);
        imageButton4.setImageResource(R.drawable.star);
        imageButton5.setImageResource(R.drawable.star_outline);
        switch (category) {
            case PODNIK:
                podnik.setStars(4);
                break;
            case OBCHOD:
                obchod.setStars(4);
                break;
        }
    }

    @OnClick (R.id.form_imageButton4)
    public void fiveStar(View view){
        imageButton.setImageResource(R.drawable.star);
        imageButton2.setImageResource(R.drawable.star);
        imageButton3.setImageResource(R.drawable.star);
        imageButton4.setImageResource(R.drawable.star);
        imageButton5.setImageResource(R.drawable.star);
        switch (category) {
            case PODNIK:
                podnik.setStars(5);
                break;
            case OBCHOD:
                obchod.setStars(5);
                break;
        }
    }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);


            //Detects request codes
            if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Chytil ma");
                Uri selectedImage = data.getData();
                String imageURI = getRealPathFromURI(selectedImage);
                editText_URI.setText(imageURI);
                Photo photo = new Photo();
                photo.setUrl(imageURI);
                switch (category){

                    case PODNIK:
                        podnik.setPhoto(photo);
                        break;
                    case OBCHOD:
                        obchod.setPhoto(photo);
                        break;
                }
                //Picasso.with(getApplicationContext()).load(selectedImage);
            }
        }


        @Override
        protected void onSaveInstanceState (Bundle outState){
            Log.i(TAG, " onSave");

            if (category!=null) {
                outState.putSerializable("category", category);
                switch (category) {
                    case PODNIK:
                        outState.putSerializable("podnik", podnik);
                        break;
                    case OBCHOD:
                        outState.putSerializable("obchod", obchod);
                        break;
                }
            }
            outState.putStringArrayList("additionalProperties", additionalProperties);

            super.onSaveInstanceState(outState);
        }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra("sent",false)) {
            podnik = (Podniky) intent.getSerializableExtra("podnik");
            obchod = (Obchody) intent.getSerializableExtra("obchod");
            additionalProperties = intent.getStringArrayListExtra("additional");
            if (podnik != null)
                category = Category.PODNIK;
            else if (obchod != null)
                category = Category.OBCHOD;
            if (category != null) {

                switch (category) {
                    case PODNIK:
                        nazov.setText(podnik.getName());
                        popis.setText(podnik.getText());
                        if (podnik.getPhoto()!=null){
                            editText_URI.setText(podnik.getPhoto().getUrl());
                        }
                        podnik.setLattitude(intent.getDoubleExtra("latitude", 0));
                        podnik.setLongtitude(intent.getDoubleExtra("longitude", 0));
                        podnik.setAddress(intent.getStringExtra("address"));

                        break;
                    case OBCHOD:
                        nazov.setText(obchod.getName());
                        popis.setText(obchod.getText());
                        if (obchod.getPhoto()!=null){
                            editText_URI.setText(obchod.getPhoto().getUrl());
                        }
                        obchod.setLattitude(intent.getDoubleExtra("latitude", 0));
                        obchod.setLongtitude(intent.getDoubleExtra("longitude", 0));
                        obchod.setAddress(intent.getStringExtra("address"));
                        break;
                }
            }

            editText.setText(intent.getStringExtra("address"));
        }
        super.onResume();
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
