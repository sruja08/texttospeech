package com.example.ravin.speechtotext;

import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class PlaceDetailsActivity extends AppCompatActivity {
    public static final String TAG = "myapp";

    TextToSpeech textToSpeechEngine;
    TextView textView, titleView;
    Button textToSpeechBtn;
    Spinner spinner;
    String[] supportedLanguages = new String[]{"English", "French", "German", "Japanese"};

    private String title = "";
    private String placeDetails = "";
    private Locale language = Locale.ENGLISH;

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        // Initialize text to speech engine
        textToSpeechEngine = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeechEngine.setLanguage(language);
                }
            }
        });

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        placeDetails = intent.getStringExtra("placeDetails");

        // Initialize views
        textView=(TextView) findViewById(R.id.detailText);
        textView.setText(placeDetails);

        titleView = findViewById(R.id.title);
        titleView.setText(title);

        textToSpeechBtn=(Button)findViewById(R.id.textToSpeechBtn);
        textToSpeechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = textView.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                textToSpeechEngine.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        SpinnerAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, supportedLanguages);

        spinner = findViewById(R.id.dropDown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String languageTag = ((TextView) view).getText().toString();
                Locale selectedLanguage = Locale.ENGLISH;
                switch (languageTag) {
                    case "English": selectedLanguage = Locale.ENGLISH;
                        break;
                    case "German": selectedLanguage = Locale.GERMAN;
                        break;
                    case "French": selectedLanguage = Locale.FRENCH;
                        break;
                    case "Japanese": selectedLanguage = Locale.JAPANESE;
                        break;
                }

                Log.d(TAG, "Langulage selected: "+selectedLanguage.toString());
                // todo: get text for selected language
                onLanguageChange(selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void onLanguageChange(Locale selectedLanguage) {
        language = selectedLanguage;
        if (language != null) {
            textToSpeechEngine.setLanguage(language);
            Log.d(TAG, "Text to speech engine language: "
                    +textToSpeechEngine.getAvailableLanguages());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public void onPause(){
        if(textToSpeechEngine !=null){
            textToSpeechEngine.stop();
            textToSpeechEngine.shutdown();
        }
        super.onPause();
    }
}
