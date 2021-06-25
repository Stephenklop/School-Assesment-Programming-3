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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.stephenklop.breakingbadassesment.data.APIService;
import com.stephenklop.breakingbadassesment.data.LocalAppStorage;
import com.stephenklop.breakingbadassesment.domain.Character;
import com.stephenklop.breakingbadassesment.logic.LocaleHelper;
import com.stephenklop.breakingbadassesment.ui.home.CharacterAdapter;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    // Create final variables
    private final APIService apiService;
    private final LocalAppStorage localAppStorage;
    private final String LOG_TAG = this.getClass().getSimpleName();

    // Create variables
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    // Creating variables for our UI components
    private RecyclerView characterRV;

    // Variable for our adapter class and array list
    private CharacterAdapter mAdapter;
    private List<Character> mCharacters;

    public MainActivity() {
        localAppStorage = (LocalAppStorage) this.getApplication();
        apiService = new APIService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "On creation of the homepage");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call shared preferences
        sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        // Set search view
        setSearchBar();

        // Set radio buttons
        setRadioButtonActions();

        // Initializing our variables
        characterRV = findViewById(R.id.activity_main_rv_items);

        // Create threads
        Thread charactersThread = new Thread(() -> {
            Looper.prepare();
            mCharacters = apiService.getAllCharacters();
            localAppStorage.setCharacters(mCharacters);
            Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
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

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "On pause of the homepage");
        super.onPause();

        sharedPreferencesEditor.putString("lifeCycleLeaveTime", convertCurrentDateTime());
        sharedPreferencesEditor.commit();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "On resume of the homepage");
        super.onResume();

        String currentTime = convertCurrentDateTime();
        String leaveTime = sharedPreferences.getString("lifeCycleLeaveTime", currentTime);
        Duration duration = Duration.between(OffsetDateTime.parse(leaveTime), OffsetDateTime.parse(currentTime));

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.youHaveBeenAway) + " " + duration.getSeconds() + " " + getResources().getString(R.string.seconds) , Toast.LENGTH_LONG).show();
    }

    private String convertCurrentDateTime() {
        Log.d(LOG_TAG, "Convert DateTime to String");
        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(new Date());
    }

    private void setSearchBar() {
        Log.d(LOG_TAG, "Set the searchbar on homepage");
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
        Log.d(LOG_TAG, "Set filter on homepage");
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

        if(filteredList.isEmpty()) {
            findViewById(R.id.activity_main_not_items_found).setVisibility(View.VISIBLE);
            characterRV.setVisibility(View.GONE);
        } else {
            findViewById(R.id.activity_main_not_items_found).setVisibility(View.GONE);
            characterRV.setVisibility(View.VISIBLE);
            mAdapter.filterList(filteredList);
            Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    // Method to remove keyboard when clicked outside of input field
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d(LOG_TAG, "Clicked outside keyboard");
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
        Log.d(LOG_TAG, "Fill Recyclerview on homepage");
        // Initializing our adapter class
        mAdapter = new CharacterAdapter(mCharacters, this);

        // Adding layout manager to our recycler view
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            characterRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        } else {
            characterRV.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }

        if(mCharacters.size() != 0) {
            // Connecting adapter to our recycler view
            findViewById(R.id.activity_main_not_items_found).setVisibility(View.INVISIBLE);
            characterRV.setAdapter(mAdapter);
        } else {
            findViewById(R.id.activity_main_not_items_found).setVisibility(View.VISIBLE);
        }
    }

    private void setRadioButtonActions() {
        Log.d(LOG_TAG, "Add listeners to the radio buttons on homepage");

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.activity_main_rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println(checkedId);
                System.out.println(group);

                // Creating a new array list to filter our data
                ArrayList<Character> filteredList = new ArrayList<>();

                // Running a for loop to compare elements
                for(Character character : mCharacters) {

                    if(checkedId == R.id.activity_main_rg_first) {
                        mAdapter.filterList(mCharacters);
                        sharedPreferencesEditor.remove("selectedStatus");
                        sharedPreferencesEditor.commit();
                        Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
                    } else if(checkedId == R.id.activity_main_rg_second) {
                        if(character.getmStatus().toLowerCase().equals("presumed dead")) {
                            filteredList.add(character);
                        }
                        sharedPreferencesEditor.putString("selectedStatus", "presumed dead");
                        sharedPreferencesEditor.commit();
                        Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
                    } else if(checkedId == R.id.activity_main_rg_third) {
                        if(character.getmStatus().toLowerCase().equals("alive")) {
                            filteredList.add(character);
                        }
                        sharedPreferencesEditor.putString("selectedStatus", "alive");
                        sharedPreferencesEditor.commit();
                        Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
                    } else if(checkedId == R.id.activity_main_rg_fourth) {
                        if(character.getmStatus().toLowerCase().equals("deceased")) {
                            filteredList.add(character);
                        }
                        sharedPreferencesEditor.putString("selectedStatus", "deceased");
                        sharedPreferencesEditor.commit();
                        Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
                    } else if(checkedId == R.id.activity_main_rg_fifth) {
                        if(character.getmStatus().toLowerCase().equals("unknown")) {
                            filteredList.add(character);
                        }
                        sharedPreferencesEditor.putString("selectedStatus", "unknown");
                        sharedPreferencesEditor.commit();
                        Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
                    }
                }

                if(filteredList.size() != 0) {
                    mAdapter.filterList(filteredList);
                    Toast.makeText(getApplicationContext(), mCharacters.size() + " " + getResources().getString(R.string.loaded), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}