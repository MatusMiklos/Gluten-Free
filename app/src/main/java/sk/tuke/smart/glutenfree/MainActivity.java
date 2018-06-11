package sk.tuke.smart.glutenfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button add_button;
    Button find_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        find_button=(Button) findViewById(R.id.button_find);
        add_button=(Button) findViewById(R.id.button_add);

        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent find = new Intent(getApplicationContext(), MenuActivity.class);
                find.putExtra("action", "find");
                startActivity(find );
            }});

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), MenuActivity.class);
                add.putExtra("action", "add");
                startActivity(add);
            }});
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));

    }
}
