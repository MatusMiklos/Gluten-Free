package sk.tuke.smart.glutenfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.tuke.smart.glutenfree.pojo.Obchody;
import sk.tuke.smart.glutenfree.pojo.Podniky;

public class DetailActivity extends AppCompatActivity {
    private Podniky podnik;
    private Obchody obchod;
    @BindView(R.id.detail_name) TextView detail_name;
    @BindView(R.id.detail_text) TextView detail_text;
    @BindView(R.id.detail_address) TextView detail_address;
    @BindView(R.id.star1) ImageView star1;
    @BindView(R.id.star2) ImageView star2;
    @BindView(R.id.star3) ImageView star3;
    @BindView(R.id.star4) ImageView star4;
    @BindView(R.id.star5) ImageView star5;
    @BindView(R.id.imageView) ImageView photoView;
    private Integer numberOfStars;
    private String foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

        podnik = (Podniky)getIntent().getSerializableExtra("podnik");
        obchod = (Obchody) getIntent().getSerializableExtra("obchod");


        if (podnik != null){
            detail_name.setText(podnik.getName());
            detail_text.setText(podnik.getText());
            detail_address.setText(podnik.getAddress());
            numberOfStars = podnik.getStars();
            if(podnik.getPhoto() == null){
                Log.i("detailActivity", "url !!!!!!!!!!!!! som null");
            }else {
                foto = podnik.getPhoto().getUrl();
            }
        }
        else if (obchod != null){
            detail_name.setText(obchod.getName());
            detail_text.setText(obchod.getText());
            detail_address.setText(obchod.getAddress());
            numberOfStars = obchod.getStars();
            if(obchod.getPhoto() == null){
                Log.i("detailActivity", "url !!!!!!!!!!!!! som null");
            }else {
                foto = obchod.getPhoto().getUrl();
            }
        }

        Log.i("detailActivity", "url: " + foto);

        Picasso.with(getApplicationContext())
                .load(foto)
                .resize(825,800).into(photoView);

        if (numberOfStars >= 1){
            star1.setImageResource(android.R.drawable.star_big_on);
        }
        if (numberOfStars >= 2){
            star2.setImageResource(android.R.drawable.star_big_on);
        }
        if (numberOfStars >= 3) {
            star3.setImageResource(android.R.drawable.star_big_on);
        }
        if (numberOfStars >= 4) {
            star4.setImageResource(android.R.drawable.star_big_on);
        }
        if (numberOfStars >= 5) {
           star5.setImageResource(android.R.drawable.star_big_on);
        }

    }
}
