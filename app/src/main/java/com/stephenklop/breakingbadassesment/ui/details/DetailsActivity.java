package com.stephenklop.breakingbadassesment.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.stephenklop.breakingbadassesment.R;
import com.stephenklop.breakingbadassesment.data.APIService;
import com.stephenklop.breakingbadassesment.data.LocalAppStorage;
import com.stephenklop.breakingbadassesment.domain.Character;
import com.stephenklop.breakingbadassesment.domain.Quote;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    // Create final variables
    private final APIService apiService;

    private LocalAppStorage localAppStorage;
    private String previousActivity;
    private List<Character> characters;
    private List<Quote> quotes;
    private Character character;
    private TextView name, nickname, status, birthdate, occupation, seasons;
    private ImageView poster;
    private int characterId;

    public DetailsActivity() {
        apiService = new APIService();
        localAppStorage = (LocalAppStorage) this.getApplication();
        characters = localAppStorage.getCharacters();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setBackButton();
        setCharacterDetails();
    }

    private void setBackButton() {
        characterId = getIntent().getIntExtra("characterId", -1);
        previousActivity = getIntent().getStringExtra("prevActivity");

        ImageButton backButton = findViewById(R.id.activity_details_imgbtn_backicon);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(v -> {
            try {
                Intent backIntent = new Intent(getApplicationContext(), Class.forName(previousActivity));
                backIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getApplicationContext().startActivity(backIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void setCharacterDetails() {
        System.out.println("Set Character Details");
        name = findViewById(R.id.activity_details_txt_name);
        nickname = findViewById(R.id.activity_details_txt_nickname);
        status = findViewById(R.id.activity_details_txt_status);
        birthdate = findViewById(R.id.activity_details_txt_birthdate);
        occupation = findViewById(R.id.activity_details_txt_occupation);
        seasons = findViewById(R.id.activity_details_txt_seasons);
        poster = findViewById(R.id.activity_details_img);

        System.out.println(characterId);

        if(characterId >= 0) {

            // Load the corresponding character.
            for(Character character : characters) {
                if(character.getmId() == characterId) {
                    this.character = character;
                }
            }

            // Set Name
            name.setText(character.getmName());

            // Set Nickname
            nickname.setText(character.getmNickname());

            // Set Image
            Glide.with(getApplicationContext()).load(character.getmImg()).into(poster);

            // Set Status
            status.setText(character.getmStatus());

            // Set Birthdate
            birthdate.setText(character.getmBirthday());

            // Set Occupation
            occupation.setText(character.getmOccupation());

            // Set Seasons
            seasons.setText(character.getmAppearance());

            // Set Quotes
            setCharacterQuotes(character.getmName());
        }
    }

    private void setCharacterQuotes(String character) {
        new Thread(() -> {
            Looper.prepare();
            quotes = apiService.getQuoteByAuthor(character);

            System.out.println(quotes);
        }).start();
    }
}
