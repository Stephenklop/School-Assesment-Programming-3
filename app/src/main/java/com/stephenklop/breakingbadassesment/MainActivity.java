package com.stephenklop.breakingbadassesment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.stephenklop.breakingbadassesment.data.APIService;
import com.stephenklop.breakingbadassesment.data.LocalAppStorage;
import com.stephenklop.breakingbadassesment.domain.Character;
import com.stephenklop.breakingbadassesment.logic.LocaleHelper;
import com.stephenklop.breakingbadassesment.ui.home.CharacterAdapter;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Create final variables
    private final APIService apiService;
    private final LocalAppStorage localAppStorage;

    // Create variables
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private LocalDateTime timePause;
    private Context context;
    private Resources resources;

    // Creating variables for our UI components
    private RecyclerView characterRV;

    // Variable for our adapter class and array list
    private CharacterAdapter mAdapter;
    private List<Character> mCharacters;
    private View mTitleBar;
    private ImageView mFlagIconEnglish, mFlagIconDutch;

    public MainActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        apiService = new APIService();
        sharedPreferences = getSharedPreferences("SharedPref", MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set language toggle
        languageToggle();

        // Set search view
        setSearchBar();

        // Initializing our variables
        characterRV = findViewById(R.id.activity_main_rv_items);

        // Create threads
        Thread charactersThread = new Thread(() -> {
            Looper.prepare();
            mCharacters = apiService.getAllCharacters();
            localAppStorage.setCharacters(mCharacters);
            Toast.makeText(getApplicationContext(), mCharacters.size() + " items ingeladen!", Toast.LENGTH_LONG).show();
        });

        Thread adapterThread = new Thread(() -> {
            // Calling the method to build the recyclerview
            buildRecyclerView();
        });

        // Start and join the threads
        try {
            charactersThread.start();
            charactersThread.join();

            adapterThread.start();
            adapterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setSearchBar() {
        SearchView searchView = (SearchView) findViewById(R.id.fragment_search_searchInput);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Inside this method we are calling the method to filter our recycler view
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        // Creating a new array list to filter our data
        ArrayList<Character> filteredList = new ArrayList<>();

        // Running a for loop to compare elements
        for(Character character : mCharacters) {

            // Checking if the given string matches with the title of any item in our recycler view
            if(character.getmName().toLowerCase().contains(text.toLowerCase())) {

                // If the item is matched we are adding it to our filtered list
                filteredList.add(character);
            }
        }

        mAdapter.filterList(filteredList);
        Toast.makeText(getApplicationContext(), filteredList.size() + " items ingeladen!", Toast.LENGTH_SHORT).show();

//        if(filteredList.isEmpty()) {
//            // If no item is added in our filtered list, we are displaying a toast message that no data is found
//            characterRV.setVisibility(View.GONE);
//
//        } else {
//            characterRV.setVisibility(View.VISIBLE);
//            mAdapter.filterList(filteredList);
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("pause");
        timePause = LocalDateTime.now();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("resume");
        LocalDateTime timeResume = LocalDateTime.now();

    }

    @Override
    // Method to remove keyboard when clicked outside of input field
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void buildRecyclerView() {
        // Initializing our adapter class
        mAdapter = new CharacterAdapter(mCharacters, this);

        // Adding layout manager to our recycler view
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            characterRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            characterRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }

        // Connecting adapter to our recycler view
        characterRV.setAdapter(mAdapter);
    }

    private void languageToggle() {
//        mTitleBar = findViewById(R.id.fragment_menubar);
//        mFlagIconEnglish = findViewById(R.id.fragment_menubar_flag_english);
//        mFlagIconDutch = findViewById(R.id.fragment_menubar_flag_dutch);
//
//        mFlagIconDutch.setOnClickListener(v -> {
//            context = LocaleHelper.setLocale(MainActivity.this, "en");
//            resources = context.getResources();
//            System.out.println("Dutch flag icon");
//        });
//
//        mFlagIconEnglish.setOnClickListener(v -> {
//            context = LocaleHelper.setLocale(MainActivity.this, "nl");
//            resources = context.getResources();
//        });
    }
}