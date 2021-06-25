package com.stephenklop.breakingbadassesment.ui.details;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final LocalAppStorage localAppStorage;

    // Create variables
    private String previousActivity;
    private Character currentCharacter;
    private int characterId;
    private TextView name, nickname, status, birthdate, occupation, seasons;
    private ImageView poster;

    // Create variables for our UI components
    private RecyclerView quotesRV;

    // Create variables for our adapter class and array list
    private QuoteAdapter mAdapter;
    private List<Character> mCharacters;
    private List<Quote> mQuotes;

    public DetailsActivity() {
        apiService = new APIService();
        localAppStorage = (LocalAppStorage) this.getApplication();
        mCharacters = localAppStorage.getCharacters();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "On creation of the details page");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Set back button
        setBackButton();

        // Initializing our variables
        quotesRV = findViewById(R.id.activity_details_rv_quotes);

        // Set our character details
        setCharacterDetails();

        // Create threads
        Thread quotesThread = new Thread(() -> {
            mQuotes = apiService.getQuoteByAuthor(currentCharacter.getmName());
        });

        Thread adapterThread = new Thread(() -> {
            buildQuoteRecyclerView();
        });

        // Start and join the threads
        try {
           quotesThread.start();
           quotesThread.join();

           adapterThread.start();
           adapterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setBackButton() {
        Log.d(LOG_TAG, "Set back button on details page");

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
        Log.d(LOG_TAG, "Set character details on details page");
        name = findViewById(R.id.activity_details_txt_name);
        nickname = findViewById(R.id.activity_details_txt_nickname);
        status = findViewById(R.id.activity_details_txt_status);
        birthdate = findViewById(R.id.activity_details_txt_birthdate);
        occupation = findViewById(R.id.activity_details_txt_occupation);
        seasons = findViewById(R.id.activity_details_txt_seasons);
        poster = findViewById(R.id.activity_details_img);

        if(characterId >= 0) {

            // Load the corresponding character.
            for(Character character : mCharacters) {
                if(character.getmId() == characterId) {
                    this.currentCharacter = character;
                }
            }

            // Set Name
            name.setText(currentCharacter.getmName());

            // Set Nickname
            nickname.append(" " + currentCharacter.getmNickname());

            // Set Image
            Glide.with(getApplicationContext()).load(currentCharacter.getmImg()).into(poster);

            // Set Status
            status.append(" " + currentCharacter.getmStatus());

            // Set Birthdate
            birthdate.append(" " + currentCharacter.getmBirthday());

            // Set Occupation
            occupation.append(" " + currentCharacter.getmOccupation());

            // Set Seasons
            seasons.append(" " + currentCharacter.getmAppearance());
        }
    }

    private void buildQuoteRecyclerView() {
        Log.d(LOG_TAG, "Build quote recycler view on homepage");

        // Initializing our adapter class
        mAdapter = new QuoteAdapter(mQuotes, this);

        // Adding layout manager to our recycler view
        quotesRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

        if(mQuotes.size() != 0) {
            // Connecting adapter to our recycler view
            findViewById(R.id.activity_details_not_items_found).setVisibility(View.INVISIBLE);
            quotesRV.setAdapter(mAdapter);
        } else {
            findViewById(R.id.activity_details_not_items_found).setVisibility(View.VISIBLE);
        }
    }
}
