package com.example.ravin.speechtotext;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        ed2 = findViewById(R.id.editText2);
        b1=(Button)findViewById(R.id.button);

        final Activity activity = this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PlaceDetailsActivity.class);
                intent.putExtra("placeDetails", ed1.getText().toString());
                intent.putExtra("title", ed2.getText().toString());
                startActivity(intent);
            }
        });

//        Intent intent = new Intent(this, MapActivity.class);
//        intent.putExtra("latitude", 20);
//        intent.putExtra("longitude", 120);
//        startActivity(intent);
    }

}

